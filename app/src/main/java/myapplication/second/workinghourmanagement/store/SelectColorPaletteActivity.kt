package myapplication.second.workinghourmanagement.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import myapplication.second.workinghourmanagement.databinding.ActivitySelectColorPaletteBinding

class SelectColorPaletteActivity: AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySelectColorPaletteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectColorPaletteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupListeners()
    }

    private fun setupView() {
        initGridLayoutView(binding.gridLayoutColorPalette)
    }

    private fun initGridLayoutView(gridLayout: GridLayout) {

    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnChooseComplete.setOnClickListener {
            // TODO: 팔레트에서 컬러를 선택한 후의 작업 구현
        }
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, SelectColorPaletteActivity::class.java)
    }

    override fun onClick(view: View) {
    }
}