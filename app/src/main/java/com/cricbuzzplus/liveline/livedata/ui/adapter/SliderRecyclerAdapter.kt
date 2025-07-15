package com.cricbuzzplus.liveline.livedata.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.ItemPollsBinding
import com.cricbuzzplus.liveline.livedata.response.MyPolls
import com.cricbuzzplus.liveline.livedata.response.PollsResponse
import com.cricbuzzplus.liveline.livedata.ui.interfaces.PollClickListener
import com.google.firebase.firestore.FirebaseFirestore

class SliderRecyclerAdapter(
    var itemList: ArrayList<PollsResponse>, val context : AppCompatActivity, val listener: PollClickListener, val deviceid:String
) : RecyclerView.Adapter<SliderRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemPollsBinding) : RecyclerView.ViewHolder(binding.root) {}

    fun updateList( itemList: ArrayList<PollsResponse>){
        this.itemList = itemList

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPollsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val model=itemList[position]

        holder.binding.model=model

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.teamARadio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                holder.binding.teamARadio.isChecked = true
                holder.binding.teamBRadio.isChecked = false
                holder.binding.tieRadio.isChecked = false

                holder.binding.teamASelect.setBackgroundResource(R.drawable.round_bg)
                holder.binding.teamBSelect.setBackgroundResource(R.drawable.roung_unselected_bg)
                holder.binding.tieSelect.setBackgroundResource(R.drawable.roung_unselected_bg)

                holder.binding.teamASelect.setTextColor(context.resources.getColor(R.color.white))
                holder.binding.teamBSelect.setTextColor(context.resources.getColor(R.color.black))
                holder.binding.tieSelect.setTextColor(context.resources.getColor(R.color.black))
            }
        }

        holder.binding.teamBRadio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                holder.binding.teamBRadio.isChecked = true
                holder.binding.teamARadio.isChecked = false
                holder.binding.tieRadio.isChecked = false

                holder.binding.teamBSelect.setBackgroundResource(R.drawable.round_bg)
                holder.binding.teamASelect.setBackgroundResource(R.drawable.roung_unselected_bg)
                holder.binding.tieSelect.setBackgroundResource(R.drawable.roung_unselected_bg)

                holder.binding.teamBSelect.setTextColor(context.resources.getColor(R.color.white))
                holder.binding.teamASelect.setTextColor(context.resources.getColor(R.color.black))
                holder.binding.tieSelect.setTextColor(context.resources.getColor(R.color.black))
            }
        }

        holder.binding.tieRadio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                holder.binding.tieRadio.isChecked = true
                holder.binding.teamBRadio.isChecked = false
                holder.binding.teamARadio.isChecked = false

                holder.binding.tieSelect.setBackgroundResource(R.drawable.round_bg)
                holder.binding.teamASelect.setBackgroundResource(R.drawable.roung_unselected_bg)
                holder.binding.teamBSelect.setBackgroundResource(R.drawable.roung_unselected_bg)

                holder.binding.tieSelect.setTextColor(context.resources.getColor(R.color.white))
                holder.binding.teamASelect.setTextColor(context.resources.getColor(R.color.black))
                holder.binding.teamBSelect.setTextColor(context.resources.getColor(R.color.black))
            }
        }

        holder.binding.teamASelect.setOnClickListener(View.OnClickListener {
            holder.binding.teamARadio.isChecked = true
            holder.binding.teamBRadio.isChecked = false
            holder.binding.tieRadio.isChecked = false

            holder.binding.teamASelect.setBackgroundResource(R.drawable.round_bg)
            holder.binding.teamBSelect.setBackgroundResource(R.drawable.roung_unselected_bg)
            holder.binding.tieSelect.setBackgroundResource(R.drawable.roung_unselected_bg)

            holder.binding.teamASelect.setTextColor(context.resources.getColor(R.color.white))
            holder.binding.teamBSelect.setTextColor(context.resources.getColor(R.color.black))
            holder.binding.tieSelect.setTextColor(context.resources.getColor(R.color.black))

        })

        holder.binding.teamBSelect.setOnClickListener(View.OnClickListener {
            holder.binding.teamBRadio.isChecked = true
            holder.binding.teamARadio.isChecked = false
            holder.binding.tieRadio.isChecked = false

            holder.binding.teamBSelect.setBackgroundResource(R.drawable.round_bg)
            holder.binding.teamASelect.setBackgroundResource(R.drawable.roung_unselected_bg)
            holder.binding.tieSelect.setBackgroundResource(R.drawable.roung_unselected_bg)

            holder.binding.teamBSelect.setTextColor(context.resources.getColor(R.color.white))
            holder.binding.teamASelect.setTextColor(context.resources.getColor(R.color.black))
            holder.binding.tieSelect.setTextColor(context.resources.getColor(R.color.black))
        })

        holder.binding.tieSelect.setOnClickListener(View.OnClickListener {
            holder.binding.tieRadio.isChecked = true
            holder.binding.teamBRadio.isChecked = false
            holder.binding.teamARadio.isChecked = false

            holder.binding.tieSelect.setBackgroundResource(R.drawable.round_bg)
            holder.binding.teamASelect.setBackgroundResource(R.drawable.roung_unselected_bg)
            holder.binding.teamBSelect.setBackgroundResource(R.drawable.roung_unselected_bg)

            holder.binding.tieSelect.setTextColor(context.resources.getColor(R.color.white))
            holder.binding.teamASelect.setTextColor(context.resources.getColor(R.color.black))
            holder.binding.teamBSelect.setTextColor(context.resources.getColor(R.color.black))
        })
        holder.binding.submitBtn.setOnClickListener(View.OnClickListener {

            if (holder.binding.teamARadio.isChecked) {
                holder.binding.teamARadio.isChecked = false
                holder.binding.teamASelect.setBackgroundResource(R.drawable.roung_unselected_bg)
                holder.binding.teamASelect.setTextColor(context.resources.getColor(R.color.black))
                listener.onClick(model?.match_id!!, holder.binding.teamASelect.text.toString(),1)
            } else if (holder.binding.teamBRadio.isChecked) {
                holder.binding.teamBRadio.isChecked = false
                holder.binding.teamBSelect.setBackgroundResource(R.drawable.roung_unselected_bg)
                holder.binding.teamBSelect.setTextColor(context.resources.getColor(R.color.black))
                listener.onClick(model?.match_id!!, holder.binding.teamBSelect.text.toString(),2)
            } else if (holder.binding.tieRadio.isChecked) {
                holder.binding.tieRadio.isChecked = false
                holder.binding.tieSelect.setBackgroundResource(R.drawable.roung_unselected_bg)
                holder.binding.tieSelect.setTextColor(context.resources.getColor(R.color.black))
                listener.onClick(model?.match_id!!, holder.binding.tieSelect.text.toString(),3)
            } else {
                Toast.makeText(context, "Please select prediction", Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        getMyData(holder.binding)
    }

    @SuppressLint("NewApi")
    fun getMyData(binding: ItemPollsBinding){


       /* FirebaseFirestore.getInstance().collection("poll").document(""+binding.model?.match_id).
        collection("votes").whereEqualTo("deviceid",deviceid).get().addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    Log.e("TAG11", "getMyData: yes" )
                    for (doc in task.result?.documentChanges!!){

                        if (doc.document.get("deviceid").toString().equals(deviceid)){

                            binding.myvote=true

                            var myvote = doc.document.get("my_vote")

                            var totalCount = binding.model?.count_a!! + binding.model?.count_b!! + binding.model?.tie!!

                            binding.totalPoll.setText("Total poll : ${ totalCount }")
                            binding.yourVote.setText("Your Vote : ${ myvote }")

                            binding.teamAShort.setText(binding.model?.team_a_short)
                            binding.teamBShort.setText(binding.model?.team_b_short)
                            binding.tieShort.setText("TIE")

                            var teamAPercent = (binding.model?.count_a!!.toDouble() / totalCount) * 100
                            var teamBPercent = (binding.model?.count_b!!.toDouble()/totalCount)*100
                            var tiePercent = (binding.model?.tie!!.toDouble()/totalCount)*100

                            binding.teamAProgress.setProgress(teamAPercent.toInt(),true)
                            binding.teamBProgress.setProgress(teamBPercent.toInt(),true)
                            binding.tieProgress.setProgress(tiePercent.toInt(),true)

                            binding.teamAPercent.setText(""+teamAPercent.toInt()+" %")
                            binding.teamBPercent.setText(""+teamBPercent.toInt()+" %")
                            binding.tiePercent.setText(""+tiePercent.toInt()+" %")

                        }else{
                            binding.myvote=false
                        }
                      //  doc.document.get("deviceid")
                           *//* val myPolls = doc.document.toObject(MyPolls::class.java) as MyPolls
                            Log.e("TAG11", "getMyPollList: " + doc.document.id)
                            myPollsList.add(myPolls)*//*
                            Log.e("TAG11", "getMyData: " + doc.document.get("deviceid"))
                            Log.e("TAG11", "getMyData: " + doc.document)
                            Log.e("TAG11", "getMyData: " + doc.document.data)

                    }

                }else{
                    binding.myvote=false
                }
            }.addOnFailureListener { error ->
            binding.myvote=false
            }*/

        FirebaseFirestore.getInstance().collection("poll").document(""+binding.model?.match_id).collection("votes")
            .whereEqualTo("deviceid",deviceid)
            .get().addOnSuccessListener  {
                if(it.isEmpty){
                    binding.myvote=false
                    binding.progressBar.visibility = View.GONE
                    binding.mainLinear.visibility = View.VISIBLE
                }
                if(it.documents.isEmpty()){
                    binding.myvote=false
                    binding.progressBar.visibility = View.GONE
                    binding.mainLinear.visibility = View.VISIBLE
                }
                it.documents.forEach {doc->
                   val mypoll= doc.toObject(MyPolls::class.java)
                    binding.myvote=true
                    Log.e("TAG11", "getMyData: yes" )
                    var myvote = mypoll?.my_vote

                    var totalCount = binding.model?.count_a!! + binding.model?.count_b!! + binding.model?.tie!!

                    binding.totalPoll.setText("Total poll : ${ totalCount }")
                    binding.yourVote.setText("Your Vote : ${ myvote }")

                    binding.teamAShort.setText(binding.model?.team_a_short)
                    binding.teamBShort.setText(binding.model?.team_b_short)
                    binding.tieShort.setText("TIE")

                    var teamAPercent = (binding.model?.count_a!!.toDouble() / totalCount) * 100
                    var teamBPercent = (binding.model?.count_b!!.toDouble()/totalCount)*100
                    var tiePercent = (binding.model?.tie!!.toDouble()/totalCount)*100

                    binding.teamAProgress.setProgress(teamAPercent.toInt(),true)
                    binding.teamBProgress.setProgress(teamBPercent.toInt(),true)
                    binding.tieProgress.setProgress(tiePercent.toInt(),true)

                    binding.teamAPercent.setText(""+teamAPercent.toInt()+" %")
                    binding.teamBPercent.setText(""+teamBPercent.toInt()+" %")
                    binding.tiePercent.setText(""+tiePercent.toInt()+" %")
                    binding.progressBar.visibility = View.GONE
                    binding.mainLinear.visibility = View.VISIBLE
                }


            }.addOnFailureListener {
                Log.e("TAG11", "getMyData: no" )
                binding.myvote=false
                binding.progressBar.visibility = View.GONE
                binding.mainLinear.visibility = View.VISIBLE
            }
    }


}