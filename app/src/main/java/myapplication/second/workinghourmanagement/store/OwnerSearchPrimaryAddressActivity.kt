package myapplication.second.workinghourmanagement.store

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import myapplication.second.workinghourmanagement.R
import myapplication.second.workinghourmanagement.databinding.ActivityOwnerSearchPrimaryAddressBinding

class OwnerSearchPrimaryAddressActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOwnerSearchPrimaryAddressBinding
    private lateinit var ownerSearchPrimaryAddressAdapter: OwnerSearchPrimaryAddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_search_primary_address)

        binding.lifecycleOwner = this

        setupView()
        setupListeners()
    }

    private fun setupView() {
//        initRecyclerView(binding.rvPrimaryAddressList)
    }

//    private fun initRecyclerView(recyclerView: RecyclerView) {
//        ownerSearchPrimaryAddressAdapter = OwnerSearchPrimaryAddressAdapter()
//
//        recyclerView.run {
//            setHasFixedSize(true)
//            layoutManager = LinearLayoutManager(context)
////            adapter = ownerConvertStoreAdapter
//        }
//    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.ivSearchPrimaryAddress.setOnClickListener {
            // TODO: 기본주소 검색 기능 구현
            showDaumAddressWebView()
        }
    }

    private fun showDaumAddressWebView() {

        binding.layoutTip.isVisible = false
        binding.webView.isVisible = true

        binding.webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
        }

        binding.webView.apply {
            addJavascriptInterface(WebViewData(), "php에서 적용한 name")
            webViewClient = client
            webChromeClient = chromeClient
            loadUrl("//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js")
        }
    }

    private val client: WebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return false
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            handler?.proceed()
        }
    }

    private inner class WebViewData {
        @JavascriptInterface
        fun getAddress(zoneCode: String, roadAddress: String, buildingName: String) {
            CoroutineScope(Dispatchers.Default).launch {
                withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {
                    binding.editSearchPrimaryAddress.setText("($zoneCode) $roadAddress $buildingName")
                    showDaumAddressWebView()
                }
            }
        }
    }

    private val chromeClient = object : WebChromeClient() {

        override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {

            val newWebView = WebView(this@OwnerSearchPrimaryAddressActivity)

            newWebView.settings.javaScriptEnabled = true

            val dialog = Dialog(this@OwnerSearchPrimaryAddressActivity)

            dialog.setContentView(newWebView)

            val params = dialog.window!!.attributes

            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.attributes = params
            dialog.show()

            newWebView.webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
                    super.onJsAlert(view, url, message, result)
                    return true
                }

                override fun onCloseWindow(window: WebView?) {
                    dialog.dismiss()
                }
            }

            (resultMsg!!.obj as WebView.WebViewTransport).webView = newWebView
            resultMsg.sendToTarget()

            return true
        }
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, OwnerSearchPrimaryAddressActivity::class.java)
    }

    override fun onClick(view: View) {
    }
}