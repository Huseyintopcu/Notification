package tr.edu.ogu.ceng.notification.servicetests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tr.edu.ogu.ceng.notification.dto.NotificationsSettingsDTO;
import tr.edu.ogu.ceng.notification.entity.NotificationSettings;
import tr.edu.ogu.ceng.notification.repository.NotificationsSettingsRepo;
import tr.edu.ogu.ceng.notification.service.NotificationsSettingsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
class NotificationsSettingsServiceTest {


    @MockBean
    private NotificationsSettingsRepo notificationSettingsRepository;

    @Autowired
    private NotificationsSettingsService notificationsSettingsService;

    private NotificationSettings sampleNotificationSettings;
    private NotificationsSettingsDTO sampleNotificationSettingsDTO;

    @BeforeEach
    void setUp() {

        sampleNotificationSettings = new NotificationSettings();
        sampleNotificationSettings.setId(1L);
        sampleNotificationSettings.setEnabled(true);

        sampleNotificationSettingsDTO = new NotificationsSettingsDTO(
                1L,
                true
        );
    }

    @Test
    void getAllNotificationSettings_ShouldReturnListOfNotificationSettingsDTO() {
        List<NotificationSettings> settingsList = new ArrayList<>();
        settingsList.add(sampleNotificationSettings);
        when(notificationSettingsRepository.findAll()).thenReturn(settingsList);

        List<NotificationsSettingsDTO> result = notificationsSettingsService.getAllNotificationSettings();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleNotificationSettings.getId(), result.get(0).getId());
        verify(notificationSettingsRepository, times(1)).findAll();
    }

    @Test
    void getNotificationSettingById_ShouldReturnNotificationSettingsDTO_WhenSettingExists() {
        when(notificationSettingsRepository.findById(1L)).thenReturn(Optional.of(sampleNotificationSettings));

        NotificationsSettingsDTO result = notificationsSettingsService.getNotificationSettingById(1L);

        assertNotNull(result);
        assertEquals(sampleNotificationSettingsDTO.getEnabled(), result.getEnabled());
        verify(notificationSettingsRepository, times(1)).findById(1L);
    }

    @Test
    void getNotificationSettingById_ShouldThrowException_WhenSettingDoesNotExist() {
        when(notificationSettingsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationsSettingsService.getNotificationSettingById(1L));
        verify(notificationSettingsRepository, times(1)).findById(1L);
    }

    @Test
    void createNotificationSetting_ShouldSaveAndReturnNotificationSettingsDTO() {
        when(notificationSettingsRepository.save(any(NotificationSettings.class))).thenReturn(sampleNotificationSettings);

        NotificationsSettingsDTO result = notificationsSettingsService.createNotificationSetting(sampleNotificationSettingsDTO);

        assertNotNull(result);
        assertEquals(sampleNotificationSettingsDTO.getEnabled(), result.getEnabled());
        verify(notificationSettingsRepository, times(1)).save(any(NotificationSettings.class));
    }

    @Test
    void updateNotificationSetting_ShouldUpdateAndReturnUpdatedNotificationSettingsDTO_WhenSettingExists() {
        when(notificationSettingsRepository.findById(1L)).thenReturn(Optional.of(sampleNotificationSettings));
        when(notificationSettingsRepository.save(any(NotificationSettings.class))).thenReturn(sampleNotificationSettings);

        NotificationsSettingsDTO result = notificationsSettingsService.updateNotificationSetting(1L, sampleNotificationSettingsDTO);

        assertNotNull(result);
        assertEquals(sampleNotificationSettingsDTO.getEnabled(), result.getEnabled());
        verify(notificationSettingsRepository, times(1)).findById(1L);
        verify(notificationSettingsRepository, times(1)).save(any(NotificationSettings.class));
    }

    @Test
    void updateNotificationSetting_ShouldThrowException_WhenSettingDoesNotExist() {
        when(notificationSettingsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationsSettingsService.updateNotificationSetting(1L, sampleNotificationSettingsDTO));
        verify(notificationSettingsRepository, times(1)).findById(1L);
    }

    @Test
    void deleteNotificationSetting_ShouldDeleteSetting_WhenSettingExists() {
        when(notificationSettingsRepository.existsById(1L)).thenReturn(true);

        notificationsSettingsService.deleteNotificationSetting(1L);

        verify(notificationSettingsRepository, times(1)).existsById(1L);
        verify(notificationSettingsRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteNotificationSetting_ShouldThrowException_WhenSettingDoesNotExist() {
        when(notificationSettingsRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> notificationsSettingsService.deleteNotificationSetting(1L));
        verify(notificationSettingsRepository, times(1)).existsById(1L);
        verify(notificationSettingsRepository, never()).deleteById(1L);
    }
}

