package acheng1314.cn.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction

object DbQuery {

    suspend fun <T> dbQuery(
            block: () -> T): T =
            withContext(Dispatchers.IO) {
                transaction { block() }
            }
}