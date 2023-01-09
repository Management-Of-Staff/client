package myapplication.second.workinghourmanagement.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import myapplication.second.workinghourmanagement.databinding.FragmentOwnerHomeBinding

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentOwnerHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOwnerHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    companion object{
        fun newInstance(): HomeFragment{
            return HomeFragment()
        }
    }
}