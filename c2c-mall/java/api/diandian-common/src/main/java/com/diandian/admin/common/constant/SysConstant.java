package com.diandian.admin.common.constant;

/**
 * 系统常量
 *
 * @author x
 * @date 2018/9/12 16:13
 */
public class SysConstant {

    /**
     *  生产环境
     */
    public static final String SYS_PROFILE = "prod";

    /**
     * 用户默认头像
     */
    public static final String USER_DEFAULT_AVATAR = "https://s1.ax1x.com/2018/05/19/CcdVQP.png";

    /**
     * 状态标记
     */
    public static final String STATUS = "state";
    /**
     * 正常状态
     */
    public static final Integer STATUS_NORMAL = 0;

    /**
     * 禁用状态
     */
    public static final Integer STATUS_DISABLED = 1;

    /**
     * 删除状态
     */
    public static final Integer STATUS_DELETED = 1;

    /**
     * 超级管理用户ID
     */
    public static final Long USER_SUPPER_ADMIN = 1L;

    /**
     * 根机构
     */
    public static final Long ROOT_ORG = 1L;

    /**
     * 超级管理用户名
     */
    public static final String USER_SUPPER_ADMIN_NAME = "admin";

    /**
     * 性别 男
     */
    public static final Integer SEX_MAN = 0;

    /**
     * 性别 女
     */
    public static final Integer SEX_WOMAN = 1;

    /**
     * 删除标记
     */
    public static final String DB_DEL_FLAG = "del_flag";

    /**
     * 顶级节点ID
     */
    public static final Long BOOT_NODE = 0L;

    /**
     * 与否概念 1： 是
     */
    public static final Integer IS_YES = 1;
    /**
     * 与否概念 0： 否
     */
    public static final Integer IS_NO = 0;


    /**
     * 菜单类型
     */
    public enum MenuType {

        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private Integer value;

        MenuType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }


    /**
     * 菜单类型
     */
    public enum UserType {

        SUPER(0, "超级管理员"),
        GENERAL(1, "普通管理员");

        private Integer value;

        private String label;

        UserType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLable() {
            return this.label;
        }
    }


}

