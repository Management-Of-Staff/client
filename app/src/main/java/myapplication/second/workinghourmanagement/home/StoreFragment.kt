package myapplication.second.workinghourmanagement.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import myapplication.second.workinghourmanagement.databinding.FragmentOwnerStoreBinding

class StoreFragment: Fragment() {
    private lateinit var binding: FragmentOwnerStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOwnerStoreBinding.inflate(layoutInflater)

        return binding.root
    }

    companion object{
        fun newInstance(): StoreFragment{
            return StoreFragment()
        }
    }
}