package com.diandian.dubbo.facade.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import com.diandian.dubbo.facade.model.merchant.MerchantAccountInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantWalletInfoModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商户信息表
 *
 * @author wbc
 * @date 2019/02/14
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("merchant_info")
public class MerchantInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    @TableField("create_by")
    private Long createBy;


    /**
     * 状态 0 正常；1 禁用；2过期
     */
    @TableField("disabled_flag")
    private Integer disabledFlag;

    /**
     * 禁用理由
     */
//    @TableField("disabled_reason")
//    private String disabledReason;

    /**
     * 开通方式（0-申请开通，1-直接开通）
     */
//    @TableField("open_method")
//    private Integer openMethod;

    /**
     * 商户名称
     */
    @TableField("name")
    private String name;

    /**
     * 邀请码
     */
    @TableField("code")
    private String code;

    /**
     * 登陆账号
     */
    @TableField("login_name")
    private String loginName;

    /**
     * 等级
     * 用户等级 1:普通会员 2:商户 3:合伙人
     */
    @TableField("level")
    private Integer level;

    /**
     * 登录密码
     */
    @TableField("login_password")
    private String loginPassword;

    /**
     * 提现密码
     */
    @TableField("withdraw_password")
    private String withdrawPassword;

    /**
     * 选货密码
     */
//    @TableField("option_password")
//    private String optionPassword;

    /**
     * 盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 负责人
     */
    @TableField("leader")
    private String leader;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;




    /**
     * 推存人ID
     */
    @TableField("recommend_id")
    private Long recommendId;

    /**
     *
     */
    @TableField("recommend_type_flag")
    private Integer recommendTypeFlag;

    /**
     * 上级ID
     */
    @TableField("parent_id")
    private Long parentId;


    /**
     * 软件类型名
     */
    @TableField("soft_type_name")
    private String softTypeName;

    /**
     * 软件类型ID
     */
    @TableField("soft_type_id")
    private Long softTypeId;

    /**
     * 认证状态  0未认证 ； 1己认证
     */
    @TableField("approve_flag")
    private Integer approveFlag;

    /**
     *
     */
    @TableField("remark")
    private String remark;


    /**
     * 树结点链
     */
    @TableField("tree_str")
    private String treeStr;



    @TableField(exist = false)
    private MerchantAccountInfoModel merchantAccountInfo;

    @TableField(exist = false)
    private MerchantWalletInfoModel merchantWalletInfo;

    /**
     * 删除标志(0 正常，1 删除)
     */
    @TableField("del_flag")
    private Integer delFlag;




}
