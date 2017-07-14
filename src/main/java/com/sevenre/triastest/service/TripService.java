package com.sevenre.triastest.service;

import com.sevenre.triastest.Stop;
import com.sevenre.triastest.TriasService;
import com.sevenre.triastest.Trip;
import com.sevenre.triastest.exceptions.SrNotFoundException;
import com.sevenre.triastest.exceptions.XmlParsingException;
import com.sevenre.triastest.repository.StopRepository;
import com.sevenre.triastest.utils.Constants;
import com.sevenre.triastest.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nikhilesh on 14/07/17.
 */
@Service
public class TripService {

    @Autowired
    TriasService triasService;

    @Autowired
    StopRepository stopRepository;

    public Set<Trip> getTrips(String stopId, HttpHeaders headers) {
        JwtUtils.verifyHeader(headers);
        List<Stop> stop = stopRepository.findByGlobalId(stopId);
        if (stop.size() > 0) {
            return this.getTripsByStopId(stopId);
        } else {
            throw new SrNotFoundException("Stop Id not found");
        }
    }

    private Set<Trip> getTripsByStopId(String stopId) {
        Set<Trip> tripSet = null;
        try {
            String triasResponse = triasService.stopEventRequestByStopID(stopId, 3);
            tripSet = this.parseXmlResponse(triasResponse);
        } catch (IOException e) {
            throw new XmlParsingException(e.getMessage());
        }
        return tripSet;
    }

    private Set<Trip> parseXmlResponse(String triasResponse) {
        Set<Trip> tripSet = new HashSet<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(triasResponse));
            Document doc = db.parse(is);
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile(Constants.tripXPath);
            NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nl.getLength(); i++) {
                Element element = (Element) nl.item(i);
                String tripId = element.getFirstChild().getTextContent();
                String stopPointName = element.getFirstChild().getNextSibling().getFirstChild().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getTextContent();
                String time = element.getFirstChild().getNextSibling().getFirstChild().getFirstChild().getFirstChild().getNextSibling().getNextSibling().getFirstChild().getTextContent();
                String direction = element.getFirstChild().getNextSibling().getFirstChild().getNextSibling().getFirstChild().getNextSibling().getNextSibling().getNextSibling().getTextContent();
                String modeNo = element.getFirstChild().getNextSibling().getFirstChild().getNextSibling().getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getTextContent();
                String origin = element.getFirstChild().getNextSibling().getFirstChild().getNextSibling().getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getTextContent();
                String destination = element.getFirstChild().getNextSibling().getFirstChild().getNextSibling().getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getTextContent();
                Trip trip = new Trip();
                trip.setTripId(tripId);
                trip.setCurrentStop(stopPointName);
                trip.setDestinationStop(destination);
                trip.setTime(time);
                trip.setDirection(direction);
                trip.setModeNo(modeNo);
                trip.setOriginStop(origin);
                tripSet.add(trip);
            }
        } catch (SAXException e) {
            throw new XmlParsingException(e.getMessage());
        } catch (XPathExpressionException e) {
            throw new XmlParsingException(e.getMessage());
        } catch (ParserConfigurationException e) {
            throw new XmlParsingException(e.getMessage());
        } catch (IOException e) {
            throw new XmlParsingException(e.getMessage());
        }
        return tripSet;
    }
}
