package com.diandian.admin.business.modules.sys.vo;

import com.diandian.admin.model.sys.SysRoleModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/2/18 17:34
 */
@Data
public class OrgRoleVO implements Serializable {

    private static final long serialVersionUID = 7678017115246821850L;

    private String orgName;
    private List<SysRoleModel> roleList;
}
