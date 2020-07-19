package com.diandian.admin.merchant.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.admin.model.sys.SysUserTokenModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 *
 * @author x
 * @date 2018/10/29
 */
@Mapper
public interface SysUserTokenMapper extends BaseMapper<SysUserTokenModel> {

    SysUserTokenModel getByToken(String token);

}
