package tr.edu.ogu.ceng.notification.servicetests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import tr.edu.ogu.ceng.notification.dto.NotificationTypesDTO;
import tr.edu.ogu.ceng.notification.entity.NotificationTypes;
import tr.edu.ogu.ceng.notification.repository.NotificationsTypesRepo;
import tr.edu.ogu.ceng.notification.service.NotificationsTypesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
class NotificationsTypesServiceTest {



    @MockBean
    private NotificationsTypesRepo notificationTypesRepository;

    @Autowired
    private NotificationsTypesService notificationsTypesService;

    private NotificationTypes sampleNotificationType;
    private NotificationTypesDTO sampleNotificationTypeDTO;

    @BeforeEach
    void setUp() {

        sampleNotificationType = new NotificationTypes();
        sampleNotificationType.setId(1L);
        sampleNotificationType.setTypeName("TypeA");

        sampleNotificationTypeDTO = new NotificationTypesDTO();
        sampleNotificationTypeDTO.setId(1L);
        sampleNotificationTypeDTO.setTypeName("TypeA");
    }

    @Test
    void getAllNotificationTypes_ShouldReturnListOfNotificationTypesDTO() {
        List<NotificationTypes> typesList = new ArrayList<>();
        typesList.add(sampleNotificationType);
        when(notificationTypesRepository.findAll()).thenReturn(typesList);

        List<NotificationTypesDTO> result = notificationsTypesService.getAllNotificationTypes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleNotificationType.getId(), result.get(0).getId());
        verify(notificationTypesRepository, times(1)).findAll();
    }

    @Test
    void getNotificationTypeById_ShouldReturnNotificationTypesDTO_WhenTypeExists() {
        when(notificationTypesRepository.findById(1L)).thenReturn(Optional.of(sampleNotificationType));

        NotificationTypesDTO result = notificationsTypesService.getNotificationTypeById(1L);

        assertNotNull(result);
        assertEquals(sampleNotificationTypeDTO.getTypeName(), result.getTypeName());
        verify(notificationTypesRepository, times(1)).findById(1L);
    }

    @Test
    void getNotificationTypeById_ShouldThrowException_WhenTypeDoesNotExist() {
        when(notificationTypesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> notificationsTypesService.getNotificationTypeById(1L));
        verify(notificationTypesRepository, times(1)).findById(1L);
    }

    @Test
    void createNotificationType_ShouldSaveAndReturnNotificationTypesDTO() {
        when(notificationTypesRepository.save(any(NotificationTypes.class))).thenReturn(sampleNotificationType);

        NotificationTypesDTO result = notificationsTypesService.createNotificationType(sampleNotificationTypeDTO);

        assertNotNull(result);
        assertEquals(sampleNotificationTypeDTO.getTypeName(), result.getTypeName());
        verify(notificationTypesRepository, times(1)).save(any(NotificationTypes.class));
    }

    @Test
    void updateNotificationType_ShouldUpdateAndReturnUpdatedNotificationTypesDTO_WhenTypeExists() {
        when(notificationTypesRepository.findById(1L)).thenReturn(Optional.of(sampleNotificationType));
        when(notificationTypesRepository.save(any(NotificationTypes.class))).thenReturn(sampleNotificationType);

        sampleNotificationTypeDTO.setTypeName("UpdatedType");
        NotificationTypesDTO result = notificationsTypesService.updateNotificationType(1L, sampleNotificationTypeDTO);

        assertNotNull(result);
        assertEquals("UpdatedType", result.getTypeName());
        verify(notificationTypesRepository, times(1)).findById(1L);
        verify(notificationTypesRepository, times(1)).save(any(NotificationTypes.class));
    }

    @Test
    void updateNotificationType_ShouldThrowException_WhenTypeDoesNotExist() {
        when(notificationTypesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> notificationsTypesService.updateNotificationType(1L, sampleNotificationTypeDTO));
        verify(notificationTypesRepository, times(1)).findById(1L);
    }

    @Test
    void deleteNotificationType_ShouldDeleteType_WhenTypeExists() {
        when(notificationTypesRepository.findById(1L)).thenReturn(Optional.of(sampleNotificationType));

        notificationsTypesService.deleteNotificationType(1L);

        verify(notificationTypesRepository, times(1)).findById(1L);
        verify(notificationTypesRepository, times(1)).delete(any(NotificationTypes.class));
    }

    @Test
    void deleteNotificationType_ShouldThrowException_WhenTypeDoesNotExist() {
        when(notificationTypesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> notificationsTypesService.deleteNotificationType(1L));
        verify(notificationTypesRepository, times(1)).findById(1L);
        verify(notificationTypesRepository, never()).delete(any(NotificationTypes.class));
    }
}