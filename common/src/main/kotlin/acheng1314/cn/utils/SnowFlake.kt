package acheng1314.cn.utils

import java.util.*

object SnowFlake {

    /**
     * 起始的时间戳:这个时间戳自己随意获取，比如自己代码的时间戳
     */
    private val START_STMP = 1543903501000L

    /**
     * 每一部分占用的位数
     */
    private val SEQUENCE_BIT: Int = 12 //序列号占用的位数
    private val MACHINE_BIT: Int = 5  //机器标识占用的位数
    private val DATACENTER_BIT: Int = 5//数据中心占用的位数

    /**
     * 每一部分的最大值：先进行左移运算，再同-1进行异或运算；异或：相同位置相同结果为0，不同结果为1
     */
    /** 用位运算计算出最大支持的数据中心数量：31  */
    private val MAX_DATACENTER_NUM = -1L xor (-1L shl DATACENTER_BIT)

    /** 用位运算计算出最大支持的机器数量：31  */
    private val MAX_MACHINE_NUM = -1L xor (-1L shl MACHINE_BIT)

    /** 用位运算计算出12位能存储的最大正整数：4095  */
    private val MAX_SEQUENCE = -1L xor (-1L shl SEQUENCE_BIT)

    /**
     * 每一部分向左的位移
     */

    /** 机器标志较序列号的偏移量  */
    private val MACHINE_LEFT = SEQUENCE_BIT

    /** 数据中心较机器标志的偏移量  */
    private val DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT

    /** 时间戳较数据中心的偏移量  */
    private val TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT

    private val datacenterId: Long = 0  //数据中心
    private val machineId: Long = 0    //机器标识
    private var sequence = 0L //序列号
    private var lastStmp = -1L//上一次时间戳

    private val nextMill: Long
        get() {
            var mill = newStmp
            while (mill <= lastStmp) {
                mill = newStmp
            }
            return mill
        }

    private val newStmp: Long
        get() = Calendar.getInstance(Locale.CHINESE).timeInMillis

    /**
     * 产生下一个ID
     *
     * @return
     */
    @Synchronized
    fun nextId(): Long {
        /** 获取当前时间戳  */
        var currStmp = newStmp

        /** 如果当前时间戳小于上次时间戳则抛出异常  */
        if (currStmp < lastStmp) {
            throw RuntimeException("Clock moved backwards.  Refusing to generate id")
        }
        /** 相同毫秒内  */
        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = sequence + 1 and MAX_SEQUENCE
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {

                /** 获取下一时间的时间戳并赋值给当前时间戳  */
                currStmp = nextMill
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L
        }
        /** 当前时间戳存档记录，用于下次产生id时对比是否为相同时间戳  */
        lastStmp = currStmp


        return (currStmp - START_STMP shl TIMESTMP_LEFT //时间戳部分
                or (datacenterId shl DATACENTER_LEFT)     //数据中心部分

                or (machineId shl MACHINE_LEFT)          //机器标识部分

                or sequence)                            //序列号部分
    }
}