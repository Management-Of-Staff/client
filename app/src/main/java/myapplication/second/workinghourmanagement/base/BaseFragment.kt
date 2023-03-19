package myapplication.second.workinghourmanagement.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<V:ViewDataBinding,VM:BaseViewModel>(@LayoutRes private val layoutRes: Int):Fragment(){
    private var _binding: V? = null
    protected val binding get() = _binding!!

    protected abstract val viewModel:VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        initView()
        setEvent()
        observeData()

        super.onViewCreated(view, savedInstanceState)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun initView()
    abstract fun setEvent()
    abstract fun observeData()
}