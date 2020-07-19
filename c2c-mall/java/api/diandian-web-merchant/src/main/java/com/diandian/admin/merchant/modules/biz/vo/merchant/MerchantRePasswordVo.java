package com.diandian.admin.merchant.modules.biz.vo.merchant;

import lombok.Data;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 18:57 2019/10/31
 * @Modified By:
 */
@Data
public class MerchantRePasswordVo {
   private String phone;
   private String code;
   private String password;
   private String rePassword;
}
