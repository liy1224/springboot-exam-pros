package com.exam.starter.properties.qo;


import com.exam.starter.properties.vo.BasePage;
import io.swagger.annotations.ApiModel;


@ApiModel(description = "试卷类型表")
public class CoursePaperTypeQo extends BasePage {

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 是否有效
     */
    private Integer validFlag;

    /**
     * 显示顺序
     */
    private Integer displayOrder;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
