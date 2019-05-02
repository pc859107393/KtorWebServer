package cache

import model.AdminUserDTO
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

    private val adminUserCacheConfiguration = CacheConfigurationBuilder
            .newCacheConfigurationBuilder<String, AdminUserDTO>(String::class.javaObjectType, AdminUserDTO::class.java
                    , ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(10, MemoryUnit.KB)    //堆缓存空间
                    .offheap(20, MemoryUnit.MB) //堆外缓存空间
                    .disk(500, MemoryUnit.MB, true))  //磁盘缓存空间
            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(300)))
            .withSizeOfMaxObjectGraph(1000L)
            .withSizeOfMaxObjectSize(1L, MemoryUnit.KB)
            .add(cacheEventListenerConfiguration)
            .build()

    //初始化 cacheManager
    val cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .with(CacheManagerPersistenceConfiguration(File(System.getProperty("user.home") + "/ktor", "cache")))
            .build(true)

    /*
    * 后台admin缓存
    * */
    val adminUserCache = cacheManager.createCache<String, AdminUserDTO>("adminUsers"
            , adminUserCacheConfiguration)


}