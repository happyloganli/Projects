package com.udacity.catpoint.security.service;

import com.udacity.catpoint.image.service.FakeImageService;
import com.udacity.catpoint.security.service.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.awt.image.BufferedImage;

import static org.mockito.Mockito.*;

public class SecurityServiceTest {
    @Mock
    private SecurityRepository securityRepository;
    @Mock
    private FakeImageService imageService;
    private SecurityService securityService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        securityService = new SecurityService(securityRepository, imageService);
    }



    @Test
    void testSensorActivationInArmedState() {
        // Arrange
        when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED);
        when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.NO_ALARM);

        Sensor sensor = new Sensor("Sensor1", false);

        // Act
        securityService.changeSensorActivationStatus(sensor, true);

        // Assert
        verify(securityRepository).getArmingStatus();
        verify(securityRepository).getAlarmStatus();
        verify(securityRepository).updateSensor(sensor);
        assertEquals(AlarmStatus.PENDING_ALARM, securityService.getAlarmStatus());
    }

    @Test
    void testSensorActivationInPendingAlarmState() {
        // Arrange
        when(securityRepository.getArmingStatus()).thenReturn(ArmingStatus.ARMED);
        when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);

        Sensor sensor = new Sensor("Sensor2", false);

        // Act
        securityService.changeSensorActivationStatus(sensor, true);

        // Assert
        verify(securityRepository).getArmingStatus();
        verify(securityRepository).getAlarmStatus();
        verify(securityRepository).updateSensor(sensor);
        assertEquals(AlarmStatus.ALARM, securityService.getAlarmStatus());
    }

    interface ImageService {
        boolean imageContainsCat(BufferedImage image, float confidenceThreshold);
    }

    interface SecurityRepository {
        ArmingStatus getArmingStatus();
        AlarmStatus getAlarmStatus();
        void updateSensor(Sensor sensor);
    }

    static class Sensor {
        private final String name;
        private boolean active;

        public Sensor(String name, boolean active) {
            this.name = name;
            this.active = active;
        }

        public String getName() {
            return name;
        }

        public boolean getActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }

    enum AlarmStatus {
        NO_ALARM, PENDING_ALARM, ALARM
    }

    enum ArmingStatus {
        DISARMED, ARMED, ARMED_HOME
    }
}
