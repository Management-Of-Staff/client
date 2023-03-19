package myapplication.second.workinghourmanagement.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import myapplication.second.workinghourmanagement.MyApplication
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.RetrofitManager.Companion.retrofit
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerProfileInfoBinding
import myapplication.second.workinghourmanagement.dto.ResultResponse
import myapplication.second.workinghourmanagement.dto.User
import myapplication.second.workinghourmanagement.member.LoginActivity
import myapplication.second.workinghourmanagement.member.PhoneAuthActivity
import myapplication.second.workinghourmanagement.retrofit.OwnerService
import myapplication.second.workinghourmanagement.vm.UserInfoViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class OwnerProfileInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerProfileInfoBinding
    private lateinit var service: OwnerService
    private lateinit var viewModel: UserInfoViewModel
    private lateinit var profileImg: MultipartBody.Part

    private val permissionList = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_profile_info)
        viewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this

        service = retrofit.create(OwnerService::class.java)

        bind()
        //initProfile()
    }

    private fun selectGallery() {
        val writePermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val readPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (writePermission == PackageManager.PERMISSION_DENIED ||
            readPermission == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this, permissionList, REQ_GALLERY)
        } else {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            imageResult.launch(intent)
        }
    }

    // 절대경로 변환
    private fun absolutelyPath(path: Uri?, context: Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = c?.getString(index!!)

        return result!!
    }


    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(absolutelyPath(imageUri, this))

            val requestBody: RequestBody = file.asRequestBody("image/jpeg".toMediaType())
            profileImg = createFormData("image", file.name, requestBody)

            Log.d("testProfile-b", profileImg.body.toString())
            Log.d("testProfile-h", profileImg.headers.toString())
            Log.d("testProfile-f", file.name)

//            val requestFile: RequestBody =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file)
//            profileImg = createFormData("uploaded_file", file.name, requestFile)
////            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//            profileImg = createFormData("profile", file.name, requestFile)
            binding.profileImage.setImageURI(imageUri)
        }
    }

    private fun bind() {
//        launcher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                if (result.resultCode == RESULT_OK) {
//                    val intent = checkNotNull(result.data)
//                    val imageUri = intent.data
//
//                    //            val imageUri = result.data?.data ?: return@registerForActivityResult
////
//                    val file = File(absolutelyPath(imageUri, this))
//                    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//                    profileImg =
//                        MultipartBody.Part.createFormData("profile", file.name, requestFile)
//                    binding.profileImage.setImageURI(imageUri)
////                Glide.with(this)
////                    .load(imageUri)
////                    .into(imageview)
//                }
//            }

        binding.profileImage.setOnClickListener { selectGallery() }
        binding.email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!checkEmail(s.toString())) {
                    binding.email.setTextColor(resources.getColor(R.color.error1))
                } else {
                    binding.email.setTextColor(resources.getColor(R.color.success1))
                }
            }
        })
        binding.toolbar.btnRightText.setOnClickListener {
            withDraw()
        }
        binding.btnSettingBirth.setOnClickListener {
//            val dialog = BottomSheetDialog(this)
//            dialog.setContentView(R.layout.dialog_fragment_bottom_sheet_birth)
//            val btn = dialog.findViewById<Button>(R.id.btn_confirm)
//            val birth = dialog.findViewById<Spinner>(R.id.datePicker)
//
////            btn?.setOnClickListener {
////                binding.birth.text = birth.year.toString() + "/" + (birth.month + 1).toString() + "/" + birth.dayOfMonth.toString()
////                Toast.makeText(this, "버튼 클릭", Toast.LENGTH_SHORT).show()
////                dialog.dismiss()
////            }
//            dialog.show()

            val newFragment = SetBirthDialog()
            newFragment.show(supportFragmentManager, "datePicker")
        }
        binding.btnChangePhone.setOnClickListener {
            intentPage(PhoneAuthActivity::class.java, "update")
        }
        binding.btnChangePasswd.setOnClickListener {
            intentPage(CheckCurrentPwActivity::class.java, null)
        }
        binding.btnSaveProfile.setOnClickListener {
            val birth = binding.birth.text.toString().split(".")
            val profile = HashMap<String, String>()
            profile["email"] = binding.email.text.toString()
            profile["birthDate"] = birth[0] + "-" + birth[1] + "-" + birth[2]
            updateProfile(profile)
        }
    }

    private fun withDraw() {
        service.withDraw().enqueue(object : Callback<ResultResponse> {
            override fun onResponse(
                call: Call<ResultResponse>, response: Response<ResultResponse>
            ) {
                if (response.body()!!.statusCode == 200) {
                    MyApplication.prefs.setString("accessToken", "")
                    MyApplication.prefs.setString("refreshToken", "")
                    intentPage(LoginActivity::class.java)
                }
            }

            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                Log.e("withdraw call", "실패: $t")
            }
        })
    }

    private fun intentPage(where: Class<*>) {
        val intent = Intent(this, where)
        startActivity(intent)
        finishAffinity()
    }

    private fun updateProfile(profile: HashMap<String, String>) {
        service.updateProfileOwner(profileImg, profile).enqueue(object : Callback<ResultResponse> {
            override fun onResponse(
                call: Call<ResultResponse>, response: Response<ResultResponse>
            ) {
                Log.d("tagUpdateProfile", response.raw().toString())
                Log.d("tagUpdateDetail", response.body()!!.message)
                Log.d("tagUpdateDetail", response.body()!!.statusCode.toString())
                if (response.body()!!.statusCode == 200) {
                    finish()
                    //todo 뷰 다시그리기(initProfile 에서 정보조회 콜하는걸로 바꿀까?)
                    Log.d("tagUpdateProfile", "성공")
                }
            }

            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                Log.e("updateProfile call", "실패: $t")
            }
        })
    }

    fun getDate(year: Int, month: Int, day: Int) {
        val birth = String.format(getString(R.string.birth_format), year, month + 1, day)
        binding.birth.text = birth
    }

    private fun intentPage(where: Class<*>, state: String?) {
        val intent = Intent(this, where)
        if (!state.isNullOrBlank()) {
            intent.putExtra("state", state)
        }
        startActivity(intent)
    }

    private fun initProfile() {
        val myIntent = intent
        val user = myIntent.getParcelableExtra<User>("userInfo")!!

        binding.name.text = user.name
        binding.phone.text = user.phone

        val birth = user.birthDate
        val email = user.email

        if (birth.isNullOrEmpty()) binding.birth.text = null
        else binding.birth.text =
            String.format(getString(R.string.birthDate), birth[0], birth[1], birth[2])

        if (email.isNullOrBlank()) binding.email.text = null
        else binding.email.setText(email)
    }

    fun checkEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() // 서로 패턴이 맞닝?
    }

    companion object {
        const val REQ_GALLERY = 1
    }
}