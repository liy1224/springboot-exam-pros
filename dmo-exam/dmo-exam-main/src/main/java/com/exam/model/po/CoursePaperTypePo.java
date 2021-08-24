package com.exam.model.po;


import com.exam.model.entity.CoursePaperTypeEntity;
import io.swagger.annotations.ApiModelProperty;

public class CoursePaperTypePo extends CoursePaperTypeEntity {

    @ApiModelProperty("状态名称")
    private String validFlagName;

    @ApiModelProperty("更新人")
    private String udpaterName;

    @ApiModelProperty("创建人")
    private String createrName;

    @ApiModelProperty("该类型下的试卷数量")
    private Integer examNum;

    public String getValidFlagName() {
        return validFlagName;
    }

    public void setValidFlagName(String validFlagName) {
        this.validFlagName = validFlagName;
    }

    public String getUdpaterName() {
        return udpaterName;
    }

    public void setUdpaterName(String udpaterName) {
        this.udpaterName = udpaterName;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public Integer getExamNum() {
        return examNum;
    }

    public void setExamNum(Integer examNum) {
        this.examNum = examNum;
    }
}
