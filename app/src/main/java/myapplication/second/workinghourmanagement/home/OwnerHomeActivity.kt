package myapplication.second.workinghourmanagement.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerHomeBinding

class OwnerHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_home)

        binding.navigation.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.home -> HomeFragment.newInstance()
                    R.id.store -> StoreFragment.newInstance()
                    R.id.work -> WorkFragment.newInstance()
                    R.id.jungsan -> JungSanFragment.newInstance()
                    else -> ProfileFragment.newInstance()
                }
            )
            true
        }
        binding.navigation.selectedItemId = R.id.home
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.layout_nav_bottom, fragment)
            .commit()
    }
}