package com.exam.starter.properties.vo;


import com.exam.starter.properties.bean.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;


/**
 * 试卷类型信息
 * @author zjyz
 * @since 2021-07-07
 */
@ApiModel(description= "试卷类型信息")
public class CoursePaperTypeVo extends BaseVo {
    /**
     * 唯一标识
    */
    @ApiModelProperty(value = "唯一标识")
    private String id;


    /**
     * 类型名称
    */
    @ApiModelProperty(value = "类型名称")
    private String typeName;


    /**
     * 是否有效
    */
    @ApiModelProperty(value = "是否有效")
    private Integer validFlag;


    /**
     * 显示顺序
    */
    @ApiModelProperty(value = "显示顺序")
    private Integer displayOrder;


    /**
     * 创建时间
    */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


    /**
     * 创建者标识
    */
    @ApiModelProperty(value = "创建者标识")
    private String createUserId;


    /**
     * 更新时间
    */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    /**
     * 更新人标识
    */
    @ApiModelProperty(value = "更新人标识")
    private String updateUserId;

    /**
     * 内置标识(1 标识为系统内置）
     */
    @ApiModelProperty(value = "内置标识(1 标识为系统内置）")
    private String builtInFlag;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public Integer getValidFlag() {
        return validFlag;
    }
    public void setValidFlag(Integer validFlag) {
        this.validFlag = validFlag;
    }
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public String getCreateUserId() {
        return createUserId;
    }
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public String getUpdateUserId() {
        return updateUserId;
    }
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getBuiltInFlag() {
        return builtInFlag;
    }

    public void setBuiltInFlag(String builtInFlag) {
        this.builtInFlag = builtInFlag;
    }
}
