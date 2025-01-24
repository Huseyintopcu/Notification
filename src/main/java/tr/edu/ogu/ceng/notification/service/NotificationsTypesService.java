
package tr.edu.ogu.ceng.notification.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.notification.dto.NotificationTypesDTO;
import tr.edu.ogu.ceng.notification.entity.NotificationTypes;
import tr.edu.ogu.ceng.notification.repository.NotificationsTypesRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class NotificationsTypesService {

    private NotificationsTypesRepo notificationTypesRepository;

    private NotificationTypesDTO mapToDTO(NotificationTypes notificationType) {
        NotificationTypesDTO dto = new NotificationTypesDTO();
        dto.setId(notificationType.getId());
        dto.setTypeName(notificationType.getTypeName());
        return dto;
    }

    private NotificationTypes mapToEntity(NotificationTypesDTO dto) {
        NotificationTypes notificationType = new NotificationTypes();
        notificationType.setId(dto.getId());
        notificationType.setTypeName(dto.getTypeName());
        return notificationType;
    }

    public List<NotificationTypesDTO> getAllNotificationTypes() {
        return notificationTypesRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public NotificationTypesDTO getNotificationTypeById(Long id) {
        NotificationTypes notificationType = notificationTypesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification type not found"));
        return mapToDTO(notificationType);
    }

    public NotificationTypesDTO createNotificationType(NotificationTypesDTO notificationTypesDTO) {
        NotificationTypes notificationType = mapToEntity(notificationTypesDTO);
        NotificationTypes savedNotificationType = notificationTypesRepository.save(notificationType);
        return mapToDTO(savedNotificationType);
    }

    public NotificationTypesDTO updateNotificationType(Long id, NotificationTypesDTO notificationTypesDTO) {
        NotificationTypes notificationType = notificationTypesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification type not found"));

        notificationType.setTypeName(notificationTypesDTO.getTypeName());

        NotificationTypes updatedNotificationType = notificationTypesRepository.save(notificationType);
        return mapToDTO(updatedNotificationType);
    }

    public void deleteNotificationType(Long id) {
        NotificationTypes notificationType = notificationTypesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification type not found"));
        notificationTypesRepository.delete(notificationType);
    }
}
