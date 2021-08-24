package com.exam.starter.properties.vo;

import cn.hutool.core.collection.CollUtil;
import com.exam.starter.properties.bean.BaseBean;
import com.exam.starter.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有查询条件基类
 * @author liyang
 */
public class BaseQo extends BaseBean {

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段",hidden = true)
    private String sortField;

    /**
     * 是否升序
     */
    @ApiModelProperty(value = "是否升序",hidden = true)
    private boolean asc = true;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段列表",hidden = true)
    private List<Sort> sortList;


    @Override
    public String toString() {
        return "BaseQo{" +
                "sortField='" + sortField + '\'' +
                ", asc=" + asc +
                ", sortList=" + sortList +
                '}';
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public List<Sort> getSortList() {
        return sortList;
    }

    public void setSortList(List<Sort> sortList) {
        this.sortList = sortList;
    }

    public void addSort(String fieldName){
        this.addSort(new Sort(fieldName));
    }
    public void addSort(String fieldName,boolean asc){
        this.addSort(new Sort(fieldName,asc));
    }
    public void addSort(Sort sort){
        if(this.sortList == null){
            this.sortList = new ArrayList<>();
        }
        this.sortList.add(sort);
    }
    @ApiModelProperty(value = "排序字段列表",hidden = true)
    public List<Sort> getSorts(){
        List<Sort> ls = new ArrayList<>();
        if(StringUtils.isNotBlank(this.sortField)){
            ls.add(new Sort("",sortField,asc));
        }
        if(CollUtil.isNotEmpty(this.sortList)){
            this.sortList.forEach(ls::add);
        }
        return ls;
    }
}
