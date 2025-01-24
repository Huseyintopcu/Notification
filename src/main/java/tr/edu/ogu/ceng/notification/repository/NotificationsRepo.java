package tr.edu.ogu.ceng.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tr.edu.ogu.ceng.notification.entity.NotificationTypes;
import tr.edu.ogu.ceng.notification.entity.Notifications;

import java.util.List;

@Repository
public interface NotificationsRepo extends JpaRepository<Notifications, Long> {

    List<Notifications> findByStatus(String status);

    @Query("SELECT n FROM Notifications n WHERE n.notificationType = ?1")
    List<Notifications> findByNotificationType(NotificationTypes notificationType);

}
