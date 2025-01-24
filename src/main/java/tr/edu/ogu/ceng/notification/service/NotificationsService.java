package tr.edu.ogu.ceng.notification.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import tr.edu.ogu.ceng.notification.dto.NotificationsDTO;
import tr.edu.ogu.ceng.notification.dto.UserDTO;
import tr.edu.ogu.ceng.notification.entity.Notifications;
import tr.edu.ogu.ceng.notification.repository.NotificationsRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class NotificationsService {

    private final NotificationsRepo notificationsRepository;
    private final RestClient restClient;



    public NotificationsDTO getNotificationById(Long id) {
        Notifications notification = notificationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification with id " + id + " not found"));
        return convertToDTO(notification);
    }

    public List<NotificationsDTO> getAllNotifications() {
        return notificationsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NotificationsDTO createNotification(NotificationsDTO notificationDTO) {
        Notifications notification = convertToEntity(notificationDTO);
        Notifications savedNotification = notificationsRepository.save(notification);
        return convertToDTO(savedNotification);
    }

    @Transactional
    public NotificationsDTO updateNotification(Long id, NotificationsDTO notificationDTO) {
        Optional<Notifications> optionalNotification = notificationsRepository.findById(id);

        if (optionalNotification.isPresent()) {
            Notifications notification = optionalNotification.get();
            notification.setMessage(notificationDTO.getMessage());
            notification.setStatus(notificationDTO.getStatus());
            notification.setSentAt(notificationDTO.getSentAt());

            Notifications updatedNotification = notificationsRepository.save(notification);
            return convertToDTO(updatedNotification);
        } else {
            throw new RuntimeException("Notification with id " + id + " not found");
        }
    }

    public void deleteNotification(Long id) {
        if (notificationsRepository.existsById(id)) {
            notificationsRepository.deleteById(id);
        } else {
            throw new RuntimeException("Notification with id " + id + " not found");
        }
    }

    private NotificationsDTO convertToDTO(Notifications notification) {
        return new NotificationsDTO(
                notification.getId(),
                notification.getMessage(),
                notification.getStatus(),
                notification.getCreatedAt(),
                notification.getSentAt()
        );
    }

    private Notifications convertToEntity(NotificationsDTO notificationDTO) {
        Notifications notification = new Notifications();
        notification.setMessage(notificationDTO.getMessage());
        notification.setStatus(notificationDTO.getStatus());
        notification.setCreatedAt(notificationDTO.getCreatedAt());
        notification.setSentAt(notificationDTO.getSentAt());
        return notification;
    }

    public String getUsername() {

         UserDTO userDTO = restClient.get().uri("http://192.168.137.195:8007/api/users/testuser").accept(MediaType.APPLICATION_JSON).
                retrieve().body(UserDTO.class);

         return userDTO.getUsername();
    }
}
