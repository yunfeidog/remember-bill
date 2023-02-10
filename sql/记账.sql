# 记账APP数据库MYSQL

# 用户表 用户id 手机号 密码 用户名 是否被删除  创建时间 更新时间
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`
(
    `id`          INT(11)     NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `phone`       VARCHAR(11) NOT NULL COMMENT '手机号',
    `user_password`    VARCHAR(32) NOT NULL COMMENT '密码',
    `user_name`    VARCHAR(32) NOT NULL COMMENT '用户名',
    `is_delete`   TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '是否被删除 0-未删除 1-已删除',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `phone` (`phone`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT = '用户表';


# 账单表 账单id 用户id 金额(可能小数) 类型(收入/支出) 分类(数组)(餐饮/购物/交通/住房/娱乐/医疗/通讯/人情/其他)
# 日期(年月日) 商家(可为空) 备注(可为空) 是否被删除 创建时间 更新时间
DROP TABLE IF EXISTS `tb_bill`;
CREATE TABLE `tb_bill`
(
    `id`          INT(11)        NOT NULL AUTO_INCREMENT COMMENT '账单id',
    `user_id`     INT(11)        NOT NULL COMMENT '用户id',
    `money`       DECIMAL(10, 2) NOT NULL COMMENT '金额(可能小数)',
    `bill_type`        TINYINT(1)     NOT NULL COMMENT '类型(收入/支出) 0-收入 1-支出',
    `category`    VARCHAR(32)    NOT NULL COMMENT '分类(数组)(餐饮/购物/交通/住房/娱乐/医疗/通讯/人情/其他)',
    `bill_date`        DATE           NOT NULL COMMENT '日期(年月日)',
    `shop`        VARCHAR(32)    NULL     DEFAULT NULL COMMENT '商家(可为空)',
    `remark`      VARCHAR(255)   NULL     DEFAULT NULL COMMENT '备注(可为空)',
    `is_delete`   TINYINT(1)     NOT NULL DEFAULT 0 COMMENT '是否被删除 0-未删除 1-已删除',
    `create_time` DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`), # 这行是为了加快查询速度
    constraint `tb_bill_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT = '账单表';