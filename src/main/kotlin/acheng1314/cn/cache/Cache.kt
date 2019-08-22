package acheng1314.cn.cache


import acheng1314.cn.model.AdminMenuDTO
import acheng1314.cn.model.AdminUserDTO
import org.ehcache.config.builders.*
import org.ehcache.config.units.MemoryUnit
import org.ehcache.event.EventType
import org.ehcache.impl.config.persistence.CacheManagerPersistenceConfiguration
import java.io.File
import java.time.Duration

/**
 * 缓存包装
 */
object Cache {

    //监听器配置
    private val cacheEventListenerConfiguration = CacheEventListenerConfigurationBuilder
            .newEventListenerConfiguration(EhCacheEventListener(), EventType.EXPIRED)
            .unordered().asynchronous()

    private val adminLoginCacheConfiguration = CacheConfigurationBuilder
            .newCacheConfigurationBuilder<String, AdminUserDTO>(String::class.javaObjectType, AdminUserDTO::class.java
                    , ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(10, MemoryUnit.KB)    //堆缓存空间
                    .offheap(20, MemoryUnit.MB) //堆外缓存空间
                    .disk(500, MemoryUnit.MB, true))  //磁盘缓存空间
            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(1800))) //半个钟头的过期时间
            .withSizeOfMaxObjectGraph(1000L)
            .withSizeOfMaxObjectSize(1L, MemoryUnit.KB)
            .add(cacheEventListenerConfiguration)
            .build()

    private val adminUserCacheConfiguration = CacheConfigurationBuilder
            .newCacheConfigurationBuilder<Int, AdminUserDTO>(Int::class.javaObjectType, AdminUserDTO::class.java
                    , ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(10, MemoryUnit.KB)    //堆缓存空间
                    .offheap(20, MemoryUnit.MB) //堆外缓存空间
                    .disk(500, MemoryUnit.MB, true))  //磁盘缓存空间
            .withSizeOfMaxObjectGraph(1000L)
            .withSizeOfMaxObjectSize(1L, MemoryUnit.KB)
            .add(cacheEventListenerConfiguration)
            .build()

    private val adminMenuCacheConfiguration = CacheConfigurationBuilder
            .newCacheConfigurationBuilder<Int, AdminMenuDTO>(Int::class.javaObjectType, AdminMenuDTO::class.java
                    , ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(10, MemoryUnit.KB)    //堆缓存空间
                    .offheap(20, MemoryUnit.MB) //堆外缓存空间
                    .disk(500, MemoryUnit.MB, true))  //磁盘缓存空间
            .withSizeOfMaxObjectGraph(1000L)
            .withSizeOfMaxObjectSize(1L, MemoryUnit.KB)
            .add(cacheEventListenerConfiguration)
            .build()

    //初始化 cacheManager
    val cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .with(CacheManagerPersistenceConfiguration(File(System.getProperty("user.home") + "/ktor", "")))
            .build(true)

    /*
    * 后台admin缓存
    * */
    val adminUserLoginCache = cacheManager.createCache<String, AdminUserDTO>("adminLogin"
            , adminLoginCacheConfiguration)

    val adminIdCache = cacheManager.createCache<Int, AdminUserDTO>("adminUsers", adminUserCacheConfiguration)

    val adminMenuIdCache = cacheManager.createCache<Int, AdminMenuDTO>("adminMenus", adminMenuCacheConfiguration)
}