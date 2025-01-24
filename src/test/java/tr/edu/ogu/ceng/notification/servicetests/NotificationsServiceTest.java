package tr.edu.ogu.ceng.notification.servicetests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tr.edu.ogu.ceng.notification.dto.NotificationsDTO;
import tr.edu.ogu.ceng.notification.entity.Notifications;
import tr.edu.ogu.ceng.notification.repository.NotificationsRepo;
import tr.edu.ogu.ceng.notification.service.NotificationsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
public class NotificationsServiceTest {

    @MockBean
    private NotificationsRepo notificationsRepository;

    @Autowired
    private NotificationsService notificationsService;

    private Notifications sampleNotification;
    private NotificationsDTO sampleNotificationDTO;

    @BeforeEach
    void setUp() {

        sampleNotification = new Notifications();
        sampleNotification.setId(1L);
        sampleNotification.setMessage("Test Message");
        sampleNotification.setStatus("NEW");
        sampleNotification.setCreatedAt(LocalDateTime.now());
        sampleNotification.setSentAt(LocalDateTime.now());

        sampleNotificationDTO = new NotificationsDTO(
                1L,
                "Test Message",
                "NEW",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void getNotificationById_ShouldReturnNotificationDTO_WhenNotificationExists() {
        when(notificationsRepository.findById(1L)).thenReturn(Optional.of(sampleNotification));

        NotificationsDTO result = notificationsService.getNotificationById(1L);

        assertNotNull(result);
        assertEquals(sampleNotificationDTO.getMessage(), result.getMessage());
        verify(notificationsRepository, times(1)).findById(1L);
    }

    @Test
    void getNotificationById_ShouldThrowException_WhenNotificationDoesNotExist() {
        when(notificationsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationsService.getNotificationById(1L));
        verify(notificationsRepository, times(1)).findById(1L);
    }

    @Test
    void getAllNotifications_ShouldReturnListOfNotificationsDTO() {
        List<Notifications> notificationsList = new ArrayList<>();
        notificationsList.add(sampleNotification);
        when(notificationsRepository.findAll()).thenReturn(notificationsList);

        List<NotificationsDTO> result = notificationsService.getAllNotifications();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(notificationsRepository, times(1)).findAll();
    }

    @Test
    void createNotification_ShouldSaveAndReturnNotificationDTO() {
        when(notificationsRepository.save(any(Notifications.class))).thenReturn(sampleNotification);

        NotificationsDTO result = notificationsService.createNotification(sampleNotificationDTO);

        assertNotNull(result);
        assertEquals(sampleNotificationDTO.getMessage(), result.getMessage());
        verify(notificationsRepository, times(1)).save(any(Notifications.class));
    }

    @Test
    void updateNotification_ShouldUpdateAndReturnUpdatedNotificationDTO_WhenNotificationExists() {
        when(notificationsRepository.findById(1L)).thenReturn(Optional.of(sampleNotification));
        when(notificationsRepository.save(any(Notifications.class))).thenReturn(sampleNotification);

        NotificationsDTO result = notificationsService.updateNotification(1L, sampleNotificationDTO);

        assertNotNull(result);
        assertEquals(sampleNotificationDTO.getMessage(), result.getMessage());
        verify(notificationsRepository, times(1)).findById(1L);
        verify(notificationsRepository, times(1)).save(sampleNotification);
    }

    @Test
    void updateNotification_ShouldThrowException_WhenNotificationDoesNotExist() {
        when(notificationsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationsService.updateNotification(1L, sampleNotificationDTO));
        verify(notificationsRepository, times(1)).findById(1L);
    }

    @Test
    void deleteNotification_ShouldDeleteNotification_WhenNotificationExists() {
        when(notificationsRepository.existsById(1L)).thenReturn(true);

        notificationsService.deleteNotification(1L);

        verify(notificationsRepository, times(1)).existsById(1L);
        verify(notificationsRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteNotification_ShouldThrowException_WhenNotificationDoesNotExist() {
        when(notificationsRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> notificationsService.deleteNotification(1L));
        verify(notificationsRepository, times(1)).existsById(1L);
        verify(notificationsRepository, never()).deleteById(1L);
    }
}
