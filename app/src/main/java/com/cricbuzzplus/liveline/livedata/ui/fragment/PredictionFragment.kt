package com.cricbuzzplus.liveline.livedata.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cricbuzzplus.liveline.databinding.PredictionFragmentBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.PredictionResponse
import com.cricbuzzplus.liveline.livedata.ui.adapter.PredictionAdapter

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.cricbuzzplus.liveline.livedata.ui.viewmodel.UsersViewModel
import kotlin.collections.ArrayList


class PredictionFragment : BaseFragment() {


    lateinit var binding: PredictionFragmentBinding

    private lateinit var viewModel: UsersViewModel

    var list : ArrayList<PredictionResponse> = arrayListOf()

    var adapter : PredictionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding = PredictionFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }


    fun getData(){

        FirebaseFirestore.getInstance().collection("Prediction")
            .orderBy("createdAt", Query.Direction.ASCENDING).addSnapshotListener{ snapshot, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                //   showToast(e.message.toString())
                return@addSnapshotListener
            }

            if (snapshot != null ) {

                for (dc in snapshot!!.documentChanges) {

                    val prediction=dc.document.toObject(PredictionResponse::class.java) as PredictionResponse
                    list.add(prediction)

                }
                setRecycler()
            }

        }
       /* FirebaseFirestore.getInstance()
            .collection("Prediction")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val myListOfDocuments =
                        task.result.documents

                    for (doc in myListOfDocuments) {

                        val prediction=doc.toObject(PredictionResponse::class.java) as PredictionResponse
                      list.add(prediction)

                        //Log.e(TAG, "getData: " + doc.data?.get("message"))
                    }
                   *//* Log.e(TAG, "getData: "+myListOfDocuments.get(0) )

                    val sfd = SimpleDateFormat("dd-MM-yyyy")
                    Log.e(TAG, "getData: "+ sfd.format(Date(list.get(0).date.toDate().toString())))*//*
                    for (li in list){
                        Log.e(TAG, "getData: date: "+li.date +" message: "+li.message )
                    }
                    setRecycler()
                }
            }.addOnFailureListener({
                Log.e(TAG, "getData: "+it.message )
            })*/
    }

    fun setRecycler(){
        if (!list.isNullOrEmpty()){
            if (adapter == null) {
                adapter = PredictionAdapter(list, context)
                binding.predictionRecycler.adapter = adapter
            }else{
                adapter?.updateList(list)
            }

            binding.predictionRecycler.smoothScrollToPosition(adapter?.itemCount!! - 1)

        }
    }

}