package com.diandian.dubbo.facade.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 机构申请信息列表对象
 * @author cjunyuan
 * @date 2019/2/20 9:25
 */
@Data
public class OrgApplyInfoListVO implements Serializable {

    private static final long serialVersionUID = 1627841679587925474L;

    private Long id;

    private String applyNo;

    private Integer applyType;

    /**
     * 上级联系人
     */
    private String parentContactName;

    /**
     * 上级联系人联系方式
     */
    private String parentPhone;

    /**
     * 上级名称
     */
    private String parentName;

    /**
     * 上级类型名称
     */
    private String parentTypeName;

    private Long orgId;

    /**
     * 申请机构名称
     */
    private String orgName;

    /**
     * 申请机构类型名称
     */
    private String orgTypeName;

    private String contactName;

    private String phone;

    private Integer auditState;

    private String auditFailReason;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 推荐机构联系人
     */
    private String recommendContactName;

    /**
     * 推荐机构联系人联系方式
     */
    private String recommendPhone;

    /**
     * 推荐机构名称
     */
    private String recommendName;

    /**
     * 推荐机构类型名称
     */
    private String recommendTypeName;
}
