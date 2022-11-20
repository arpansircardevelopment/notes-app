package com.arpansircar.notes_app.presentation.utils

import android.text.Editable
import android.text.TextWatcher
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.removeErrorMessage
import com.google.android.material.textfield.TextInputLayout

object ListenerUtils {
    fun getWatcher(til: TextInputLayout?, watcherHashSet: HashSet<TextWatcher>): TextWatcher {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                til?.removeErrorMessage()
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        watcherHashSet.add(textWatcher)
        return textWatcher
    }
}