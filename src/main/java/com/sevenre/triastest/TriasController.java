package com.sevenre.triastest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by shah on 6/23/2017.
 */
@RestController
@RequestMapping("/ws/trias")
public class TriasController {

    @GetMapping("/stopName")
    public List<Stop> findByName(String stopName) throws IOException {
        // ToDo: Search stops from database using query to search via stopName
        return null;
    }

    @GetMapping("/locName")
    public List<Stop> locByName(String locName) throws IOException {
        // ToDo: Search stops from database using query to search via locName
        return null;
    }

    @GetMapping("/trip")
    public List<Trip> findTrip(String stopId) {
        // ToDo: fetch Trip from Trias, parse XML response and map it to Trip.class and return
        return null;
    }

}
