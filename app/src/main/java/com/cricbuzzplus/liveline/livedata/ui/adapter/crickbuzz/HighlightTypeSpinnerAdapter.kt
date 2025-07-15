package com.cricbuzzplus.liveline.livedata.ui.adapter.crickbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cricbuzzplus.liveline.R
import com.cricbuzzplus.liveline.livedata.model.HighlightTypeModel
import com.cricbuzzplus.liveline.livedata.response.cricbuzz.InningsScoreListItem

class HighlightTypeSpinnerAdapter(
    private val context: Context,
    private val teamNames: ArrayList<HighlightTypeModel>
) : BaseAdapter() {
    override fun getCount(): Int {
        return teamNames.size
    }

    override fun getItem(position: Int): Any {
        return teamNames[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val teamName = teamNames[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_spinner_layout, null)
        val textView = view.findViewById<TextView>(R.id.text1)

        textView.text = teamName.name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val teamName = teamNames[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_spinner_layout, null)
        val textView = view.findViewById<TextView>(R.id.text1)
        textView.text = teamName.name
        return view
    }
}