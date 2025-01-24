package tr.edu.ogu.ceng.notification.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import tr.edu.ogu.ceng.notification.dto.NotificationsSettingsDTO;
import tr.edu.ogu.ceng.notification.entity.NotificationSettings;
import tr.edu.ogu.ceng.notification.repository.NotificationsSettingsRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class NotificationsSettingsService {

    private NotificationsSettingsRepo notificationSettingsRepository;

    public List<NotificationsSettingsDTO> getAllNotificationSettings() {
        return notificationSettingsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public NotificationsSettingsDTO getNotificationSettingById(Long id) {
        NotificationSettings notificationSettings = notificationSettingsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationSettings with id " + id + " not found"));
        return convertToDTO(notificationSettings);
    }

    public NotificationsSettingsDTO createNotificationSetting(NotificationsSettingsDTO notificationsSettingsDTO) {

        NotificationSettings notificationSettings = convertToEntity(notificationsSettingsDTO);
        NotificationSettings savedNotificationSettings = notificationSettingsRepository.save(notificationSettings);
        return convertToDTO(savedNotificationSettings);
    }

    public NotificationsSettingsDTO updateNotificationSetting(Long id, NotificationsSettingsDTO notificationsSettingsDTO) {

        Optional<NotificationSettings> optionalNotificationSettings = notificationSettingsRepository.findById(id);

        if (optionalNotificationSettings.isPresent()) {
            NotificationSettings notificationSettings = optionalNotificationSettings.get();
            notificationSettings.setEnabled(notificationsSettingsDTO.getEnabled());
            notificationSettings.setId(notificationsSettingsDTO.getId());

            NotificationSettings updated = notificationSettingsRepository.save(notificationSettings);
            return convertToDTO(updated);
        } else {
            throw new RuntimeException("NotificationSettings with id " + id + " not found");
        }
    }

    public void deleteNotificationSetting(Long id) {

        if (notificationSettingsRepository.existsById(id)) {
            notificationSettingsRepository.deleteById(id);
        } else {
            throw new RuntimeException("NotificationSettings with id " + id + " not found");
        }
    }

    private NotificationsSettingsDTO convertToDTO(NotificationSettings notificationSettings) {
        return new NotificationsSettingsDTO(
                notificationSettings.getId(),
                notificationSettings.getEnabled()
        );
    }

    private NotificationSettings convertToEntity(NotificationsSettingsDTO notificationsSettingsDTO) {

        NotificationSettings notificationSettings = new NotificationSettings();

        notificationSettings.setId(notificationsSettingsDTO.getId());
        notificationSettings.setEnabled(notificationsSettingsDTO.getEnabled());

        return notificationSettings;
    }
}
