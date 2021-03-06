package acheng1314.cn.utils.okhttp.model


/**
 * Http请求中的参数(包括请求头参数)封装
 *
 * @author kymjs (http://www.kymjs.com/) on 12/17/15.
 */
class HttpParamsEntry(var k: String?, var v: Any) : Comparable<HttpParamsEntry> {

    override fun equals(other: Any?): Boolean {
        return if (other is HttpParamsEntry) {
            k == other.k
        } else {
            super.equals(other)
        }
    }

    override fun hashCode(): Int {
        return k!!.hashCode()
    }

    override fun compareTo(other: HttpParamsEntry): Int {
        return if (k == null) {
            -1
        } else {
            k!!.compareTo(other.k!!)
        }
    }
}
