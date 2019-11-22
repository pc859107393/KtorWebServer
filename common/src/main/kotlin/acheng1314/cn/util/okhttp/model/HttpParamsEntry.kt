package acheng1314.cn.util.okhttp.model


/**
 * Http请求中的参数(包括请求头参数)封装
 *
 * @author kymjs (http://www.kymjs.com/) on 12/17/15.
 */
class HttpParamsEntry(var k: String?, var v: Any) : Comparable<HttpParamsEntry> {

    override fun equals(o: Any?): Boolean {
        return if (o is HttpParamsEntry) {
            k == o.k
        } else {
            super.equals(o)
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
