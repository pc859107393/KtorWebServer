//package rabbitmq
//
//import com.rabbitmq.client.Connection
//import com.rabbitmq.client.ConnectionFactory
//
//
//class RabbitMQFactory {
//    companion object {
//        @Volatile
//        private var instance: RabbitMQFactory? = null
//
//        lateinit var rabbitCon: Connection
//
//        @JvmStatic
//        fun getInstance(): RabbitMQFactory? {
//            instance ?: synchronized(RabbitMQFactory::class.java) {
//                instance ?: RabbitMQFactory().also {
//                    rabbitCon = init()
//                }
//            }
//            return instance
//        }
//
//        private fun init(): Connection {
//            val connectionFactory = ConnectionFactory()
//            connectionFactory.host = "localhost"
//            connectionFactory.port = 5672
//            connectionFactory.username = "guest"
//            connectionFactory.password = "guest"
//            connectionFactory.virtualHost = "/cheng"
//            return connectionFactory.newConnection()
//        }
//    }
//}