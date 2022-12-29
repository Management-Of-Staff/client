package myapplication.second.workinghourmanagement

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.TextView

class CustomDialog(context: Context, contents: String) :
    Dialog(context) {
    private val msgContents: TextView
    val shutdownClick: Button

    init {
        setContentView(R.layout.custom_dialog_activity)
        msgContents = findViewById(R.id.contents)
        msgContents.text = contents
        shutdownClick = findViewById(R.id.btn_shutdown)
    }
}