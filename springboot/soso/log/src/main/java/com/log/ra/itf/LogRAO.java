package com.log.ra.itf;

import com.common.log.LogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LogRAO {
    public void registerWarnLog(LogDTO logDTO);
}
