package com.diandian.dubbo.facade.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-05 20:52
 */
@Data
@TableName("user_shop_role")
public class UserShopRoleModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long shopId;

    private Long roleId;

}