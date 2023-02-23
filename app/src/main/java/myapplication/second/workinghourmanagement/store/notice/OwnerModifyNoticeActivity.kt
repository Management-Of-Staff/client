package myapplication.second.workinghourmanagement.store.notice

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerModifyNoticeBinding

class OwnerModifyNoticeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOwnerModifyNoticeBinding
    private lateinit var service: RetrofitService
    private lateinit var modifyNoticeImageListAdapter: ModifyNoticeImageListAdapter

    private val openGalleryResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {

                val clipData = it?.data?.clipData
                val clipDataSize = clipData?.itemCount

                if (it.data == null) { // 어떤 이미지도 선택하지 않은 경우
                    Toast.makeText(this, "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show()
                } else { // 이미지를 하나라도 선택한 경우
                    if (clipData == null) { //이미지를 하나만 선택한 경우 clipData 가 null 이 올수 있음
                        val photoPath = it?.data?.data!!

//                        viewModel.addRecipePhotoList(listOf(photoPath.toString()))
                    } else {
                        clipData.let { clip ->
                            if (clipDataSize != null) {
                                if (clipDataSize > 10) {
                                    Toast.makeText(this, "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                                        .show()
                                } else { // 선택한 이미지가 1장 이상 10장 이하인 경우

                                    // 선택 한 사진수만큼 반복
                                    val photoList = (0 until clipDataSize).map { index ->
                                        val photoPath = clip.getItemAt(index).uri
                                        photoPath.toString()
                                    }

//                                    viewModel.addRecipePhotoList(photoList)
                                }
                            }
                        }
                    }
                }

                binding.rvNoticeImages.visibility = View.VISIBLE
                if (binding.editTitleNotice.text.toString().isNotEmpty()) {
                    binding.btnModifyNotice.setTextColor(ContextCompat.getColor(this, R.color.app_color))
                    binding.btnModifyNotice.isClickable = true
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_modify_notice)

        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        setupView()
        setupListeners()
    }

    private fun setupView() {
        initRecyclerView(binding.rvNoticeImages)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        modifyNoticeImageListAdapter = ModifyNoticeImageListAdapter(
            onClick = ::intentPhoto,
            onCancelClick = ::deletePhoto
        )

        recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = modifyNoticeImageListAdapter
            val spaceDecoration = HorizontalSpaceItemDecoration(1)
            removeItemDecoration(object : DividerItemDecoration(this@OwnerModifyNoticeActivity, HORIZONTAL) {

            })
            addItemDecoration(spaceDecoration)
        }
    }

    // RecyclerView Item 간 간격 조정하기 위한 클래스
    inner class HorizontalSpaceItemDecoration(private val horizontalSpaceWidth: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            val count = state.itemCount

            when (position) {
                0 -> {
                    outRect.left = horizontalSpaceWidth
                }
                count - 1 -> {
                    outRect.right = horizontalSpaceWidth
                }
                else -> {
                    outRect.left = horizontalSpaceWidth
                    outRect.right = horizontalSpaceWidth
                }
            }
        }
    }

    private fun deletePhoto(position: Int) {

    }

    private fun intentPhoto(photoUrl: String) {
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.layoutOpenGallery.setOnClickListener {
            if (checkStoragePermission()) {
                openGallery()
            }
        }

        binding.btnModifyNotice.setOnClickListener {
            modifyNotice()
        }
    }

    private fun modifyNotice() {

    }

    private fun checkStoragePermission(): Boolean {
        val readPermission = Manifest.permission.READ_EXTERNAL_STORAGE
        val writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        return if (ActivityCompat.checkSelfPermission(
                this,
                readPermission
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                writePermission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(readPermission, writePermission),
                PERMISSION_REQ_CODE
            )
            false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        }
    }

    private fun openGallery() {
        val pickIntent = Intent(Intent.ACTION_PICK)
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        pickIntent.action = Intent.ACTION_PICK
        openGalleryResultLauncher.launch(pickIntent)
    }

    companion object {
        const val PERMISSION_REQ_CODE = 1010

        fun getIntent(context: Context) =
            Intent(context, OwnerModifyNoticeActivity::class.java)
    }
}