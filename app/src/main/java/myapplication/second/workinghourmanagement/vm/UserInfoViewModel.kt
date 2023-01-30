package myapplication.second.workinghourmanagement.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import myapplication.second.workinghourmanagement.MyApplication
import myapplication.second.workinghourmanagement.RetrofitManager
import myapplication.second.workinghourmanagement.RetrofitService
import myapplication.second.workinghourmanagement.dto.ResultUserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    private val _phone = MutableLiveData<String>()
    private val _birth = MutableLiveData<String?>()
    private val _email = MutableLiveData<String?>()
    private val _role = MutableLiveData<String>()
    private val _uuid = MutableLiveData<String>()
    private val service: RetrofitService =
        RetrofitManager.retrofit.create(RetrofitService::class.java)

    init {
        getUserInfo()
    }

    val name: LiveData<String>
        get() = _name
    val phone: LiveData<String>
        get() = _phone
    val birth: LiveData<String?>
        get() = _birth
    val email: LiveData<String?>
        get() = _email
    val role: LiveData<String>
        get() = _role
    val uuid: LiveData<String>
        get() = _uuid

    // 회원정보 조회
    private fun getUserInfo() {
        val tok = "Bearer " + MyApplication.prefs.getString("accessToken")
        service.selectOwnerInfo(tok).enqueue(object : Callback<ResultUserInfo> {
            override fun onResponse(
                call: Call<ResultUserInfo>, response: Response<ResultUserInfo>
            ) {
                val body = response.body()
                if (body != null) {
                    if (body.statusCode == 200) {
                        val data = body.data

                        _name.value = data.name
                        _phone.value = data.phone
                        _birth.value =
                            data.birthDate?.get(0).toString() + "-" + data.birthDate?.get(1)
                                .toString() + "-" + data.birthDate?.get(2).toString()
                        _email.value = data.email
                        _role.value = data.role
                        _uuid.value = data.uuid

                        Log.d(" tag[200],", data.toString())
                    } else {
                        Log.d(" tag[400],", body.message)
                    }
                }
            }

            override fun onFailure(call: Call<ResultUserInfo>, t: Throwable) {
                Log.e("getPosts fail", "[Fail]$t")
            }
        })
    }
}