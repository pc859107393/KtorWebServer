package acheng1314.cn.util.okhttp.utils

import javax.net.ssl.*
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

/**
 * @author cheng
 */
object SSLUtil {
    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    fun createSSLSocketFactory(): SSLSocketFactory? {
        var sSLSocketFactory: SSLSocketFactory? = null
        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(TrustAllManager()),
                    SecureRandom())
            sSLSocketFactory = sc.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return sSLSocketFactory
    }

    class TrustAllManager : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls(0)
        }
    }

    class TrustAllHostnameVerifier : HostnameVerifier {
        override fun verify(hostname: String, session: SSLSession): Boolean {
            return true
        }
    }

}
