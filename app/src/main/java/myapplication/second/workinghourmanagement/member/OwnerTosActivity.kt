package myapplication.second.workinghourmanagement.member

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerTosBinding

class OwnerTosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerTosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_tos)

        bind()
    }

    private fun bind() {
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
        binding.buttonNext.setOnClickListener {
            val intent = Intent(this, PhoneAuthActivity::class.java)
            startActivity(intent)
        }
    }
}