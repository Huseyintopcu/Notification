package tr.edu.ogu.ceng.notification.repotest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tr.edu.ogu.ceng.notification.CommonTest;
import tr.edu.ogu.ceng.notification.entity.NotificationSettings;
import tr.edu.ogu.ceng.notification.entity.NotificationTypes;
import tr.edu.ogu.ceng.notification.entity.User;
import tr.edu.ogu.ceng.notification.repository.NotificationsSettingsRepo;
import tr.edu.ogu.ceng.notification.repository.NotificationsTypesRepo;
import tr.edu.ogu.ceng.notification.repository.UserRepo;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NotificationSettingsRepositoryTest extends CommonTest {

    @Autowired
    private NotificationsSettingsRepo notificationSettingsRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private NotificationsTypesRepo notificationTypeRepo;


    @Test
    void testCreateNotificationSetting() {
        // Kullanıcı ve bildirim türü oluştur
        User user = new User();
        user.setName("Test User");
        userRepo.save(user);

        NotificationTypes type = new NotificationTypes();
        type.setTypeName("Test Type");
        notificationTypeRepo.save(type);

        // Bildirim ayarını oluştur
        NotificationSettings settings = new NotificationSettings();
        settings.setUser(user);
        settings.setNotificationType(type);
        settings.setEnabled(true);
        notificationSettingsRepo.save(settings);

        assertThat(settings.getId()).isNotNull();
    }

    @Test
    void testReadNotificationSetting() {
        // Yeni bildirim ayarı oluştur
        NotificationSettings settings = createTestNotificationSettings();
        Long id = settings.getId();

        // Ayarı sorgula
        Optional<NotificationSettings> foundSetting = notificationSettingsRepo.findById(id);

        assertThat(foundSetting).isPresent();
        assertThat(foundSetting.get().getEnabled()).isTrue();
    }

    @Test
    void testUpdateNotificationSetting() {
        // Yeni bildirim ayarı oluştur
        NotificationSettings settings = createTestNotificationSettings();

        // Ayarı güncelle
        settings.setEnabled(false);
        notificationSettingsRepo.save(settings);

        // Güncellenmiş ayarı kontrol et
        Optional<NotificationSettings> updatedSetting = notificationSettingsRepo.findById(settings.getId());
        assertThat(updatedSetting).isPresent();
        assertThat(updatedSetting.get().getEnabled()).isFalse();
    }

    @Test
    void testDeleteNotificationSetting() {
        // Yeni bildirim ayarı oluştur
        NotificationSettings settings = createTestNotificationSettings();
        Long id = settings.getId();

        // Ayarı sil
        notificationSettingsRepo.deleteById(id);

        // Kontrol et
        Optional<NotificationSettings> deletedSetting = notificationSettingsRepo.findById(id);
        assertThat(deletedSetting).isEmpty();
    }

    private NotificationSettings createTestNotificationSettings() {
        // Kullanıcı ve bildirim türü oluştur
        User user = new User();
        user.setName("Test User");
        userRepo.save(user);

        NotificationTypes type = new NotificationTypes();
        type.setTypeName("Test Type");
        notificationTypeRepo.save(type);

        // Bildirim ayarını oluştur
        NotificationSettings settings = new NotificationSettings();
        settings.setUser(user);
        settings.setNotificationType(type);
        settings.setEnabled(true);
        notificationSettingsRepo.save(settings);

        return settings;
    }
}
