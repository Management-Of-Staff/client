package myapplication.second.workinghourmanagement

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.databinding.CommonLoginActivityBinding
import myapplication.second.workinghourmanagement.databinding.OwnerJoinInfoActivityBinding

class OwnerJoinInfoActivity : AppCompatActivity() {
    private lateinit var binding: OwnerJoinInfoActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.owner_join_info_activity)

        binding.ownerJoinBtnJoin.setOnClickListener{
            val intent = Intent(this, OwnerJoinCompleteActivity::class.java)
            startActivity(intent)
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar: ActionBar = supportActionBar!!
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}