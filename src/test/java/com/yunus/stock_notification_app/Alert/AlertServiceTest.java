package com.yunus.stock_notification_app.Alert;

import com.yunus.stock_notification_app.StockNotificationAppApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= StockNotificationAppApplication.class)
class AlertServiceTest {
    @Mock
    private AlertRepository alertRepository;
    @Mock
    AlertDtoConverter alertDtoConverter;
    @InjectMocks
    private AlertService alertService;

    List<AlertDto> alertDtos = new ArrayList<>();
    List<Alert> alerts = new ArrayList<>();

    @BeforeEach
    void setUp() {
        String aaplUUID = UUID.randomUUID().toString();
        String nvdaUUID = UUID.randomUUID().toString();
        alertDtos = List.of(
                new AlertDto(aaplUUID, "AAPL", 300.0, 100.0, true),
                new AlertDto(nvdaUUID, "NVDA", 200.0, 50.0, false)
        );
        alerts = List.of(
                new Alert(aaplUUID, "AAPL", 300.0, 100.0, true),
                new Alert(nvdaUUID, "NVDA", 200.0, 50.0, false)
        );
    }

    @Test
    void shouldGetAllAlerts() {
        when(alertRepository.findAll()).thenReturn(alerts);
        when(alertDtoConverter.convert(alerts.get(0))).thenReturn(alertDtos.get(0));
        when(alertDtoConverter.convert(alerts.get(1))).thenReturn(alertDtos.get(1));

        assertEquals(alertService.getAllAlerts(), alertDtos);
    }

    @Test
    void shouldGetAlertWhenGivenValidId() {
        String uuid = UUID.randomUUID().toString();
        when(alertRepository.existsById(uuid)).thenReturn(true);
        when(alertRepository.findById(uuid)).thenReturn(Optional.ofNullable(alerts.get(0)));
        when(alertDtoConverter.convert(alerts.get(0))).thenReturn(alertDtos.get(0));

        assertEquals(alertDtos.get(0), alertService.getAlertById(uuid));
    }

}