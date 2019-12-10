package acheng1314.cn.model

import org.jetbrains.exposed.dao.LongIdTable

/**
 * 楼盘信息表
 * @author cheng
 */
object HouseProperty : LongIdTable("t_house_property") {
    /**
     * 区域id
     */
    val areaId = integer("area_id")

    /**
     * 区域描述信息
     */
    val areaDescription = varchar("area_description", 32).nullable()

    /**
     * 楼盘名称
     */
    val name = varchar("name", 128)

    /**
     * 物业类型
     */
    val propertyType = varchar("property_type", 32).nullable()

    /**
     * 产权年限
     */
    val housePeriod = varchar("house_period", 32).nullable()

    /**
     * 开发商
     */
    val developer = varchar("developer", 32).nullable()

    /**
     * 占地面积
     */
    val floorArea = double("floor_area").nullable()

    /**
     * 建筑面积
     */
    val coveredArea = double("covered_area").nullable()

    /**
     * 容积率
     */
    val plotRatio = varchar("plot_ratio", 32).nullable()

    /**
     * 绿化率
     */
    val greenRate = varchar("green_rate", 32).nullable()

    /**
     * 总户数
     */
    val totalHouse = varchar("total_house", 32).nullable()

    /**
     * 物业公司
     */
    val propertyCompany = varchar("property_company", 64).nullable()

    /**
     * 物业费
     */
    val propertyPrice = varchar("property_price", 16)

    /**
     * 位置信息{"longitude"：100.10,"latitude":100.10,"title":"位置描述"}
     */
    val marker = varchar("marker", 1024)

    /**
     * 交通描述
     */
    val transportation = text("transportation")

    /**
     * 楼盘描述
     */
    val description = text("description")

    /**
     * 楼层描述
     */
    val level = varchar("level", 32)

    /**
     * 描述
     */
    val remark = varchar("remark", 1024)

    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")
}

/**
 * 楼盘其他信息
 */
object HouseOtherProperty : LongIdTable("t_house_other") {
    /**
     * 楼盘id
     */
    val propertyId = long("property_id")
    /**
     * 交通情况
     */
    val transportation = text("transportation")
    /**
     * 学校信息
     */
    val school = text("school")
    /**
     * 购物信息，商场之类的信息
     */
    val shop = text("shop")
    /**
     * 公园信息
     */
    val park = text("park")
    /**
     * 扩展信息
     */
    val other = text("other")

    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")

}