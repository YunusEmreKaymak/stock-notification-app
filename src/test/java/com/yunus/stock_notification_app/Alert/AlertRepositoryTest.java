//package com.yunus.stock_notification_app.Alert;
//
//import com.yunus.stock_notification_app.StockNotificationAppApplication;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(classes = StockNotificationAppApplication.class)
//@ContextConfiguration(classes = {TestEntityManager.class})
//@Transactional
//class AlertRepositoryTest {
//    @Autowired
//    AlertRepository alertRepository;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    List<AlertDto> alertDtos = new ArrayList<>();
//    List<Alert> alerts = new ArrayList<>();
//
//    @BeforeEach
//    void setUp() {
//        String aaplUUID = UUID.randomUUID().toString();
//        String nvdaUUID = UUID.randomUUID().toString();
//        alertDtos = List.of(new AlertDto(aaplUUID, "AAPL", 300.0, 100.0, true), new AlertDto(nvdaUUID, "NVDA", 200.0, 50.0, false));
//        alerts = List.of(new Alert(aaplUUID, "AAPL", 300.0, 100.0, true), new Alert(nvdaUUID, "NVDA", 200.0, 50.0, false));
//    }
//
//    @Test
//    void shouldSaveAlert() {
//        alertRepository.save(alerts.get(0));
//
//        System.out.println(alertRepository.findAll().get(0).getMaxPrice());
//        System.out.println(entityManager.contains(alerts.get(0)));
//
//        assertEquals(alerts.get(0), entityManager.find(Alert.class, alerts.get(0).getId()));
//    }
//
//    @Test
//    void shouldFindAlertWhenGivenCorrectId() {
//        String uuid = UUID.randomUUID().toString();
//        Alert alert = alerts.get(0);
//        alert.setId(uuid);
//        entityManager.persist(alert);
//        Optional<Alert> alertById = alertRepository.findById(uuid);
//
//        assertTrue(alertById.isPresent());
//
//    }
//}