package com.diandian.dubbo.facade.dto.sys;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/2/22 16:00
 */
@Getter
@Setter
@ToString
public class OrgTypeDTO implements Serializable {

    private Integer delFlag;

    private Level level;

    public OrgTypeDTO(){}

    public OrgTypeDTO(Integer delFlag, Level level){
        this.level = level;
        this.delFlag = delFlag;
    }

    @Getter
    @Setter
    @ToString
    public static class Level implements Serializable {

        private static final long serialVersionUID = 1032641114686927227L;

        private Integer value;

        /**
         * -1-小于，0-等于，1-大于
         */
        private String compare;

        public Level(){}

        public Level(Integer value, String compare){
            this.value = value;
            this.compare = compare;
        }
    }
}
