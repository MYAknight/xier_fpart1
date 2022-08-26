package com.healthcare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_binList")
@ApiModel(value = "bin对象", description = "")
public class Bin {
    @ApiModelProperty(value = "垃圾桶编号", example = "1")
    @TableId(value = "PK_BID", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "纬度位置", example = "39.906901")
    @TableField("Blatitude")
    private String latitude;

    @ApiModelProperty(value = "经度位置", example = "116.397972")
    @TableField("Blongitude")
    private String longitude;

    @ApiModelProperty(value = "垃圾量，已有垃圾占比，默认为0", example = "0.50")
    @TableField("Blevel")
    private double level;

    @ApiModelProperty(value = "垃圾桶状态(0为异常，1为正常，默认为1)", example = "1")
    @TableField("Bstate")
    private int state;

    @ApiModelProperty(value = "垃圾桶位置文字描述", example = "长江路6号十字路口前")
    @TableField("BotherThing")
    private String otherThing;
}
