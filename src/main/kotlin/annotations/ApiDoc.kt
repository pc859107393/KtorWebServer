package annotations

import kotlin.reflect.KClass

/**
 * api注解，使用这个注解生成对应的api文档
 */
@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
annotation class ApiDoc(
        val name: String,   //名称
        val url: String,    //请求url
        val params: KClass<*> = Null::class,    //请求参数
        val result: KClass<*> = Null::class,    //返回结果
        val method: String = "get"  //请求方法
) {

    class Null
}
