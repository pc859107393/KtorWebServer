package acheng1314.cn.model

import org.jetbrains.exposed.sql.Table

object SiteConfig : Table("site_con") {
    val name = varchar("name", 512).primaryKey()
    val value = varchar("value", 4096)
}