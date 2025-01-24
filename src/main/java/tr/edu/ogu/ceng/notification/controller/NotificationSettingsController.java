package tr.edu.ogu.ceng.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.edu.ogu.ceng.notification.dto.NotificationsSettingsDTO;
import tr.edu.ogu.ceng.notification.service.NotificationsSettingsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification-settings")
public class NotificationSettingsController {

    private final NotificationsSettingsService notificationSettingsService;

    @GetMapping
    public List<NotificationsSettingsDTO> getAllNotificationSettings() {
        return notificationSettingsService.getAllNotificationSettings();
    }

    @GetMapping("/{id}")
    public NotificationsSettingsDTO getNotificationSettingById(@PathVariable Long id) {
        return notificationSettingsService.getNotificationSettingById(id);
    }

    @PostMapping
    public NotificationsSettingsDTO createNotificationSetting(@RequestBody NotificationsSettingsDTO notificationsSettingsDTO) {
        return notificationSettingsService.createNotificationSetting(notificationsSettingsDTO);
    }

    @PutMapping("/{id}")
    public NotificationsSettingsDTO updateNotificationSetting(@PathVariable Long id, @RequestBody NotificationsSettingsDTO notificationsSettingsDTO) {
        return notificationSettingsService.updateNotificationSetting(id, notificationsSettingsDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteNotificationSetting(@PathVariable Long id) {
        notificationSettingsService.deleteNotificationSetting(id);
    }
}
