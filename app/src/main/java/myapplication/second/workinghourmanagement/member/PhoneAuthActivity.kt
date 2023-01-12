package myapplication.second.workinghourmanagement.member

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import myapplication.second.workinghourmanagement.*
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityPhoneAuthBinding
import myapplication.second.workinghourmanagement.dto.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class PhoneAuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneAuthBinding
    private lateinit var auth: FirebaseAuth
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var storedVerificationId = ""   //인증완료시 부여되는 Id
    private lateinit var customDialog: CustomDialog
    private var isClickedSendBtn = false
    private lateinit var service: RetrofitService
    private var state = FINDPW
    //todo state 설정해주기!!

    private val callbacks by lazy {
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(applicationContext, "Invalid request", Toast.LENGTH_SHORT).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(applicationContext, "Too many request", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("TAG_sendMsg_callbacks", "onCodeSent:$verificationId")
                resendToken = token
                storedVerificationId = verificationId
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_auth)
        auth = Firebase.auth
        service = RetrofitManager.retrofit.create(RetrofitService::class.java)

        when (state) {
            UPDATE -> binding.titleToolbar.setText(R.string.update_phone)
            FINDPW -> binding.titleToolbar.setText(R.string.find_passwd)
            else -> binding.titleToolbar.setText(R.string.owner_join)
        }

        bind()

        setSupportActionBar(binding.toolbar)
        val actionBar: ActionBar = supportActionBar!!
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    private fun bind() {
        binding.ownerJoinEditPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isClickedSendBtn = false
                binding.ownerJoinBtnAuth.setText(R.string.certification)
            }

            override fun afterTextChanged(s: Editable?) {
                binding.ownerJoinBtnAuth.isEnabled =
                    !isClickedSendBtn && checkPhoneValidation(binding.ownerJoinEditPhone.text.toString())
            }
        })
        binding.ownerJoinBtnAuth.setOnClickListener {
            val phone = "+82" + binding.ownerJoinEditPhone.text.toString().substring(1)
            Log.d("전화번호", phone)

            if (!isClickedSendBtn) {
                sendMessage(phone)
            } else {
                resendMessage(phone, resendToken)
            }

            mCountDown().cancel()
            isClickedSendBtn = true
            binding.ownerJoinBtnAuth.isEnabled = false
            binding.ownerJoinBtnAuth.setText(R.string.resend)
            binding.ownerJoinTextRemainTime.isVisible = true
            mCountDown().start()
        }

        binding.ownerJoinEditAuthenticationNum.doAfterTextChanged {
            binding.ownerJoinBtnCheck.isEnabled =
                binding.ownerJoinEditAuthenticationNum.text.toString().length == 6
        }

        binding.ownerJoinBtnCheck.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(
                storedVerificationId,
                binding.ownerJoinEditAuthenticationNum.text.toString()
            )
            verifyPhoneNumberWithCode(credential)
        }
    }

    private fun updateRemainTime(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000

        val remainTimeText =
            String.format(getString(R.string.remainTime), remainSeconds / 60, remainSeconds % 60)
        binding.ownerJoinTextRemainTime.text = remainTimeText
    }

    private fun mCountDown(): CountDownTimer = object : CountDownTimer(90000, 100) {
        override fun onTick(millisUntilFinished: Long) {
            updateRemainTime(millisUntilFinished)
        }

        override fun onFinish() {
            updateRemainTime(0)
            binding.ownerJoinBtnAuth.isEnabled = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun verifyPhoneNumberWithCode(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //인증성공
                    customDialog = CustomDialog(this, getString(R.string.auth_success))
                    customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    customDialog.show()

                    when (state) {
                        UPDATE -> {
                            customDialog.shutdownClick.setOnClickListener {
                                updatePhone()
                                mCountDown().cancel()
                                finish()
                                customDialog.dismiss()
                            }
                        }
                        JOIN -> {
                            customDialog.shutdownClick.setOnClickListener {
                                mCountDown().cancel()
                                val myIntent = Intent(this, OwnerJoinInfoActivity::class.java)
                                myIntent.putExtra(
                                    "phone",
                                    binding.ownerJoinEditPhone.text.toString()
                                )
                                myIntent.putExtra("uuid", storedVerificationId)
                                startActivity(myIntent)
                                customDialog.dismiss()
                            }
                        }
                        FINDPW -> {
                            customDialog.shutdownClick.setOnClickListener {
                                mCountDown().cancel()
                                val myIntent = Intent(this, ResetPasswdActivity::class.java)
                                startActivity(myIntent)
                                customDialog.dismiss()
                            }
                        }
                    }

                } else {
                    //인증실패
                    customDialog = CustomDialog(this, getString(R.string.auth_fail))
                    customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    customDialog.show()
                    customDialog.shutdownClick.setOnClickListener { customDialog.dismiss() }
                }
            }
    }

    private fun updatePhone() {
        val token = "Bearer " + MyApplication.prefs.getString("accessToken")
        val phone = HashMap<String, String>()
        phone["phone"] = binding.ownerJoinEditPhone.text.toString()
        phone["uuid"] = storedVerificationId
        service.updatePhone(token, phone).enqueue(object : Callback<ResultResponse> {
            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                Log.e("updatePhone", "실패: $t")
            }

            override fun onResponse(
                call: Call<ResultResponse>, response: Response<ResultResponse>
            ) {
                if (response.body()!!.statusCode == 200) {
                    Log.d("updatePhone", "성공?")
                }
            }
        })
    }

    private fun sendMessage(phone: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)  // "+821012345678" 형식
            .setTimeout(90L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
        auth.setLanguageCode("kr")
        Toast.makeText(applicationContext, "문자를 확인하여 인증을 완료해주세요", Toast.LENGTH_SHORT).show()
    }

    private fun resendMessage(phone: String, token: PhoneAuthProvider.ForceResendingToken?) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)  // "+821012345678" 형식
            .setTimeout(90L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
        if (token != null) {
            options.setForceResendingToken(token)
        }

        PhoneAuthProvider.verifyPhoneNumber(options.build())
        auth.setLanguageCode("kr")
    }

    private fun checkPhoneValidation(pw: String): Boolean {
        return pw.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}\$".toRegex())
    }

    companion object {
        private const val UPDATE = "999"
        private const val FINDPW = "777"
        private const val JOIN = "111"
    }
}