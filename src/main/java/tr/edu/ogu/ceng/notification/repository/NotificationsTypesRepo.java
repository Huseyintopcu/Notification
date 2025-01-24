package tr.edu.ogu.ceng.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.edu.ogu.ceng.notification.entity.NotificationTypes;

@Repository
public interface NotificationsTypesRepo extends JpaRepository<NotificationTypes, Long> {
}
