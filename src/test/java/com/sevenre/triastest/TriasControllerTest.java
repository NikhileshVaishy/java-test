package com.sevenre.triastest;


import com.sevenre.triastest.service.StopService;
import com.sevenre.triastest.service.TripService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by shah on 6/24/2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=TriasTestApplication.class)
@WebMvcTest(TriasController.class)
public class TriasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StopService stopService;

    @MockBean
    TripService tripService;

    @Test
    public void testFindTrip() throws Exception {
        this.mockMvc.perform(get("/ws/trias/trip?q=de:7312:91312").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        this.mockMvc.perform(get("/ws/trias/trip").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindByName() throws Exception {
        this.mockMvc.perform(get("/ws/trias/stopName?q=Brunnenstraße").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        this.mockMvc.perform(get("/ws/trias/stopName").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLocByName() throws Exception {
        this.mockMvc.perform(get("/ws/trias/locName?q=Mutterstadt").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        this.mockMvc.perform(get("/ws/trias/locName").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isBadRequest());
    }
}
