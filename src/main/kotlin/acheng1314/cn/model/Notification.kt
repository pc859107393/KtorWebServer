package acheng1314.cn.model

enum class ChangeType { CREATE, UPDATE, DELETE}

data class Notification<T>(val type: ChangeType, val id: Int, val entity: T)