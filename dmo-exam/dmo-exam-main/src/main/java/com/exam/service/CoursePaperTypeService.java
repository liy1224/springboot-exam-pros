package com.exam.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.model.entity.CoursePaperTypeEntity;
import com.exam.model.po.CoursePaperTypePo;
import com.exam.starter.properties.qo.CoursePaperTypeQo;
import com.exam.starter.properties.vo.CoursePaperTypeVo;
import com.exam.starter.util.PageData;

import java.util.List;

/**
 * <p>试卷类型表</p>
 */
public interface CoursePaperTypeService  extends IService<CoursePaperTypeEntity> {

    /**
     * 获取所有数据
     * @return
     */
    List<CoursePaperTypeVo> getPaperTypeDatas();

    /**
     * 分页查询
     * @param typeQo
     * @return
     */
    PageData<CoursePaperTypePo> getPaperTypeDatas(CoursePaperTypeQo typeQo);
}
