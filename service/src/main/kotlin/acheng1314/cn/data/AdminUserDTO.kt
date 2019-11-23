package acheng1314.cn.data

import java.io.Serializable

/**
 * 用户包装
 */
data class AdminUserDTO(
        val id: Int,
        val name: String,
        val loginName: String,
        var password: String,
        val duty: String,
        val createDate: Long,
        val used: Boolean
) : Serializable