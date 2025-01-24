package tr.edu.ogu.ceng.notification.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.*;
import tr.edu.ogu.ceng.notification.dto.NotificationsDTO;
import tr.edu.ogu.ceng.notification.service.NotificationsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
@Getter
public class NotificationController {

    private final NotificationsService notificationService;

    public NotificationController(NotificationsService notificationsService) {
        this.notificationService = notificationsService;
    }

    @GetMapping
    public List<NotificationsDTO> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public NotificationsDTO getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    @PostMapping
    public NotificationsDTO createNotification(@RequestBody NotificationsDTO notification) {
        return notificationService.createNotification(notification);
    }

    @PutMapping("/{id}")
    public NotificationsDTO updateNotification(@PathVariable Long id, @RequestBody NotificationsDTO notificationDetails) {
        return notificationService.updateNotification(id, notificationDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
    }
}
