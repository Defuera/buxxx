package ru.justd.bux.app.view

import android.content.Context
import android.view.View
import android.widget.TextView
import ru.justd.bux.R
import ru.justd.lilwidgets.LilLoaderWidget

class SimpleError(val message : String) : LilLoaderWidget.Error {
    override fun inflateErrorView(context: Context): View? {
        val textView = View.inflate(context, R.layout.widget_simple_error, null) as TextView
        textView.text = message
        return textView
    }
}