package acheng1314.cn.data

import java.io.Serializable

/**
 * 菜单包装
 */
data class AdminMenuDTO(
        val id: Long,
        val parentId: Long,
        val sort: Int,
        val name: String,
        val uri: String,
        val icon: String,
        val createDate: Long,
        val updateDate: Long
) : Serializable
