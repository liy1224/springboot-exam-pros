package com.exam.starter.properties.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * BasePage
 *
 * @author liyang
 */
@ApiModel(description = "分页对象")
public class BasePage extends BaseQo {

    @ApiModelProperty(value = "当前页", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页记录数", example = "10")
    private Integer pageSize = 10;

    public Integer getPageNum() { return pageNum; }

    public void setPageNum(Integer pageNum) { this.pageNum = pageNum; }

    public Integer getPageSize() { return pageSize; }

    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }

    @Override
    public String toString() {
        return super.toString() + "\n"
                +"BasePage{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
