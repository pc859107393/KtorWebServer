package acheng1314.cn.bean

import org.joda.time.DateTime
import java.io.Serializable

/**
 * 房源信息
 */
class HousePropertyBean : Serializable {
    /**
     * id
     */
    var id: Long? = null
    /**
     * 区域ID
     */
    var areaId: Int? = null
    /**
     * 区域描述
     */
    var areaDescription: String? = null
    /**
     * 楼盘名称
     */
    var name: String? = null

    var propertyType: String? = null
    var housePeriod: String? = null
    var developer: String? = null
    var floorArea: Double? = null
    var coveredArea: Double? = null
    var plotRatio: String? = null
    var greenRate: String? = null
    var totalHouse: String? = null
    var propertyCompany: String? = null
    var propertyPrice: String? = null
    var marker: String? = null
    var transportation: String? = null
    var description: String? = null
    var level: String? = null
    var remark: String? = null
    var createTime: DateTime? = null
    var updateTime: DateTime? = null
}