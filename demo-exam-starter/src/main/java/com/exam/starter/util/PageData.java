package com.exam.starter.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;


/**
 * 分页对象
 * @param <T>
 * @author liyang
 */
@ApiModel(description= "分页数据")
public class PageData<T> {
    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private long pageNum;
    /**
     * 每页记录数
     */
    @ApiModelProperty(value = "每页记录数")
    private long pageSize;
    /**
     * 起始行
     */
    @ApiModelProperty(value = "起始行")
    private long startRow;
    /**
     * 末行
     */
    @ApiModelProperty(value = "末行")
    private long endRow;
    /**
     * 总数
     */
    @ApiModelProperty(value = "总记录数")
    private long total;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private long pages;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据")
    private List<T> data = new ArrayList<T>();

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
        if (this.pageSize != 0) {
            this.pages = total % pageSize > 0 ? total / pageSize + 1 : total / pageSize;
        }
    }

    public long getStartRow() {
        return startRow;
    }

    public void setStartRow(long startRow) {
        this.startRow = startRow;
    }

    public long getEndRow() {
        return endRow;
    }

    public void setEndRow(long endRow) {
        this.endRow = endRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
        if (this.pageSize != 0) {
            this.pages = total % pageSize > 0 ? total / pageSize + 1 : total / pageSize;
        }
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", total=" + total +
                ", pages=" + pages +
                ", data=" + data +
                '}';
    }
}
