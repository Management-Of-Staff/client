package myapplication.second.workinghourmanagement.manageStaff

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import myapplication.second.workinghourmanagement.databinding.BottomSheetAddWorkScheduleBinding

class BottomSheetAddWorkSchedule(context: Context) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAddWorkScheduleBinding
    private val mContext: Context = context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomSheetAddWorkScheduleBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.btnOk.setOnClickListener {
            //todo 근무 시간 추가 api call
            Toast.makeText(mContext, "확인", Toast.LENGTH_SHORT).show()
            dismiss()
        }
        binding.title.btnBottomSheetClose.setOnClickListener {
            Toast.makeText(mContext, "닫기", Toast.LENGTH_SHORT).show()
            dismiss()
        }
        return binding.root
    }
}