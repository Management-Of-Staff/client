package myapplication.second.workinghourmanagement

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.TextView
import myapplication.second.workinghourmanagement.R

class CustomDialog(context: Context, contents: String) :
    Dialog(context) {
    private val msgContents: TextView
    val shutdownClick: Button

    init {
        setContentView(R.layout.activity_custom_dialog)
        msgContents = findViewById(R.id.contents)
        msgContents.text = contents
        shutdownClick = findViewById(R.id.btn_shutdown)
    }
}