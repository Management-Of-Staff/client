package myapplication.second.workinghourmanagement.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerImportantScheduleBinding

class OwnerImportantScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerImportantScheduleBinding
    private lateinit var ownerImportantScheduleListAdapter: OwnerImportantScheduleListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerImportantScheduleBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupView()
        setupListeners()
    }

    private fun setupView() {
        initScheduleRecyclerView(binding.rvScheduleList)
    }

    private fun initScheduleRecyclerView(recyclerView: RecyclerView) {
        ownerImportantScheduleListAdapter = OwnerImportantScheduleListAdapter()

        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.mcvCalendar.setOnDateChangedListener { widget, date, selected ->

        }

        binding.fabAddSchedule.setOnClickListener {
            intentPostSchedule()
        }
    }

    private fun intentPostSchedule() {
        val intent = OwnerPostScheduleActivity.getIntent(this)
        startActivity(intent)
    }
}