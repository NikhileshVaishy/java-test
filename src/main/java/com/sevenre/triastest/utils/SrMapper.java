package com.sevenre.triastest.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by nikhilesh on 13/07/17.
 */
public class SrMapper {
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();
    static final Logger logger = LogManager.getLogger(SrMapper.class.getName());

    public <Any> Any convertModel(Object object,Class clz){
        logger.info("request to convert object "+ object.toString()+"to "+clz);
        Any output = (Any) this.mapper.map(object, clz);
        logger.info("object "+object.toString()+" has been converted to "+output.toString());
        return output;
    }
}
