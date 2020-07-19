package com.diandian.dubbo.facade.dto.merchant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/2/28 14:41
 */
@Getter
@Setter
@ToString
public class MerchantOpenApplyLogQueryDTO implements Serializable {

    private Long id;

    private Long parentId;

    private Long softTypeId;

    private String name;

    private String phone;

    private String billNo;

    private String loginName;

    private String leader;

    private Integer applyType;

    private Integer auditState;

    private String treeStr;

    private String startTime;

    private String endTime;

    private Integer curPage;

    private Integer pageSize;
}
