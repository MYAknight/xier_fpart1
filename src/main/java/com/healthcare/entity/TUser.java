package com.healthcare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author liubo
 * @since 2022-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("users")
@ApiModel(value = "Users对象", description = "")
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名", example = "小明")
    private String username;

    @ApiModelProperty(value = "积分", example = "100")
    private Integer point;

    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

    @ApiModelProperty(value = "邮箱", example = "123456@162.com")
    private String email;

    @ApiModelProperty(value = "照片url", example = "http://47.107.225.197/:8098/sk/1.jpg")
    private String photo;

    @ApiModelProperty(value = "注册时间", example = "2022-05-05 20:21:02")
    @TableField("setTime")
    private Date settime;


}
