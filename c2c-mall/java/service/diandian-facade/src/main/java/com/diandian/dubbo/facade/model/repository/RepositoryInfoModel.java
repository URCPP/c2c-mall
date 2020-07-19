package com.diandian.dubbo.facade.model.repository;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 仓库信息
 *
 * @author zzhihong
 * @date 2019/02/22
 */
@Data
@TableName("repository_info")
public class RepositoryInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 仓库名称
     */
    @TableField("repository_name")
    private String repositoryName;

    /**
     * 联系人
     */
    @TableField("contact_name")
    private String contactName;

    /**
     * 联系电话
     */
    @TableField("contact_phone")
    private String contactPhone;

    /**
     * 联系地址
     */
    @TableField("address")
    private String address;


    /**
     * 删除标识0未删除 1已删除
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 所属店铺id
     */
    @TableField("shop_info_id")
    private String shopInfoId;


}
