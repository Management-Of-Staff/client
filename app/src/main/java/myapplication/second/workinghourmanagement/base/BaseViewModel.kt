package myapplication.second.workinghourmanagement.base

import androidx.lifecycle.ViewModel
import myapplication.second.workinghourmanagement.SingleLiveEvent

open class BaseViewModel:ViewModel() {
    protected val _networkErrorEvent = SingleLiveEvent<Throwable?>()
    val networkErrorEvent get() = _networkErrorEvent

    protected val _loadingEvent = SingleLiveEvent<Boolean>()
    val loadingEvent get() = _loadingEvent
}