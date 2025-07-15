package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cricbuzzplus.liveline.databinding.FragmentPollsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.MyPolls
import com.cricbuzzplus.liveline.livedata.response.PollModel
import com.cricbuzzplus.liveline.livedata.response.PollsResponse
import com.cricbuzzplus.liveline.livedata.ui.adapter.SliderRecyclerAdapter
import com.cricbuzzplus.liveline.livedata.ui.interfaces.PollClickListener
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.messaging.FirebaseMessaging

class PollsFragment : BaseFragment(), PollClickListener {

  //  private lateinit var viewModel: PollsViewModel
    lateinit var binding: FragmentPollsBinding

    var list = arrayListOf<PollsResponse>()
    lateinit var db:FirebaseFirestore
    lateinit var device_id: String

    var myPollsList = arrayListOf<MyPolls>()

    var sliderAdapter : SliderRecyclerAdapter?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // viewModel = ViewModelProvider(this).get(PollsViewModel::class.java)
        binding = FragmentPollsBinding.inflate(inflater,container,false)
        db= FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            device_id = task.result.toString()

            Log.e("TAG1111", "onViewCreated: ${device_id}", )

        })

        getData()
    }


    fun getData(){
        list.clear()
        myPollsList.clear()
        FirebaseFirestore.getInstance().collection("poll")
            .orderBy("match_date",Query.Direction.ASCENDING).addSnapshotListener{ snapshot, e ->
                if (e != null) {
                    Log.w("TAG", "Listen failed.", e)
                    //   showToast(e.message.toString())
                    return@addSnapshotListener
                }
                if (snapshot != null ) {
                    list.clear()
                    for (dc in snapshot!!.documentChanges) {

                        val prediction=dc.document.toObject(PollsResponse::class.java) as PollsResponse
                        list.add(prediction)

                       // getMyPollList(prediction.match_id)

                    }
                    Log.e("TAG111", "getData: not null", )
                    setAdapter()
                }else{
                    Log.e("TAG111", "getData: null", )
                }
            }
        /*db.collection("poll").orderBy("createdAt",Query.Direction.ASCENDING).get().addOnCompleteListener{ task ->
                if (task.isSuccessful){

                    for (doc in task.result?.documentChanges!!){

                        val prediction=doc.document.toObject(PollsResponse::class.java) as PollsResponse
                        list.add(prediction)

                        getMyPollList(prediction.match_id)

                    }
                    setAdapter()

                }else{
                    Log.e("TAG", "personal_chat: "+task.exception )
                }
            }.addOnFailureListener { error ->
                Toast.makeText(activity,error.message,Toast.LENGTH_SHORT).show()
            }*/
    }

    private fun setAdapter() {

        Log.e("TAG11", "setAdapter: "+list )

        if (!list.isNullOrEmpty()) {
            if (sliderAdapter == null) {
                sliderAdapter = SliderRecyclerAdapter(list, activity, this, device_id)
                binding.viewPage.adapter = sliderAdapter
                binding.indicator.attachTo(binding.viewPage, true)
            } else {
                Handler().postDelayed({
                    sliderAdapter?.updateList(list)
                }, 500)

            }
        }else{
            binding.linearlayoutIfLiveNotShowImage.visibility = View.VISIBLE
        }
    }

    fun getMyPollList(match_id: Int){

        FirebaseFirestore.getInstance().collection("poll").document(""+match_id).collection("votes")

            .addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                   showToast(e.message.toString())
                return@addSnapshotListener
            }

            for (doc in snapshot!!.documentChanges) {
                if (device_id.equals(doc.document.id)) {
                    val myPolls = doc.document.toObject(MyPolls::class.java) as MyPolls
                    Log.e("TAG11", "getMyPollList: " + doc.document.id)
                    myPollsList.add(myPolls)
                    Log.e("TAG11", "myPolls: " + myPolls.my_vote)
                }
            }


        }

       /* db.collection("poll").document(match_id.toString())
            .collection("votes").get().addOnCompleteListener{ task ->
                if (task.isSuccessful){

                    for (doc in task.result?.documentChanges!!){

                        if (device_id.equals(doc.document.id)) {
                            val myPolls = doc.document.toObject(MyPolls::class.java) as MyPolls
                            Log.e("TAG11", "getMyPollList: " + doc.document.id)
                            myPollsList.add(myPolls)
                            Log.e("TAG11", "myPolls: " + myPolls.my_vote)
                        }
                    }

                }else{
                    Log.e("TAG", "personal_chat: "+task.exception )
                }
            }.addOnFailureListener { error ->
                Toast.makeText(activity,error.message,Toast.LENGTH_SHORT).show()
            }*/

    }

    override fun onClick(match_id: Int, my_vote: String,team:Int) {

        val pollModel=  PollModel(match_id,my_vote,device_id, FieldValue.serverTimestamp())
        val matchref=db.collection("poll").document(match_id.toString())
        matchref.collection("votes").document()
            .set(pollModel).addOnSuccessListener  { result->

                if (team == 1){
                    matchref.update("count_a", FieldValue.increment(1))
                }
                else if (team == 2){
                    matchref.update("count_b", FieldValue.increment(1))
                }
                else if (team == 3){
                    matchref.update("tie", FieldValue.increment(1))
                }

                getData()

            }.addOnFailureListener{e->
                handleProgressLoader(false)
                Log.e("Creating Error","Room "+e.message.toString())
                showToast(e.message.toString())
                // activity?.findNavController(R.id.nav_host_fragment).navigateUp()
            }

    }
}