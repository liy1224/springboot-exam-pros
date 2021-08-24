package com.exam.starter.properties.vo;

import com.exam.starter.properties.excepiton.CommonException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回前端结果类
 * @param <T>
 * @author liyang
 */
@ApiModel(description= "返回结果")
public class JsonResult<T> {
    @ApiModelProperty(value = "返回码：0为正确，非0为有错误。")
    private int code;
    @ApiModelProperty(value = "错误信息。")
    private String message;
    @ApiModelProperty(value = "返回的结果数据")
    private T result;


    public static <T> JsonResult<T> ok() {
        JsonResult<T> result = new JsonResult<>();
        result.setMessage("success");
        return result;
    }

    public static <T> JsonResult<T> ok(T resultObj) {
        JsonResult<T> result = new JsonResult<>();
        result.setResult(resultObj);
        result.setMessage("success");
        return result;
    }


    public static <T> JsonResult<T> error(CommonException exception) {
        JsonResult<T> result = new JsonResult<>();
        result.setCode(exception.getCode());
        result.setMessage(exception.getMessage());
        return result;
    }

    public static <T> JsonResult<T> error(int errCode, String message) {
        JsonResult<T> result = new JsonResult<>();
        result.setCode(errCode);
        result.setMessage(message);
        return result;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JsonResult{" + "code=" + code + ", message='" + message + '\'' + ", result=" + result + '}';
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.code == 0;
    }
}
