package com.exam.starter.compoment;
/**
 * @auther liyang
 */

import cn.hutool.core.collection.CollUtil;
import com.exam.starter.util.PageData;
import com.exam.starter.util.StringUtils;
import com.github.dozermapper.core.Mapper;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.cglib.beans.BeanMap;

/**
 * 转换对象
 * @date 2021/08/18
 */
public class BeanMapper {

    private Mapper mapper;

    public BeanMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public <T> T map(Object source, Class<T> destinationClass) {
        return source == null ? null : this.mapper.map(source, destinationClass);
    }

    public <T> T map(Object source, Class<T> destinationClass, Set<String> includes) {
        T bean = this.map(source, destinationClass);
        if (!CollUtil.isEmpty(includes)) {
            Map<String, Object> map = beanToMap(bean, includes);
            bean = mapToBean(map, bean);
        }

        return bean;
    }

    public <FROM, TO> List<TO> mapList(List<FROM> fromList, Class<TO> toClass) {
        if (fromList == null) {
            return null;
        } else {
            List<TO> ls = new ArrayList(fromList.size());
            Iterator var4 = fromList.iterator();

            while(var4.hasNext()) {
                FROM f = (FROM) var4.next();
                ls.add(this.mapper.map(f, toClass));
            }

            return ls;
        }
    }

    public <FROM, TO> List<TO> mapList(List<FROM> fromList, Class<TO> toClass, Set<String> includes) {
        if (fromList == null) {
            return null;
        } else {
            List<TO> ls = new ArrayList(fromList.size());
            Iterator var5 = fromList.iterator();

            while(var5.hasNext()) {
                FROM f = (FROM) var5.next();
                ls.add(this.map(f, toClass, includes));
            }

            return ls;
        }
    }

    public <FROM, TO> PageData<TO> mapPageData(PageData<FROM> fromPage, Class<TO> toClass) {
        if (fromPage == null) {
            return null;
        } else {
            PageData<TO> toPage = new PageData();
            toPage.setTotal(fromPage.getTotal());
            toPage.setPages(fromPage.getPages());
            toPage.setPageNum(fromPage.getPageNum());
            toPage.setPageSize(fromPage.getPageSize());
            toPage.setStartRow(fromPage.getStartRow());
            toPage.setEndRow(fromPage.getEndRow());
            if (fromPage.getData() != null) {
                toPage.setData(this.mapList(fromPage.getData(), toClass));
            }

            return toPage;
        }
    }

    public <FROM, TO> PageData<TO> mapPageData(PageData<FROM> fromPage, Class<TO> toClass, Set<String> includes) {
        if (fromPage == null) {
            return null;
        } else {
            PageData<TO> toPage = new PageData();
            toPage.setTotal(fromPage.getTotal());
            toPage.setPages(fromPage.getPages());
            toPage.setPageNum(fromPage.getPageNum());
            toPage.setPageSize(fromPage.getPageSize());
            toPage.setStartRow(fromPage.getStartRow());
            toPage.setEndRow(fromPage.getEndRow());
            if (fromPage.getData() != null) {
                toPage.setData(this.mapList(fromPage.getData(), toClass, includes));
            }

            return toPage;
        }
    }

    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            Iterator var3 = beanMap.keySet().iterator();

            while(var3.hasNext()) {
                Object key = var3.next();
                if (key != null && beanMap.get(key) != null) {
                    map.put(StringUtils.toString(key), beanMap.get(key));
                }
            }
        }

        return map;
    }

    public static <T> Map<String, Object> beanToMap(T bean, Set<String> includes) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            Iterator var4 = beanMap.keySet().iterator();

            while(var4.hasNext()) {
                Object key = var4.next();
                if (key != null && beanMap.get(key) != null && includes.contains(StringUtils.toString(key))) {
                    map.put(StringUtils.toString(key), beanMap.get(key));
                }
            }
        }

        return map;
    }

    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        Object obj;
        try {
            obj = bean.getClass().newInstance();
        } catch (Exception var4) {
            obj = bean;
        }

        BeanMap beanMap = BeanMap.create(obj);
        beanMap.putAll(map);
        return (T) obj;
    }
}