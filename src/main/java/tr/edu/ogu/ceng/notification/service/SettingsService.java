package tr.edu.ogu.ceng.notification.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.notification.dto.SettingsDTO;
import tr.edu.ogu.ceng.notification.entity.Settings;
import tr.edu.ogu.ceng.notification.repository.SettingsRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class SettingsService {

    private final SettingsRepo settingsRepository;

    public List<SettingsDTO> getAllSettings() {
        return settingsRepository.findAll().stream()
                .map(setting -> new SettingsDTO(setting.getId(), setting.getSettingKey(), setting.getValue()))
                .collect(Collectors.toList());
    }

    public SettingsDTO getSettingById(Long id) {
        Settings setting = settingsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setting not found with id: " + id));
        return new SettingsDTO(setting.getId(), setting.getSettingKey(), setting.getValue());
    }

    public SettingsDTO createSetting(SettingsDTO settingsDTO) {
        Settings setting = new Settings();
        setting.setSettingKey(settingsDTO.getSettingKey());
        setting.setValue(settingsDTO.getValue());
        Settings savedSetting = settingsRepository.save(setting);
        return new SettingsDTO(savedSetting.getId(), savedSetting.getSettingKey(), savedSetting.getValue());
    }

    public SettingsDTO updateSetting(Long id, SettingsDTO settingsDTO) {
        Settings setting = settingsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setting not found with id: " + id));
        setting.setSettingKey(settingsDTO.getSettingKey());
        setting.setValue(settingsDTO.getValue());
        Settings updatedSetting = settingsRepository.save(setting);
        return new SettingsDTO(updatedSetting.getId(), updatedSetting.getSettingKey(), updatedSetting.getValue());
    }

    public void deleteSetting(Long id) {
        settingsRepository.deleteById(id);
    }
}
