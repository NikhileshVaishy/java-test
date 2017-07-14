package com.sevenre.triastest;

import com.sevenre.triastest.dto.CreateStopDto;
import com.sevenre.triastest.dto.StopDto;
import com.sevenre.triastest.dto.UserRequestDto;
import com.sevenre.triastest.dto.UserResponseDto;
import com.sevenre.triastest.service.AuthService;
import com.sevenre.triastest.service.StopService;
import com.sevenre.triastest.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by shah on 6/23/2017.
 */
@RestController
@RequestMapping("/ws/trias")
public class TriasController {

    @Autowired
    private StopService stopService;

    @Autowired
    private TripService tripService;

    @Autowired
    AuthService authService;

    @RequestMapping(method = RequestMethod.POST, value = "login", consumes = "application/json", produces = "application/json")
    public UserResponseDto getIdentificationToken(@Valid @RequestBody UserRequestDto userLoginRequest) {
        return authService.verifyUser(userLoginRequest);
    }

    @RequestMapping(value = "/createStop", method = RequestMethod.POST)
    public boolean createStop(@RequestBody CreateStopDto createStopDto) throws IOException {
        return stopService.create(createStopDto);
    }

    @GetMapping("/stopName")
    public List<StopDto> findByName(@RequestParam(value = "q") String stopName, @RequestHeader HttpHeaders headers) throws IOException {
        return stopService.getByStopName(stopName, headers);
    }

    @GetMapping("/locName")
    public List<StopDto> locByName(@RequestParam(value = "q") String locName, @RequestHeader HttpHeaders headers) throws IOException {
        return stopService.getByLocName(locName, headers);
    }

    @GetMapping("/trip")
    public Set<Trip> findTrip(@RequestParam(value = "q") String stopId, @RequestHeader HttpHeaders headers) throws IOException {
        return tripService.getTrips(stopId, headers);
    }
}
