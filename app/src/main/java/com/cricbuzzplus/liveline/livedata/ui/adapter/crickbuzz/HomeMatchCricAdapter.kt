package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.databinding.HomeMatchesBindingNewCric
import com.cricbuzzplus.liveline.databinding.HomeNewsBinding
import com.cricbuzzplus.liveline.livedata.response.NewsListResponseItem
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.MatchesItemCric
import com.cricbuzzplus.liveline.livedata.ui.interfaces.OnClickInterface
import com.cricbuzzplus.liveline.utils.Constants

class HomeMatchCricAdapter(
    val list: ArrayList<MatchesItemCric>,
    val context: Context
) : RecyclerView.Adapter<HomeMatchCricAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: HomeMatchesBindingNewCric) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(HomeMatchesBindingNewCric.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       // holder.getBinding().model=list[position]
        val model = list[position]


        val requestOptions = RequestOptions()
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        //     requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)

        holder.binding.textviewMatchSeries.setText(model.match?.matchInfo?.seriesName.toString())
        holder.binding.textviewTeamALive.setText(model.match?.matchInfo?.team1?.teamSName.toString())
        holder.binding.textviewTeamBLive.setText(model.match?.matchInfo?.team2?.teamSName.toString())

        holder.binding.resultToss.setText(model.match?.matchInfo?.status.toString())

        if (model.match?.matchInfo?.state.equals("In Progress",true)){
            holder.binding.resultToss.setTextColor(context.resources.getColor(R.color.red))
        }else if (model.match?.matchInfo?.state.equals("Preview",true)){
            holder.binding.resultToss.setTextColor(context.resources.getColor(R.color.orange))
        }else if (model.match?.matchInfo?.state.equals("Complete",true)){
            holder.binding.resultToss.setTextColor(context.resources.getColor(R.color.bluene))
        }else{
            holder.binding.resultToss.setTextColor(context.resources.getColor(R.color.txt_color))
        }


        Glide.with(context).load(""+ Constants.cricbuzzImgFirst+model.match?.matchInfo?.team1?.imageId+ Constants.cricbuzzImgSecond).apply(requestOptions).into(holder.binding.teamImageALive)
        Glide.with(context).load(""+ Constants.cricbuzzImgFirst+model.match?.matchInfo?.team2?.imageId+ Constants.cricbuzzImgSecond).apply(requestOptions).into(holder.binding.teamImageBLive)


        if (model.match?.matchScore != null){

            if (model.match.matchScore.team1Score != null  && model.match.matchScore.team1Score.inngs1 != null){

                if (model.match.matchScore.team1Score.inngs1.wickets == null) {
                    holder.binding.tvTeamAScore.setText("" + model.match.matchScore.team1Score.inngs1.runs + "-" + 0)

                }else{
                    holder.binding.tvTeamAScore.setText("" + model.match.matchScore.team1Score.inngs1.runs + "-" + model.match.matchScore.team1Score.inngs1.wickets)

                }
                holder.binding.teamAOvers.setText("(" + model.match.matchScore.team1Score.inngs1.overs + ")")
            }

            if (model.match.matchScore.team2Score != null  && model.match.matchScore.team2Score.inngs1 != null){

                if (model.match.matchScore.team2Score.inngs1.wickets == null) {
                    holder.binding.tvTeamBScore.setText("" + model.match.matchScore.team2Score.inngs1.runs + "-" + 0)
                }else{
                    holder.binding.tvTeamBScore.setText("" + model.match.matchScore.team2Score.inngs1.runs + "-" + model.match.matchScore.team2Score.inngs1.wickets)
                }
                holder.binding.teamBOvers.setText("("+model.match.matchScore.team2Score.inngs1.overs+")")

            }

        }


    }

    override fun getItemCount(): Int {
       return list.size
    }



}