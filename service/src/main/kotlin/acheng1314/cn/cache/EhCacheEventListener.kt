package acheng1314.cn.cache

import org.ehcache.event.CacheEvent
import org.ehcache.event.CacheEventListener

class EhCacheEventListener : CacheEventListener<Any, Any> {

    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)

    override fun onEvent(event: CacheEvent<out Any, out Any>?) {
        logger.info("缓存失效")
    }

}