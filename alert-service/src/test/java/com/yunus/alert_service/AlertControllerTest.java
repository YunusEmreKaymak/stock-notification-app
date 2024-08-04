package com.yunus.alert_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AlertController.class)
@AutoConfigureMockMvc
//@SpringBootTest(classes= AlertServiceApplication.class)
class AlertControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AlertService alertService;

    List<AlertDto> alerts = new ArrayList<>();
    AlertDto alertDto;

    @BeforeEach
    void setUp() {
        String aaplUUID = UUID.randomUUID().toString();
        String nvdaUUID = UUID.randomUUID().toString();
        alerts = List.of(
                new AlertDto(aaplUUID, "AAPL", 300.0, 100.0, true),
                new AlertDto(nvdaUUID, "NVDA", 200.0, 50.0, false)
        );
        alertDto = new AlertDto(aaplUUID, "AAPL", 300.0, 100.0, true);
    }

    @Test
    void shouldFindAllAlerts() throws Exception {
        String response = """
                [
                    {
                      "stockName" : "AAPL",
                      "maxPrice": 300.0,
                      "minPrice": 100.0,
                      "active": true
                    },
                    {
                      "stockName" : "NVDA",
                      "maxPrice": 200.0,
                      "minPrice": 50.0,
                      "active": false
                    }
                ]
                """;

        when(alertService.getAllAlerts()).thenReturn(alerts);

        mockMvc.perform(get("/alert"))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    void shouldFindAlertWhenGivenValidId() throws Exception {
        String uuid = UUID.randomUUID().toString();
        String response = """
                    {
                      "stockName" : "AAPL",
                      "maxPrice": 300.0,
                      "minPrice": 100.0,
                      "active": true
                    }
                """;

        when(alertService.getAlertById(uuid)).thenReturn(alertDto);

        mockMvc.perform(get("/alert/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(response));

    }

    @Test
    void shouldCreateNewAlert() throws Exception {
        String body = """

                    {
                      "stockName" : "AAPL",
                      "maxPrice": 300.0,
                      "minPrice": 100.0,
                      "active": true
                    }

                """;
        mockMvc.perform(post("/alert").contentType("application/json").content(body))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAlertWhenGivenValidId() throws Exception {
        Mockito.doNothing().when(alertService).deleteAlertById("id");

        mockMvc.perform(delete("/alert/" + "a"))
                .andExpect(status().isOk());
    }

}