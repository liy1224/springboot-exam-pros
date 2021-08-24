package com.exam.starter.properties.vo;

import com.exam.starter.properties.bean.BaseVo;
import io.swagger.annotations.ApiModelProperty;
/**
 * 带序号的类的基类
 * @author zhouhui
 */
public class RowVo extends BaseVo {
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Long row;

    public Long getRow() {
        return row;
    }

    public void setRow(Long row) {
        this.row = row;
    }
}
