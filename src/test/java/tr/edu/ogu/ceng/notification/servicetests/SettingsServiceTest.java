package tr.edu.ogu.ceng.notification.servicetests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tr.edu.ogu.ceng.notification.dto.SettingsDTO;
import tr.edu.ogu.ceng.notification.entity.Settings;
import tr.edu.ogu.ceng.notification.repository.SettingsRepo;
import tr.edu.ogu.ceng.notification.service.SettingsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class SettingsServiceTest {






    @MockBean
    private SettingsRepo settingsRepository;

    @Autowired
    private SettingsService settingsService;

    private Settings sampleSetting;
    private SettingsDTO sampleSettingDTO;

    @BeforeEach
    void setUp() {

        sampleSetting = new Settings();
        sampleSetting.setId(1L);
        sampleSetting.setSettingKey("keyA");
        sampleSetting.setValue("valueA");

        sampleSettingDTO = new SettingsDTO(sampleSetting.getId(), sampleSetting.getSettingKey(), sampleSetting.getValue());
    }

    @Test
    void getAllSettings_ShouldReturnListOfSettingsDTO() {
        List<Settings> settingsList = new ArrayList<>();
        settingsList.add(sampleSetting);
        when(settingsRepository.findAll()).thenReturn(settingsList);

        List<SettingsDTO> result = settingsService.getAllSettings();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleSetting.getId(), result.get(0).getId());
        assertEquals(sampleSetting.getSettingKey(), result.get(0).getSettingKey());
        assertEquals(sampleSetting.getValue(), result.get(0).getValue());
        verify(settingsRepository, times(1)).findAll();
    }

    @Test
    void getSettingById_ShouldReturnSettingsDTO_WhenSettingExists() {
        when(settingsRepository.findById(1L)).thenReturn(Optional.of(sampleSetting));

        SettingsDTO result = settingsService.getSettingById(1L);

        assertNotNull(result);
        assertEquals(sampleSettingDTO.getSettingKey(), result.getSettingKey());
        verify(settingsRepository, times(1)).findById(1L);
    }

    @Test
    void getSettingById_ShouldThrowException_WhenSettingDoesNotExist() {
        when(settingsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> settingsService.getSettingById(1L));
        verify(settingsRepository, times(1)).findById(1L);
    }

    @Test
    void createSetting_ShouldSaveAndReturnSettingsDTO() {
        when(settingsRepository.save(any(Settings.class))).thenReturn(sampleSetting);

        SettingsDTO result = settingsService.createSetting(sampleSettingDTO);

        assertNotNull(result);
        assertEquals(sampleSettingDTO.getSettingKey(), result.getSettingKey());
        assertEquals(sampleSettingDTO.getValue(), result.getValue());
        verify(settingsRepository, times(1)).save(any(Settings.class));
    }

    @Test
    void updateSetting_ShouldUpdateAndReturnUpdatedSettingsDTO_WhenSettingExists() {
        when(settingsRepository.findById(1L)).thenReturn(Optional.of(sampleSetting));
        when(settingsRepository.save(any(Settings.class))).thenReturn(sampleSetting);

        sampleSettingDTO.setValue("UpdatedValue");
        SettingsDTO result = settingsService.updateSetting(1L, sampleSettingDTO);

        assertNotNull(result);
        assertEquals("UpdatedValue", result.getValue());
        verify(settingsRepository, times(1)).findById(1L);
        verify(settingsRepository, times(1)).save(any(Settings.class));
    }

    @Test
    void updateSetting_ShouldThrowException_WhenSettingDoesNotExist() {
        when(settingsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> settingsService.updateSetting(1L, sampleSettingDTO));
        verify(settingsRepository, times(1)).findById(1L);
    }

    @Test
    void deleteSetting_ShouldDeleteSetting_WhenSettingExists() {
        doNothing().when(settingsRepository).deleteById(1L);

        settingsService.deleteSetting(1L);

        verify(settingsRepository, times(1)).deleteById(1L);
    }
}
