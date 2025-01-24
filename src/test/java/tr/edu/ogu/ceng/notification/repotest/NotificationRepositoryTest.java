package tr.edu.ogu.ceng.notification.repotest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tr.edu.ogu.ceng.notification.CommonTest;
import tr.edu.ogu.ceng.notification.entity.NotificationTypes;
import tr.edu.ogu.ceng.notification.entity.Notifications;
import tr.edu.ogu.ceng.notification.entity.User;
import tr.edu.ogu.ceng.notification.repository.NotificationsRepo;
import tr.edu.ogu.ceng.notification.repository.NotificationsTypesRepo;
import tr.edu.ogu.ceng.notification.repository.UserRepo;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NotificationRepositoryTest extends CommonTest {

    @Autowired
    private NotificationsRepo repository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private NotificationsTypesRepo notificationTypeRepo;



    @Test
    void testCreateNotification() {
        // Kullanıcı ve tür oluştur
        User user = new User();
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setPhoneNumber("555-5555");
        userRepo.save(user);

        NotificationTypes type = new NotificationTypes();
        type.setTypeName("Email");
        notificationTypeRepo.save(type);

        // Yeni bildirim oluştur
        Notifications notification = new Notifications();
        notification.setUser(user);
        notification.setNotificationType(type);
        notification.setMessage("Welcome to our service!");
        notification.setStatus("pending");

        repository.save(notification);

        // Veritabanında kaydedildiğini doğrula
        assertThat(notification.getId()).isNotNull();
        assertThat(notification.getMessage()).isEqualTo("Welcome to our service!");
    }

    @Test
    void testFindById() {
        // Yeni bildirim oluştur ve kaydet
        User user = new User();
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setPhoneNumber("555-5555");
        userRepo.save(user);

        NotificationTypes type = new NotificationTypes();
        type.setTypeName("SMS");
        notificationTypeRepo.save(type);

        Notifications notification = new Notifications();
        notification.setUser(user);
        notification.setNotificationType(type);
        notification.setMessage("Test message");
        notification.setStatus("pending");

        repository.save(notification);

        // ID ile bildirim bul
        Optional<Notifications> foundNotification = repository.findById(notification.getId());

        // Doğrulama
        assertThat(foundNotification).isPresent();
        assertThat(foundNotification.get().getMessage()).isEqualTo("Test message");
    }

    @Test
    void testFindByStatus() {
        // Kullanıcı ve tür oluştur
        User user = new User();
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setPhoneNumber("333-3333");
        userRepo.save(user);

        NotificationTypes type = new NotificationTypes();
        type.setTypeName("Push");
        notificationTypeRepo.save(type);

        Notifications notification = new Notifications();
        notification.setUser(user);
        notification.setNotificationType(type);
        notification.setMessage("Your order has been shipped!");
        notification.setStatus("sent");

        repository.save(notification);

        // Status'e göre bildirimleri bul
        List<Notifications> notifications = repository.findByStatus("sent");

        // Doğrulama
        assertThat(notifications).isNotEmpty();
        assertThat(notifications.get(1).getMessage()).isEqualTo("Your order has been shipped!");
    }

    @Test
    void testUpdateNotification() {
        // Yeni bildirim oluştur ve kaydet
        User user = new User();
        user.setName("Dave");
        user.setEmail("dave@example.com");
        user.setPhoneNumber("222-2222");
        userRepo.save(user);

        NotificationTypes type = new NotificationTypes();
        type.setTypeName("Email");
        notificationTypeRepo.save(type);

        Notifications notification = new Notifications();
        notification.setUser(user);
        notification.setNotificationType(type);
        notification.setMessage("Initial message");
        notification.setStatus("pending");

        repository.save(notification);

        // Bildirimi güncelle
        notification.setMessage("Updated message");
        notification.setStatus("sent");
        repository.save(notification);

        // Güncellenmiş bildirimi doğrula
        Notifications updatedNotification = repository.findById(notification.getId()).orElseThrow();
        assertThat(updatedNotification.getMessage()).isEqualTo("Updated message");
        assertThat(updatedNotification.getStatus()).isEqualTo("sent");
    }

    @Test
    void testDeleteNotification() {
        // Yeni bildirim oluştur ve kaydet
        User user = new User();
        user.setName("Eve");
        user.setEmail("eve@example.com");
        user.setPhoneNumber("111-1111");
        userRepo.save(user);

        NotificationTypes type = new NotificationTypes();
        type.setTypeName("Push");
        notificationTypeRepo.save(type);

        Notifications notification = new Notifications();
        notification.setUser(user);
        notification.setNotificationType(type);
        notification.setMessage("Message to be deleted");
        notification.setStatus("pending");

        repository.save(notification);

        // Bildirimi sil
        repository.delete(notification);

        // Veritabanında artık olmadığını doğrula
        Optional<Notifications> deletedNotification = repository.findById(notification.getId());
        assertThat(deletedNotification).isEmpty();
    }

    @Test
    void testFindByNotificationType() {
        // Kullanıcı ve notification type oluştur
        User user = new User();
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setPhoneNumber("1234567890");
        userRepo.save(user);

        NotificationTypes type = new NotificationTypes();
        type.setTypeName("Alert");
        notificationTypeRepo.save(type);

        // Notification ekle
        Notifications notification = new Notifications();
        notification.setUser(user);
        notification.setNotificationType(type);
        notification.setMessage("Test Message");
        notification.setStatus("pending");
        repository.save(notification);

        // Notification tipine göre arama yapma
        List<Notifications> notifications = repository.findByNotificationType(type);

        // Sonuçları doğrulama
        assertThat(notifications).isNotEmpty();
        assertThat(notifications.get(0).getNotificationType().getTypeName()).isEqualTo("Alert");
    }
}
