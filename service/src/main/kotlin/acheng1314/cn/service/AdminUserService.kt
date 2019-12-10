package acheng1314.cn.service

import acheng1314.cn.cache.Cache
import acheng1314.cn.data.AdminUserDTO
import acheng1314.cn.data.UserLoginDTO
import acheng1314.cn.db.DbQuery.dbQuery
import acheng1314.cn.exception.ForbiddenException
import acheng1314.cn.exception.NotFoundException
import acheng1314.cn.exception.UnauthorizedException
import acheng1314.cn.model.AdminUser
import acheng1314.cn.model.NewAdminUser
import acheng1314.cn.utils.CipherUtil
import org.apache.commons.lang3.StringUtils
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime

class AdminUserService {

    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    private val adminUserCache = Cache.adminUserLoginCache

    private val adminIdCache = Cache.adminIdCache

    suspend fun getTenAdmins(): List<AdminUserDTO> = dbQuery {
        AdminUser.selectAll().limit(10)
                .map {
                    val adminUser = toAdminUser(it)
                    adminIdCache.put(adminUser.id, adminUser)   //存入到id-adminUser缓存中
                    adminUser.password = ""
                    return@map adminUser
                }
    }

    fun getAllAdminUserFromCache(): Collection<AdminUserDTO> {
        val result = mutableListOf<AdminUserDTO>()
        logger.info("缓存长度 -> ${adminIdCache.count()}")
        adminIdCache.sortedByDescending { entry -> entry.key }.forEach { result.add(it.value) }
        return result
    }

    /**
     * 先从缓存中拿取数据，如果数据不存在再去数据库中读取
     */
    suspend fun getAdminsByPage(pageNum: Int, pageSize: Int = 20): List<AdminUserDTO> {
//        val result = mutableListOf<AdminUserDTO>()
//        val startIndex = if (pageNum - 1 == 0) 0 else (pageNum - 1) * pageSize
//        val endIndex = pageNum * pageSize
//        adminIdCache.sortedByDescending { entry -> entry.key }.forEachIndexed { index, entry -> if (index in (startIndex + 1)..(endIndex - 1)) result.add(entry.value) }
//        if (result.isNotEmpty()) {
//            logger.info("获取管理员分页 -> 从缓存中获取！")
//            return result
//        } else
        return dbQuery {
            logger.info("获取管理员分页 -> 从数据库中获取！")
            AdminUser.selectAll()
                    .limit(pageSize, if (pageNum - 1 > 0) pageSize * (pageNum - 1) else 0)
                    .orderBy(AdminUser.id, SortOrder.DESC)
                    .map {
                        val adminUser = toAdminUser(it)
                        adminIdCache.put(adminUser.id, adminUser)   //存入到id-adminUser缓存中
                        adminUser.password = ""
                        return@map adminUser
                    }
        }
    }

    /**
     * 更新数据库后，直接把对应id的缓存替换就行
     */
    suspend fun putAdmin(admin: NewAdminUser): AdminUserDTO {
        admin.id ?: return addAdmin(admin)
        dbQuery {
            AdminUser.update({ AdminUser.id eq admin.id }) {
                it[name] = admin.name
                it[password] = admin.password
                it[used] = admin.used
            }
        }

        dbQuery {
            AdminUser.select { AdminUser.id eq admin.id }
                    .mapNotNull { toAdminUser(it) }
                    .single()
        }.also {
            adminIdCache.put(it.id, it)
            return it
        }
    }

    /**
     * 数据库添加用户后，拿着对应信息存入redis
     */
    suspend fun addAdmin(admin: NewAdminUser): AdminUserDTO {
        var key: Int? = null
        val now = DateTime.now()
        var lowerCase = admin.password.toLowerCase()
        if (lowerCase.length == 32) lowerCase = lowerCase.substring(8, 24)
        val pwd = CipherUtil.sha256(lowerCase + now.toString())
        dbQuery {
            key = (AdminUser.insert {
                it[name] = admin.name
                it[password] = pwd
                it[duty] = admin.duty
                it[loginName] = admin.loginName
                it[used] = admin.used
                it[createDate] = now
            } get AdminUser.id)
        }
        key ?: throw Exception("添加管理员失败！")
        AdminUserDTO(key!!, admin.name, admin.loginName, pwd, admin.duty, now.millis, admin.used).also {
            adminIdCache.put(key!!, it)
            return it
        }
    }

    /**
     * 登陆成功后返回用户token
     */
    suspend fun login(userDTO: UserLoginDTO): String {
        val query = dbQuery {
            AdminUser.select { AdminUser.loginName eq userDTO.userName }
                    .map { toAdminUser(it) }
                    .singleOrNull()
        }

        query ?: throw NotFoundException("用户不存在！")

        if (!query.used) throw ForbiddenException("用户禁止登陆！")

        var lowerCase = userDTO.pwd.toLowerCase()
        if (lowerCase.length == 32) lowerCase = lowerCase.substring(8, 24)
        val sha256 = CipherUtil.sha256(lowerCase + query.createDate.toString())
        if (query.password != sha256)
            throw UnauthorizedException("用户名和密码不匹配！")
        //获取token放入缓存，上次缓存的用户还存在，则继续使用上次的
        val lastToken = adminUserCache.find { it.value.id == query.id }?.key
        val token = if (StringUtils.isNotBlank(lastToken)) lastToken!! else CipherUtil.small32md5(query.toString() + System.currentTimeMillis())
        adminUserCache.put(token, query)
        return token
    }

    private fun toAdminUser(row: ResultRow): AdminUserDTO =
            AdminUserDTO(
                    id = row[AdminUser.id],
                    name = row[AdminUser.name],
                    loginName = row[AdminUser.loginName],
                    password = row[AdminUser.password],
                    duty = row[AdminUser.duty],
                    createDate = row[AdminUser.createDate].millis,
                    used = row[AdminUser.used]
            )
}