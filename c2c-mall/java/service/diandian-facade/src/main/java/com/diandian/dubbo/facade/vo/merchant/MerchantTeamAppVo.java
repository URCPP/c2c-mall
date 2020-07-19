package com.diandian.dubbo.facade.vo.merchant;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MerchantTeamAppVo implements Serializable {
    private String phone;
    private String name;
    private Integer level;
    private String avatar;

}
