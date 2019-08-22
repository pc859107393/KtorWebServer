package acheng1314.cn.model

import org.jetbrains.exposed.sql.Table
import java.util.*

object Widgets : Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 255)
    val quantity = integer("quantity")
    val dateUpdated = datetime("dateUpdated")
}

data class Widget(
        val id: Int,
        val name: String,
        val quantity: Int,
        val dateUpdated: Date
)

data class NewWidget(
        val id: Int?,
        val name: String,
        val quantity: Int
)
