package myapplication.second.workinghourmanagement.store.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import myapplication.second.workinghourmanagement.databinding.DialogFragmentAskDeleteScheduleBinding

class AskDeleteScheduleDialogFragment: DialogFragment() {
    private var _binding: DialogFragmentAskDeleteScheduleBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentAskDeleteScheduleBinding.inflate(inflater, container, false)

        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.btnNoDelete.setOnClickListener {
            requireActivity().finish()
        }

        binding.btnYesDelete.setOnClickListener {
            // TODO: 일정을 삭제했을 때 API 연동 구현하기
        }
    }

    companion object {
        fun newInstance(): AskDeleteScheduleDialogFragment =
            AskDeleteScheduleDialogFragment().apply {

            }

        const val TAG = "AskDeleteScheduleDialog"
    }
}