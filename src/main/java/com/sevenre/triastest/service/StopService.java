package com.sevenre.triastest.service;

import com.sevenre.triastest.Stop;
import com.sevenre.triastest.TriasService;
import com.sevenre.triastest.dto.CreateStopDto;
import com.sevenre.triastest.dto.StopDto;
import com.sevenre.triastest.repository.StopRepository;
import com.sevenre.triastest.utils.BoundingBox;
import com.sevenre.triastest.utils.Constants;
import com.sevenre.triastest.utils.Coordinates;
import com.sevenre.triastest.utils.JwtUtils;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nikhilesh on 13/07/17.
 */
@Service
public class StopService {

    @Autowired
    TriasService triasService;

    @Autowired
    StopRepository stopRepository;

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    MapperFacade mapper = mapperFactory.getMapperFacade();

    private static final Logger logger = LogManager.getLogger(StopRepository.class);

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
    public boolean create(CreateStopDto createStopDto) throws IOException {
        Set<StopDto> stopData = null;
        try {
            stopData = this.getStopData(createStopDto);
        } catch (IOException e) {
            throw new IOException();
        }
        for (StopDto s : stopData) {
            Stop stop = mapper.map(s, Stop.class);
            stopRepository.save(stop);
        }

        return true;
    }

    private Set<StopDto> getStopData(CreateStopDto createStopDto) throws IOException {
        BoundingBox bb = new BoundingBox();
        bb.setLat1(createStopDto.getLat1());
        bb.setLon1(createStopDto.getLon1());
        bb.setLat2(createStopDto.getLat2());
        bb.setLon2(createStopDto.getLon2());

        List<Coordinates> points = bb.calculatePoints();
        Set<StopDto> stopData = new HashSet<>();
        for (Coordinates c : points) {
            try {
                String stops = triasService.stopLocRequestByGeo(c.getLat(), c.getLon());
                // Parse XML
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(stops));
                Document doc = db.parse(is);
                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();
                XPathExpression expr = xpath.compile(Constants.locationXPath);
                NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

                for (int i = 0; i < nl.getLength(); i++) {
                    Element element = (Element) nl.item(i);
                    String stopPointRef = element.getFirstChild().getFirstChild().getTextContent();
                    String stopPointName = element.getFirstChild().getFirstChild().getNextSibling().getFirstChild().getTextContent();
                    String locName = element.getFirstChild().getNextSibling().getFirstChild().getTextContent();
                    Double lon = Double.parseDouble(element.getFirstChild().getNextSibling().getNextSibling().getFirstChild().getTextContent());
                    Double lat = Double.parseDouble(element.getFirstChild().getNextSibling().getNextSibling().getFirstChild().getNextSibling().getTextContent());
                    StopDto stopDto = new StopDto();
                    stopDto.setGlobalId(stopPointRef);
                    stopDto.setStopName(stopPointName);
                    stopDto.setLocName(locName);
                    stopDto.setLon(lon);
                    stopDto.setLat(lat);
                    stopData.add(stopDto);
                }
            } catch (Exception e) {
                logger.info(e.getMessage());
                throw new IOException();
            }
        }
        return stopData;
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
