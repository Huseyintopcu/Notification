package tr.edu.ogu.ceng.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.edu.ogu.ceng.notification.dto.SettingsDTO;
import tr.edu.ogu.ceng.notification.service.SettingsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings")
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping
    public List<SettingsDTO> getAllSettings() {
        return settingsService.getAllSettings();
    }

    // Get a setting by id
    @GetMapping("/{id}")
    public SettingsDTO getSettingById(@PathVariable Long id) {
        return settingsService.getSettingById(id);
    }

    // Create a new setting
    @PostMapping
    public SettingsDTO createSetting(@RequestBody SettingsDTO settingsDTO) {
        return settingsService.createSetting(settingsDTO);
    }

    // Update an existing setting
    @PutMapping("/{id}")
    public SettingsDTO updateSetting(@PathVariable Long id, @RequestBody SettingsDTO settingsDTO) {
        return settingsService.updateSetting(id, settingsDTO);
    }

    // Delete a setting
    @DeleteMapping("/{id}")
    public void deleteSetting(@PathVariable Long id) {
        settingsService.deleteSetting(id);
    }

}
