package myapplication.second.workinghourmanagement

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test

class AuthInterceptorTest {
    private lateinit var chain: Interceptor.Chain
    private lateinit var interceptor: AuthInterceptor

//    @Before
//    fun setUp() {
//        chain = mockk(relaxed = true)
//        interceptor = AuthInterceptor()
//    }
//
//    @Test
//    fun error403() {
//        val successRawResponseJson = """
//            {
//            	"timestamp": 1676275372960,
//            	"status": 403,
//            	"error": "Forbidden",
//            	"path": "/rest/v1/owners/"
//            }
//        """.trimIndent()
//
//        val successResponseBody = successRawResponseJson.toResponseBody("application/json".toMediaType())
//        val successResponse = Response.Builder()
//            .code(200).body(successResponseBody)
//            .request(Res)
//    }
}