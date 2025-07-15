package com.cricbuzzplus.liveline.livedata.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cricbuzzplus.liveline.databinding.FragmentTipsBinding
import com.cricbuzzplus.liveline.livedata.base.BaseFragment
import com.cricbuzzplus.liveline.livedata.response.TipsResponse
import com.cricbuzzplus.liveline.livedata.ui.adapter.TipsAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class TipsFragment :  BaseFragment() {

  //  private lateinit var viewModel: LiveFragmentsViewModel
    lateinit var binding : FragmentTipsBinding

    var list  = arrayListOf<TipsResponse>()

    var adapter : TipsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTipsBinding.inflate(inflater,container,false)
        //viewModel = ViewModelProvider(this).get(LiveFragmentsViewModel::class.java)
        getData()
        return binding.root
    }




    fun getData(){

        try {

            FirebaseFirestore.getInstance().collection("Prediction")
                .orderBy("createdAt", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w("TAG", "Listen failed.", e)
                        //   showToast(e.message.toString())
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {

                        for (dc in snapshot!!.documentChanges) {

                            val prediction = dc.document.toObject(TipsResponse::class.java) as TipsResponse

                            if (!list.contains(prediction)) {
                                list.add(prediction)
                            }

                        }
                        setRecycler()
                    }

                }
        }catch (e:Exception){
            Log.e(TAG, "getData: ${e.message}" )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    fun setRecycler(){
        if (!list.isNullOrEmpty()){
            if (adapter == null) {
                adapter = TipsAdapter(list, activity)
                binding.recyclerTips.adapter = adapter
            }else{
                adapter?.updateList(list)
            }
            binding.recyclerTips.smoothScrollToPosition(adapter?.itemCount!! - 1)

        }
    }

}