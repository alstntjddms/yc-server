package com.log.service;

import com.common.log.LogDTO;
import com.log.ra.itf.LogRAO;
import com.log.service.itf.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    private final static Logger log = LoggerFactory.getLogger(LogServiceImpl.class);
    @Autowired
    LogRAO rao;

    @Override
    public LogDTO debug(LogDTO logDTO) throws Exception {
        try{
            log.debug(logDTO.toString());
            return logDTO;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Override
    public LogDTO info(LogDTO logDTO) throws Exception {
        log.info(logDTO.toString());
        return logDTO;
    }
    @Override
    public Boolean warn(LogDTO logDTO) throws Exception{
        try {
            log.warn(logDTO.toString());
            rao.registerWarnLog(validateLogLengthCheck(logDTO));
            if(logDTO.getLog().length() > 3000)
                throw new Exception("testtest");
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private LogDTO validateLogLengthCheck(LogDTO logDTO){
        if(logDTO.getLog().length() > 2000 || logDTO.getClassName().length() > 40 || logDTO.getMethodName().length() > 40){
            if(logDTO.getLog().length() > 2000) {
                logDTO.setLog(logDTO.getLog().substring(0, 2000));
            }else if(logDTO.getClassName().length() > 40){
                logDTO.setClassName(logDTO.getClassName().substring(0, 40));
            }else if(logDTO.getMethodName().length() > 40){
                logDTO.setMethodName(logDTO.getMethodName().substring(0, 40));
            }
        }
        return logDTO;
    }
}
