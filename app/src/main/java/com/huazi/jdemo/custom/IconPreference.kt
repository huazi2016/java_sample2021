package com.huazi.jdemo.custom

import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.huazi.jdemo.R
import com.huazi.jdemo.base.utils.Utils

/**
 * Created with Android Studio.
 * Description: 学习hegaojian大神自定义动画
 *
 * @author: Wangjianxian
 * @date: 2020/01/16
 * Time: 12:32
 */
class IconPreference(context: Context, attrs: AttributeSet) : Preference(context, attrs) {

    var circleImageView: MyColorCircleView? = null
    init {
        widgetLayoutResource = R.layout.item_icon_preference_preview
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)
        val color = Utils.getColor(context)
        circleImageView = holder?.itemView?.findViewById(R.id.iv_preview)
        circleImageView?.color = color
        circleImageView?.border = color
    }

    fun setView() {
        val color = Utils.getColor(context)
        circleImageView?.color = color
        circleImageView?.border = color
    }
}