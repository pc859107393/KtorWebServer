package acheng1314.cn.service

import acheng1314.cn.db.DbQuery.dbQuery
import acheng1314.cn.model.SiteConfig
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

/**
 * 网站设置
 */
class SiteConfigService {

    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    /**
     *  插入网站设置
     */
    suspend fun put(name: String, value: String) = dbQuery {
        try {
            return@dbQuery SiteConfig.insert {
                it[SiteConfig.name] = name
                it[SiteConfig.value] = value
            }
        } catch (e: Exception) {
            logger.error(e.message)
            return@dbQuery SiteConfig.update({ SiteConfig.name eq name }) {
                it[SiteConfig.value] = value
            }
        }
    }

    /**
     * 根据名称获取网站设置
     * @param name 名称
     */
    suspend fun get(name: String) = dbQuery {
        SiteConfig.select { SiteConfig.name eq name }
                .map { resultRow -> resultRow[SiteConfig.value] }
                .last()
    }

    /**
     * 获取默认权限
     * @param name 默认权限名称
     */
    suspend fun getDefaultRuleByName(name: String) = get(name)

    /**
     * 获取默认内置管理员权限组
     */
    suspend fun getDefaultRule() = get("defRule")
}