package com.diandian.admin.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author x
 * @date 2018/9/12 17:39
 */
@Getter
@Setter
@ToString
public abstract class BaseModel implements Serializable {


    @TableId
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "create_time" ,fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
