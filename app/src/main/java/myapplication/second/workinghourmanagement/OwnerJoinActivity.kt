package myapplication.second.workinghourmanagement

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerJoinBinding
import java.util.concurrent.TimeUnit

class OwnerJoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerJoinBinding
    private lateinit var auth: FirebaseAuth
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var storedVerificationId = ""   //인증완료시 부여되는 Id
    private lateinit var customDialog: CustomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_join)
        auth = Firebase.auth

        bind()

        setSupportActionBar(binding.toolbar)
        val actionBar: ActionBar = supportActionBar!!
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    private fun bind() {
        binding.ownerJoinEditPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.ownerJoinBtnAuth.isEnabled =
                    checkPhoneValidation(binding.ownerJoinEditPhone.text.toString())
            }
        })
        binding.ownerJoinBtnAuth.setOnClickListener {
            val phone = "+82" + binding.ownerJoinEditPhone.text.toString().substring(1)
            Log.d("전화번호", phone)
            sendMessage(phone)
        }

        binding.ownerJoinEditAuthenticationNum.doAfterTextChanged {
            binding.ownerJoinBtnCheck.isEnabled =
                binding.ownerJoinEditAuthenticationNum.toString().length >= 6
        }

        binding.ownerJoinBtnCheck.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(
                storedVerificationId,
                binding.ownerJoinEditAuthenticationNum.text.toString()
            )
            verifyPhoneNumberWithCode(credential)
        }

        binding.ownerJoinBtnNext.setOnClickListener {
            val intent = Intent(this, OwnerJoinInfoActivity::class.java)
            startActivity(intent)
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
                    binding.ownerJoinBtnNext.isEnabled = true
                    customDialog = CustomDialog(this, getString(R.string.auth_success))
                    customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    customDialog.show()
                    customDialog.shutdownClick.setOnClickListener { customDialog.dismiss() }
                } else {
                    //인증실패
                    customDialog = CustomDialog(this, getString(R.string.auth_fail))
                    customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    customDialog.show()
                    customDialog.shutdownClick.setOnClickListener { customDialog.dismiss() }
                }
            }
    }

    private fun sendMessage(phone: String) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
            override fun onVerificationFailed(e: FirebaseException) {}
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("TAG_sendMsg_callbacks", "onCodeSent:$verificationId")
                resendToken = token
                storedVerificationId = verificationId
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)  // "+821012345678" 형식
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
        auth.setLanguageCode("kr")
        Toast.makeText(applicationContext, "문자를 확인하여 인증을 완료해주세요", Toast.LENGTH_SHORT).show()
    }

    private fun checkPhoneValidation(pw: String): Boolean {
        return pw.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}\$".toRegex())
    }
}