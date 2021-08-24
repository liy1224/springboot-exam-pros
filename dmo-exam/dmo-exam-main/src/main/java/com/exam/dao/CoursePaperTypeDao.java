package com.exam.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.model.entity.CoursePaperTypeEntity;
import com.exam.model.po.CoursePaperTypePo;
import com.exam.starter.properties.qo.CoursePaperTypeQo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 试卷类型表 Mapper 接口
 * </p>
 *
 * @author liyang
 */
public interface CoursePaperTypeDao extends BaseMapper<CoursePaperTypeEntity> {

    /**
     * 分页查询
     */
    IPage<CoursePaperTypePo> getCoursePaperTypePages(Page page, @Param("qo") CoursePaperTypeQo qo);

    /**
     * 获取所有试卷类型数据
     * @return
     */
    List<CoursePaperTypeEntity> getAllPaperTypes();

    /**
     * 根据考试类型获取
     * 试卷类型
     * @param examTypeId
     * @return
     */
    List<CoursePaperTypeEntity> getAllByExamTypeId(@Param("examTypeId") String examTypeId);


}
