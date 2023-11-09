package com.log.service.itf;

import com.common.log.LogDTO;

public interface LogService {
    public LogDTO debug(LogDTO logDTO) throws Exception;
    public LogDTO info(LogDTO logDTO) throws Exception;
    public Boolean warn(LogDTO logDTO) throws Exception;
}
