package com.exam.starter.properties.vo;


import com.exam.starter.util.StringUtils;

/**
 * 排序字段
 * @author liyang
 */
public class Sort {

    /**
     * 别名
     */
    private String alias;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 是否升序
     */
    private boolean asc = true;

    public Sort(String alias, String fieldName, boolean asc) {
        this.alias = alias;
        this.fieldName = fieldName;
        this.asc = asc;
    }

    public Sort(String fieldName,boolean asc) {
        this("",fieldName,asc);
    }
    public Sort(String fieldName){
        this("",fieldName,true);
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public String toSql(){
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isNotBlank(alias)){
            sb.append(alias + ".");
        }
        sb.append(fieldName);
        if(this.asc){
            sb.append(" asc");
        }else{
            sb.append(" desc");
        }
        return sb.toString();
    }
}
