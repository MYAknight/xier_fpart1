package com.healthcare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author liubo
 * @since 2022-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Essays对象", description = "")
public class Essays implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "文章ID", example = "1")
    @TableId(value = "essay_id", type = IdType.AUTO)
    private Integer essayId;
    @ApiModelProperty(value = "标题", example = "垃圾分类知识")
    private String title;
    @ApiModelProperty(value = "文本内容", example = "文本内容文本内容文本内容文本内容文本内容")
    private String text;
    @ApiModelProperty(value = "作者", example = "百度文库")
    private String writer;
    @ApiModelProperty(value = "分类", example = "知识")
    private String classification;
    @ApiModelProperty(value = "配图", example = "http://47.107.225.197/:8098/sk/1.jpg")
    private String picture;


}
