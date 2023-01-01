package myapplication.second.workinghourmanagement.member

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerJoinInfoBinding

class OwnerJoinInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerJoinInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_join_info)

        bind()

        setSupportActionBar(binding.toolbar)
        val actionBar: ActionBar = supportActionBar!!
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    private fun bind() {
        binding.ownerJoinEditBusinessNum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val bn = binding.ownerJoinEditBusinessNum.text.toString()
                binding.ownerJoinBtnBnCheck.isEnabled = checkBusinessNumValidation(bn)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.ownerJoinBtnJoin.setOnClickListener {
            //todo 회원가입 성공여부 체크후 성공시 intent
            val intent = Intent(this, OwnerJoinCompleteActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        binding.ownerJoinEditPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = binding.ownerJoinEditPassword.text.toString()
                if (checkPasswordValidation(password)) {
                    binding.passwordValidator.setTextColor(resources.getColor(R.color.success1))
                } else {
                    binding.passwordValidator.setTextColor(resources.getColor(R.color.error1))
                }
            }
        })

        binding.ownerJoinEditPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.ownerJoinEditPassword.text.toString() != binding.ownerJoinEditPasswordConfirm.text.toString()) {
                    binding.passwordConfirmValidator.setText(R.string.password_disMatch)
                    binding.passwordConfirmValidator.setTextColor(resources.getColor(R.color.error1))
                    binding.ownerJoinBtnJoin.isEnabled = false
                } else {
                    binding.passwordConfirmValidator.setText(R.string.password_match)
                    binding.passwordConfirmValidator.setTextColor(resources.getColor(R.color.success1))

                    //todo 모든 정보 입력했는지 확인한 후 enabled = true 설정!
                    binding.ownerJoinBtnJoin.isEnabled = true
                }
            }
        })
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

    private fun checkPasswordValidation(pw: String): Boolean {
        return pw.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$".toRegex())
    }

    private fun checkBusinessNumValidation(bn: String): Boolean {
        return if (bn.length == 10) {
            val keyArr = arrayOf(1, 3, 7, 1, 3, 7, 1, 3, 5)
            var check = 0

            keyArr.forEachIndexed { index, i ->
                check += i * bn[index].digitToInt()
            }
            check += (bn[8].digitToInt() * keyArr[8]) / 10

            bn[9].digitToInt() == (10 - (check % 10))
        } else false
    }
}