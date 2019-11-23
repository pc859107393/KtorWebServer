package acheng1314.cn.model

import org.jetbrains.exposed.dao.IntIdTable
import java.io.Serializable

/**
 * 菜单表
 */
object AdminMenu : IntIdTable("sys_admin_menu") {
    //菜单名称
    val name = varchar("name", 50)
    //请求uri
    val uri = varchar("uri", 128).nullable()
    //图标
    val icon = varchar("icon", 128).nullable()
    //父菜单
    val parentId = integer("parent_id").default(0)

    val createDate = date("create_date")

    val updateDate = date("update_date")

    /**
     * 菜单排序
     */
    val sort = integer("sort").default(0)
}

/**
 * 新增用户菜单
 */
data class NewAdminMenu(
        val id: Int?,
        val parentId: Int,
        val sort: Int,
        val name: String,
        val uri: String,
        val icon: String,
        val createDate: Long,
        val updateDate: Long
) :Serializable