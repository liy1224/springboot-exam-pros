package com.exam.controller;

import com.exam.model.po.CoursePaperTypePo;
import com.exam.service.CoursePaperTypeService;
import com.exam.starter.properties.qo.CoursePaperTypeQo;
import com.exam.starter.properties.vo.CoursePaperTypeVo;
import com.exam.starter.properties.vo.JsonResult;
import com.exam.starter.util.PageData;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @Autowired
    private CoursePaperTypeService paperTypeService;

    @GetMapping("/getDatas")
    @ApiOperation(value="get请求",notes = "get请求")
    public JsonResult<List<CoursePaperTypeVo>> getDatas(){
        List<CoursePaperTypeVo> paperTypeDatas = paperTypeService.getPaperTypeDatas();
        return JsonResult.ok(paperTypeDatas);
    }

    @PostMapping("/getPageDatas")
    @ApiOperation(value="post参数-分页查询",notes = "post参数-分页查询")
    public JsonResult<PageData<CoursePaperTypePo>> getPageDatas(@RequestBody CoursePaperTypeQo valueQo ){
        PageData<CoursePaperTypePo> typePoPageData = paperTypeService.getPaperTypeDatas(valueQo);
        return JsonResult.ok(typePoPageData);
    }
}
