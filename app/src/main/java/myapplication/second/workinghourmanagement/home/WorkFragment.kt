package myapplication.second.workinghourmanagement.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import myapplication.second.workinghourmanagement.databinding.FragmentOwnerWorkBinding

class WorkFragment: Fragment() {
    private lateinit var binding: FragmentOwnerWorkBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOwnerWorkBinding.inflate(layoutInflater)

        return binding.root
    }

    companion object{
        fun newInstance(): WorkFragment{
            return WorkFragment()
        }
    }
}