package com.diandian.dubbo.business.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.diandian.dubbo.facade.common.util.ShardUtil;
import com.diandian.dubbo.facade.common.annotation.IgnoreShard;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动为实体创建时间和更新时间赋值
 *
 * @author x
 * @date 2018-09-18
 */
@Component
@Slf4j
public class TimeMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        this.setFieldValByName("createTime", now, metaObject);
        this.setFieldValByName("updateTime", now, metaObject);
        Object originalObject = metaObject.getOriginalObject();
        IgnoreShard annotation = originalObject.getClass().getAnnotation(IgnoreShard.class);
        if (null != annotation) {
            return;
        }
        String s = ShardUtil.resolveYearMonth(((BaseModel) originalObject).getId());
        this.setFieldValByName("shardYearMonth", Integer.valueOf(s), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        /*Object id = this.getFieldValByName("id", metaObject);
        if (null != id) {
            Object shardYearMonth = this.getFieldValByName("shardYearMonth", metaObject);
            if (null != shardYearMonth) {
                String s = IdUtil.resolveYearMonth((Long) id);
                if (!s.equals(String.valueOf(shardYearMonth))) {
                    log.error("id与分片字段shardYearMonth不匹配");
                    throw new PayException("系统异常");
                }
            }
        }*/
    }
}
