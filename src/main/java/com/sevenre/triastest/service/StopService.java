package com.sevenre.triastest.service;

import com.sevenre.triastest.Stop;
import com.sevenre.triastest.TriasService;
import com.sevenre.triastest.dto.CreateStopDto;
import com.sevenre.triastest.dto.StopDto;
import com.sevenre.triastest.repository.StopRepository;
import com.sevenre.triastest.utils.JwtUtils;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;
import java.util.*;

/**
 * Created by nikhilesh on 13/07/17.
 */
@Service
public class StopService {

    @Autowired
    TriasService triasService;

    @Autowired
    AsyncService asyncService;

    @Autowired
    StopRepository stopRepository;

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    MapperFacade mapper = mapperFactory.getMapperFacade();

    public boolean test(CreateStopDto createStopDto) {
        StopDto stopDto = new StopDto();
        stopDto.setGlobalId("1q2w3e");
        stopDto.setLocName("test");
        stopDto.setStopName("test stop");
        stopDto.setLat(42.7111);
        stopDto.setLon(7.0033);
        Stop stop = mapper.map(stopDto, Stop.class);
        stopRepository.save(stop);
        return true;
    }

    @Transactional
    public boolean create(CreateStopDto createStopDto) throws InterruptedException, ExecutionException {
        long start_millis = System.currentTimeMillis();
        Set<StopDto> stopData = asyncService.getStopData(createStopDto);
        long stop_millis = System.currentTimeMillis();
        System.out.println(stop_millis - start_millis);
        for (StopDto s : stopData) {
            Stop stop = mapper.map(s, Stop.class);
            stopRepository.save(stop);
        }
        return true;
    }

    public List<StopDto> getByStopName(String stopName, HttpHeaders headers) {
        JwtUtils.verifyHeader(headers);
        List<Stop> stop =  stopRepository.findByStopName(stopName);
        ArrayList<StopDto> stopsDto = new ArrayList<>();
        for (Stop s : stop) {
            stopsDto.add(mapper.map(s, StopDto.class));
        }

        return stopsDto;
    }

    public List<StopDto> getByLocName(String locName, HttpHeaders headers) {
        JwtUtils.verifyHeader(headers);
        List<Stop> stop =  stopRepository.findByLocName(locName);
        ArrayList<StopDto> stopsDto = new ArrayList<>();
        for (Stop s : stop) {
            stopsDto.add(mapper.map(s, StopDto.class));
        }

        return stopsDto;
    }
}
