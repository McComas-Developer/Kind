package com.dangerfield.kind.util

import android.app.Activity
import android.graphics.Color
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_search.*
import java.lang.Math.abs

typealias Action = () -> Unit

fun View.showIFF(thisIsTrue: Boolean): View {
    this.visibility = if(thisIsTrue) View.VISIBLE else View.INVISIBLE
    return this
}

fun View.visibleContingency(whenVisible: Action, whenInvisible: Action){
    if (this.visibility == View.VISIBLE) whenVisible.invoke()
    else whenInvisible.invoke()
}

fun View.hideKeyBoardOnPressAway(){
    this.onFocusChangeListener = keyboardHider
}

private val keyboardHider = View.OnFocusChangeListener { view, b ->
    if (!b) { hideKeyboardFrom(view) }
}

private fun hideKeyboardFrom(view: View) {
    val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.addClearButton(view: View){
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            view.showIFF((p0?.length ?: 0) > 0)
        }
    })

    view.setOnClickListener {
        this.text.clear()
    }
}

fun EditText.onSearch(action: Action){
    this.setOnEditorActionListener { _, i, _ ->
        if(i == EditorInfo.IME_ACTION_SEARCH){
            action.invoke()
            this.clearFocus()
            true
        }else{false}
    }
}

fun EditText.addCharacterMax(view: TextView, max: Int){
    filters = arrayOf(InputFilter.LengthFilter(max))

    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val charsLeft = (max - (p0?.length ?: 0))

            if (charsLeft < 6) view.setTextColor(Color.RED)
            else view.setTextColor(Color.BLACK)

            view.text = charsLeft.toString()
        }
    })
}