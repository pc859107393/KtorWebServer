package acheng1314.cn.util.okhttp.model

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response

import java.io.IOException
import java.net.ProtocolException

/**
 * @author cheng
 */
class NoContentInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        return try {
            chain.proceed(chain.request())
        } catch (e: ProtocolException) {
            Response.Builder()
                    .request(chain.request())
                    .code(204)
                    .protocol(Protocol.HTTP_1_1)
                    .build()
        }
    }
}
