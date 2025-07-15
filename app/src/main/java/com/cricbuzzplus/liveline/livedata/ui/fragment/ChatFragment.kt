package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.FragmentChatBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.model.ChatModel
import com.cricbuzzplus.liveline.livedata.model.ChatResponse
import com.cricbuzzplus.liveline.livedata.model.UserModel
import com.cricbuzzplus.liveline.livedata.model.UserModelResponse
import com.cricbuzzplus.liveline.livedata.response.LoginResponse
import com.cricbuzzplus.liveline.livedata.ui.activity.SignInActivity
import com.cricbuzzplus.liveline.livedata.ui.adapter.ChatRecyclerAdapter
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.LiveViewModel
import com.cricbuzzplus.liveline.mPrefs

class ChatFragment : BaseFragment() {

    private lateinit var viewModel: LiveViewModel
    lateinit var binding: FragmentChatBinding
    lateinit var firebaseAuth: FirebaseAuth
    var senderid=""
    var sendername=""

    var matchStatus=""

    var matchId = 0


    var chatRecyclerAdapter: ChatRecyclerAdapter?= null
    var chatList = arrayListOf<ChatResponse>()
    lateinit var db: FirebaseFirestore

    val loginResultLauncer = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if (result.resultCode== Activity.RESULT_OK){
            val dataIntent:Intent? = result.data
            if (dataIntent!=null){
                val data=if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    dataIntent?.extras?.getParcelable("data",LoginResponse::class.java)
                }else{
                    dataIntent?.extras?.getParcelable<LoginResponse>("data")!!
                }

                if (data!=null){
                    mPrefs.prefUserDetails=data
                    binding.islogin=true

                    if (matchStatus.equals("Finished")) {

                        binding.progressBar.visibility = View.GONE
                        binding.matchFinished.visibility = View.VISIBLE
                        binding.ll.visibility = View.GONE

                    }else{
                        binding.matchFinished.visibility = View.GONE
                        getChat()
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LiveViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()

        matchId = arguments?.getInt("matchId")!!
        matchStatus = arguments?.getString("matchStatus","").toString()
      //  val user = firebaseAuth.currentUser
        if (mPrefs.prefUserDetails != null) {
            binding.islogin = true
            //  Toast.makeText(activity, "already logged in", Toast.LENGTH_SHORT).show()
        }else{
            binding.islogin = false
        }

        if (matchStatus.equals("Finished")) {
            binding.progressBar.visibility = View.GONE
            binding.matchFinished.visibility = View.VISIBLE
            binding.ll.visibility = View.GONE
        }else{
            binding.matchFinished.visibility = View.GONE
            getChat()
        }


        binding.TvGoogleLogin.setOnClickListener {


            val intent = Intent(activity, SignInActivity::class.java)
            //startActivity(intent)
            loginResultLauncer.launch(intent)
            /*val signInIntent: Intent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, Req_Code)*/

        }
        binding.sendbt.setOnClickListener {

            if (!binding.ETTextmsg.text.trim().toString().isNullOrEmpty()) {
                addchatdatatodb()
                binding.ETTextmsg.text.clear()
            }else{
                showToast("write something to send")
            }
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    fun addchatdatatodb() {


      //  refdb = FirebaseDatabase.getInstance().getReference()
        val chatmodel = ChatModel(sendername,senderid, binding.ETTextmsg.text.trim().toString(),
            FieldValue.serverTimestamp())

    //    var id = refdb.push().key

        db.collection("msg").document(""+matchId).collection("chats").document().set(chatmodel)
        db.collection("msg").document(""+matchId).set(chatmodel)

       // refdb.child("msg").child(id.toString()).setValue(chatmodel)
//
//        /   refdb.child("msg").child(recvierid).child(id.toString()).setValue(msgmodel)
//       refdb.child("msg").child(senderid).push().setValue(chatmodel)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        chatRecyclerAdapter  = null
    }

    fun getChat(){


        if (mPrefs.prefUserDetails != null) {

            senderid = mPrefs.prefUserDetails?.id.toString()
            sendername = mPrefs.prefUserDetails?.firstName.toString()

            db.collection("msg").document(""+matchId).collection("chats")
                .orderBy("created", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, e ->

                    if (e != null) {
                        Log.w("TAG", "Listen failed.", e)
                        //   showToast(e.message.toString())
                        binding.progressBar.visibility = View.GONE
                        binding.noMessageFound.visibility = View.VISIBLE

                        return@addSnapshotListener
                    }
                    if (snapshot != null ) {

                        binding.progressBar.visibility = View.GONE

                        for (dc in snapshot!!.documentChanges) {
                            Log.e("TAG", "getChats: "+ dc.document )
                            val usermodel = dc.document.toObject(ChatResponse::class.java)
                            // chatModelList.add(usermodel)
                            when (dc.type) {

                                DocumentChange.Type.ADDED -> {
                                    chatList.add(usermodel)

                                }
                                DocumentChange.Type.MODIFIED -> {
                                    // need to check at runtime
                                    // onlineUsersList.set(dc.newIndex,usermodel)
                                }
                                DocumentChange.Type.REMOVED -> {
                                    chatList.remove(usermodel)
                                }

                            }

                        }
                        setMessageRecycler()
                    }else{
                        binding.progressBar.visibility = View.GONE
                    }
                }


        }else{
            binding.islogin = false
            binding.progressBar.visibility = View.GONE
        }

    }

    fun setMessageRecycler(){

        if (chatRecyclerAdapter == null) {
            chatRecyclerAdapter = ChatRecyclerAdapter(activity, chatList, senderid)
            binding.recyclerview.adapter = chatRecyclerAdapter
        } else {
            chatRecyclerAdapter?.updateList(chatList)
        }


        if (!chatList.isNullOrEmpty()) {
            binding.noMessageFound.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.recyclerview.smoothScrollToPosition(chatList.size - 1)
        } else {
            binding.noMessageFound.visibility = View.VISIBLE
        }

    }


}