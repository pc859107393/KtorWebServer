package model

import org.jetbrains.exposed.dao.IntIdTable

/**
 * 菜单表
 */
object AdminMenu : IntIdTable() {
    //菜单名称
    val name = varchar("name", 50)
    //请求uri
    val uri = varchar("uri", 128)
    //图标
    val icon = varchar("icon", 128)
    //父菜单
    val parentId = integer("parent_id").default(0)

    val createDate = date("create_date")

    val updateDate = date("update_date")

}

/**
 * 菜单包装
 */
data class AdminMenuDTO(
        val id: Int,
        val parentId: Int,
        val name: String,
        val uri: String,
        val icon: String,
        val createDate: Long,
        val updateDate: Long
)

/**
 * 新增用户菜单
 */
data class NewAdminMenu(
        val id: Int?,
        val parentId: Int,
        val name: String,
        val uri: String,
        val icon: String,
        val createDate: Long,
        val updateDate: Long
)