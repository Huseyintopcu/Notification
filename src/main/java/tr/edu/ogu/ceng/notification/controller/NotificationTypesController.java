package tr.edu.ogu.ceng.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.edu.ogu.ceng.notification.dto.NotificationTypesDTO;
import tr.edu.ogu.ceng.notification.service.NotificationsTypesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification-types")
public class NotificationTypesController {

    private final NotificationsTypesService notificationTypesService;

    @GetMapping
    public List<NotificationTypesDTO> getAllNotificationTypes() {
        return notificationTypesService.getAllNotificationTypes();
    }

    @GetMapping("/{id}")
    public NotificationTypesDTO getNotificationTypeById(@PathVariable Long id) {
        return notificationTypesService.getNotificationTypeById(id);
    }

    @PostMapping
    public NotificationTypesDTO createNotificationType(@RequestBody NotificationTypesDTO notificationType) {
        return notificationTypesService.createNotificationType(notificationType);
    }

    @PutMapping("/{id}")
    public NotificationTypesDTO updateNotificationType(@PathVariable Long id, @RequestBody NotificationTypesDTO notificationTypesDTO) {
        return notificationTypesService.updateNotificationType(id, notificationTypesDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteNotificationType(@PathVariable Long id) {
        notificationTypesService.deleteNotificationType(id);
    }
}
