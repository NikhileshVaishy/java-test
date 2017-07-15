package com.sevenre.triastest.service;

import com.sevenre.triastest.TriasService;
import com.sevenre.triastest.dto.CreateStopDto;
import com.sevenre.triastest.dto.StopDto;
import com.sevenre.triastest.exceptions.XmlParsingException;
import com.sevenre.triastest.utils.BoundingBox;
import com.sevenre.triastest.utils.Constants;
import com.sevenre.triastest.utils.Coordinates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by nikhilesh on 14/07/17.
 */
@Component
public class AsyncService {


    private TriasService triasService = new TriasService();

    private static final Logger logger = LogManager.getLogger(AsyncService.class);

    public Set<StopDto> getStopData(CreateStopDto createStopDto) {
        long start_millis = System.currentTimeMillis();
        BoundingBox bb = new BoundingBox();
        bb.setLat1(createStopDto.getLat1());
        bb.setLon1(createStopDto.getLon1());
        bb.setLat2(createStopDto.getLat2());
        bb.setLon2(createStopDto.getLon2());

        List<Coordinates> points = bb.calculatePoints();
        Set<StopDto> stopData = new HashSet<>();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<Set<StopDto>>> futureList = new ArrayList<>();
        for(int i = 0; i < points.size(); i++) {

            int finalI = i;
            Future<Set<StopDto>> stopDataFuture = executorService.submit(new Callable<Set<StopDto>>() {
                @Override
                public Set<StopDto> call() throws Exception {
                    return parseXml(points.get(finalI));
                }
            });
            futureList.add(stopDataFuture);
        }
        for (int i=0;i<futureList.size();i++) {
            try {
                stopData.addAll(futureList.get(i).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        long stop_millis = System.currentTimeMillis();
        System.out.println("AsyncService time" + (stop_millis - start_millis));
        return stopData;
    }

    private Set<StopDto> parseXml(Coordinates c) {
        Set<StopDto> stopData = new HashSet<>();
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
            throw new XmlParsingException();
        }
        return stopData;
    }
}
