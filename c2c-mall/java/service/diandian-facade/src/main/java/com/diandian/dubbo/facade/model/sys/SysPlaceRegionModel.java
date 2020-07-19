package com.diandian.dubbo.facade.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 全国区县
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_place_region")
public class SysPlaceRegionModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 地区码
     */
    private Integer regionCode;

    /**
     * 父级地区码
     */
    private Integer parentRegionCode;

    /**
     * 地区名称
     */
    private String regionName;

    /**
     * 地区类型
     */
    private String regionType;

}
