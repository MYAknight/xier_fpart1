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
 * @since 2022-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Comments对象", description="")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "评论对应文章ID", example = "1")
    private Integer essayId;
    @ApiModelProperty(value = "评论内容", example = "这是一条评论")
    private String comment;
    @ApiModelProperty(value = "评论状态（0未审核，1审核通过，-1审核不通过）", example = "1")
    private Integer state;
    @ApiModelProperty(value = "评论ID", example = "1")
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;
    @ApiModelProperty(value = "评论对应用户ID", example = "1")
    private Integer userId;


    public Comments(Integer user_id,Integer essay_id, String comment) {
        this.userId=user_id;
        this.essayId=essay_id;
        this.comment=comment;
        this.state=0;
    }
}
