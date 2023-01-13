package myapplication.second.workinghourmanagement.profile

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import myapplication.second.workinghourmanagement.R
import java.util.*


class SetBirthDialog: DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_fragment_bottom_sheet_birth, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(requireActivity(), this, year, month, day)
        datePickerDialog.datePicker.calendarViewShown = false

        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val activity = activity as OwnerProfileInfoActivity?
        activity?.getDate(year, month, day)
    }
}