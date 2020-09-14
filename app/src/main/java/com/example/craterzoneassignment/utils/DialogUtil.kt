package com.example.craterzoneassignment.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.craterzoneassignment.R

fun openDialog(context: Context, iDialogCallback: IDialogCallback) {
    val builder = AlertDialog.Builder(context).create()
    val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)

    val editText = dialogLayout.findViewById<EditText>(R.id.edit_column)
    val buttonSubmit = dialogLayout.findViewById<Button>(R.id.button_submit)

    buttonSubmit.setOnClickListener{
        if (editText.text.toString().isNotEmpty()) {
            var column = Integer.parseInt(editText.text.toString())
            if (column in 1..5) {
                iDialogCallback.onButtonClick(column)
                builder.dismiss()
            }
        }
    }

    builder.setView(dialogLayout)
    builder.show()
}

interface IDialogCallback {
    fun onButtonClick(value: Int)
}