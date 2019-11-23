package acheng1314.cn.utils.okhttp.utils


/**
 * Created by pc on 2017/8/8.
 */
object TextUtil {
    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.isEmpty()
    }

    fun nullIfEmpty(str: String): String? {
        return if (isEmpty(str)) null else str
    }

    /**
     * Returns the length that the specified CharSequence would have if
     * spaces and control characters were trimmed from the start and end,
     * as by [String.trim].
     */
    fun getTrimmedLength(s: CharSequence): Int {
        val len = s.length

        var start = 0
        while (start < len && s[start] <= ' ') {
            start++
        }

        var end = len
        while (end > start && s[end - 1] <= ' ') {
            end--
        }

        return end - start
    }
}
