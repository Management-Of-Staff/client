package myapplication.second.workinghourmanagement

import android.content.Intent
import android.os.Bundle
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
import myapplication.second.workinghourmanagement.databinding.OwnerJoinActivityBinding
import java.util.concurrent.TimeUnit

class OwnerJoinActivity : AppCompatActivity() {
    private lateinit var binding: OwnerJoinActivityBinding
    private lateinit var auth: FirebaseAuth
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var storedVerificationId = ""   //인증완료시 부여되는 Id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.owner_join_activity)
        auth = Firebase.auth

        bind()

        setSupportActionBar(binding.toolbar)
        val actionBar: ActionBar = supportActionBar!!
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    private fun bind() {
        binding.ownerJoinBtnAuth.setOnClickListener {
            val phone = "+82" + binding.ownerJoinEditPhone.text.toString().substring(1)
            Log.d("전화번호", phone)
            sendMessage(phone)
        }
        //todo 인증코드는 6자리 숫자
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
                    Toast.makeText(applicationContext, "인증성공.", Toast.LENGTH_SHORT).show()

                } else {
                    //인증실패
                    Toast.makeText(applicationContext, "인증에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT)
                        .show()
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
}