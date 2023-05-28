@file:Suppress("NAME_SHADOWING")

package com.methe.assignmentapp.view.screen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.methe.assignmentapp.R
import com.methe.assignmentapp.model.GridViewModal

internal class GridFeatureAdapter(
    private val featureList: List<GridViewModal>,
    private val context: Context
) :
    BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var featureTv: TextView
    private lateinit var featureImg: ImageView

    override fun getCount(): Int {
        return featureList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView

        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.grid_feature_item, null)
        }

        featureImg = convertView!!.findViewById(R.id.iv_feature_logo)
        featureTv = convertView.findViewById(R.id.tv_feature)
        featureImg.setImageResource(featureList.get(position).featureImg)
        featureTv.text = featureList.get(position).featureName
        return convertView
    }
}