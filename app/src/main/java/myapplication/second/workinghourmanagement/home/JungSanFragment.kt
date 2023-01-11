package myapplication.second.workinghourmanagement.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import myapplication.second.workinghourmanagement.databinding.FragmentOwnerJungsanBinding

class JungSanFragment: Fragment() {
    private lateinit var binding: FragmentOwnerJungsanBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOwnerJungsanBinding.inflate(layoutInflater)

        return binding.root
    }

    companion object{
        fun newInstance(): JungSanFragment{
            return JungSanFragment()
        }
    }
}