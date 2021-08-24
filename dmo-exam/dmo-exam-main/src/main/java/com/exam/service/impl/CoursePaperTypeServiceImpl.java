package com.exam.service.impl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.dao.CoursePaperTypeDao;
import com.exam.model.entity.CoursePaperTypeEntity;
import com.exam.model.po.CoursePaperTypePo;
import com.exam.service.CoursePaperTypeService;
import com.exam.starter.compoment.BeanMapper;
import com.exam.starter.properties.constant.Constant;
import com.exam.starter.properties.constant.RedisConstant;
import com.exam.starter.properties.qo.CoursePaperTypeQo;
import com.exam.starter.properties.util.DozerUtil;
import com.exam.starter.properties.util.RedisKeyUtil;
import com.exam.starter.properties.vo.CoursePaperTypeVo;
import com.exam.starter.util.PageData;
import com.exam.starter.util.RedisCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 * 试卷类型表
 * </p>
 *
 * @author liyang
 */
@Service
public class CoursePaperTypeServiceImpl extends ServiceImpl<CoursePaperTypeDao,CoursePaperTypeEntity> implements CoursePaperTypeService {

    @Autowired
    private CoursePaperTypeDao paperTypeDao;

    @Autowired
    private BeanMapper beanMapper;

    @Override
    public List<CoursePaperTypeVo> getPaperTypeDatas(){
        // key
        String cacheKey = RedisKeyUtil.getCacheKey(RedisConstant.RedisTest.HEADER_REDIS_KEY, RedisConstant.RedisTest.HEADER_REDIS_KEY_ALL);
        System.out.println("cacheKey：" + cacheKey);
        // 判断redis中是否存在
        Object o = RedisCacheUtils.get(cacheKey);
        if (ObjectUtil.isNotEmpty(o)){
            return (List<CoursePaperTypeVo>) o;
        }
        // 如果redis不存在，则从数据库中查询
        List<CoursePaperTypeEntity> typeEntities = paperTypeDao.getAllPaperTypes();
        if (CollectionUtil.isEmpty(typeEntities)){
            return new ArrayList<>();
        }
        List<CoursePaperTypeVo> vos = beanMapper.mapList(typeEntities,CoursePaperTypeVo.class);
        // 存入redis，设置有效时间
        RedisCacheUtils.set(cacheKey,vos,10, TimeUnit.MINUTES);
        return vos;
    }

    @Override
    public PageData<CoursePaperTypePo> getPaperTypeDatas(CoursePaperTypeQo typeQo){
        Page<CoursePaperTypeEntity> page = new Page<>(typeQo.getPageNum(),typeQo.getPageSize());
        IPage<CoursePaperTypePo> coursePaperTypePages = paperTypeDao.getCoursePaperTypePages(page, typeQo);
        return DozerUtil.mapPageData(coursePaperTypePages,CoursePaperTypePo.class,beanMapper);
    }

}
