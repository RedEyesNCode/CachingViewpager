package com.redeyesncode.cachingviewpager.base

import android.app.Dialog
import android.content.Context
import android.widget.LinearLayout
import com.redeyesncode.cachingviewpager.R

class LoadingProgressDialog(context: Context) : Dialog(context) {

    init {
        setContentView(R.layout.dialog_loading)
        setCancelable(false)
        window?.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }


}