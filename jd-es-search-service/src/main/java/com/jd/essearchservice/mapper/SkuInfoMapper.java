package com.jd.essearchservice.mapper;

import com.jd.essearchapi.entity.SkuInfo;
import org.apache.ibatis.annotations.Mapper;


/**
 * (1)Mapper是通用mapper   tk.mybatis.mapper.common.Mapper
 * (2)Mapper必须是泛型
 */
@Mapper
public interface SkuInfoMapper {

    //从数据库中获取数据--与数据交互
   public SkuInfo selectByPrimaryKey(int id);

}
