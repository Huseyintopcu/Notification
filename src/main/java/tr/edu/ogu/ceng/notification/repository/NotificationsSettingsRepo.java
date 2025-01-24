package tr.edu.ogu.ceng.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.edu.ogu.ceng.notification.entity.NotificationSettings;

@Repository
public interface NotificationsSettingsRepo extends JpaRepository<NotificationSettings, Long> {
}
