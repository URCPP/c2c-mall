package com.diandian.dubbo.facade.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 会员信息表
 *
 * @author wbc
 * @date 2019/02/13
 */
@Data
@TableName("member_info")
public class MemberInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;


    /**
     * 类型 0 普通 ；1 注册；2 储值
     */
    @TableField("type")
    private Integer type;

    /**
     * 状态 0 正常；1 禁用；2过期
     */
    @TableField("state")
    private Integer state;

    /**
     * 会员昵称
     */
    @TableField("nice_name")
    private String niceName;

    /**
     * 会员编码
     */
    @TableField("code")
    private String code;

    /**
     * 会员帐号
     */
    @TableField("account_no")
    private String accountNo;

    /**
     * 登录密码
     */
    @TableField("login_password")
    private String loginPassword;

    /**
     * '盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 推荐ID
     */
    @TableField("recommend_id")
    private Long recommendId;

}
