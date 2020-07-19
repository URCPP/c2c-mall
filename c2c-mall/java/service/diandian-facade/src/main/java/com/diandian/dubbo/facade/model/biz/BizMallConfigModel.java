package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 商城目录配置
 *
 * @author zweize
 * @date 2019/02/27
 */
@Data
@TableName("biz_mall_config")
public class BizMallConfigModel extends BaseModel {

	private static final long serialVersionUID = 1L;

 	/**
	 * 0文字1图片2图文
	 */
    @TableField("mall_type")
	private Integer mallType;

	/**
	 * key编码
	 */
	@TableField("mall_key")
	private String mallKey;

    /**
	 * 对应值
	 */
	@TableField("mall_value")
	private String mallValue;

 
 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;

 
}
