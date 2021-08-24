package com.exam.starter.properties.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.exam.starter.compoment.BeanMapper;
import com.exam.starter.util.PageData;

/**
 * dozer工具类
 */
public class DozerUtil {

    public static <FROM, TO> PageData<TO> mapPageData(IPage<FROM> fromPage,
                                                      final Class<TO> toClass, BeanMapper mapper) {
        PageData<TO> toPage = new PageData<TO>();
        toPage.setTotal(fromPage.getTotal());
        toPage.setPages(fromPage.getPages());
        toPage.setPageNum(fromPage.getCurrent());
        toPage.setPageSize(fromPage.getSize());
        toPage.setStartRow(toPage.getPages() == 0 ? 0 :
                (toPage.getPageNum() - 1) * toPage.getPageSize() + 1);
        toPage.setEndRow(toPage.getPages() == 0 ? 0 :
                (toPage.getPageNum() == toPage.getPages() ?
                        toPage.getTotal() : (toPage.getPageNum()) * toPage.getPageSize()));
        if (fromPage.getRecords() != null) {
            toPage.setData(mapper.mapList(fromPage.getRecords(), toClass));
        }
        return toPage;
    }

}
