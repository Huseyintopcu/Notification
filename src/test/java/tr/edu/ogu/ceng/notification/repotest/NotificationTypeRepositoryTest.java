package tr.edu.ogu.ceng.notification.repotest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tr.edu.ogu.ceng.notification.CommonTest;
import tr.edu.ogu.ceng.notification.entity.NotificationTypes;
import tr.edu.ogu.ceng.notification.repository.NotificationsTypesRepo;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NotificationTypeRepositoryTest extends CommonTest {

    @Autowired
    private NotificationsTypesRepo repository;

    @Test
    void testSaveNotificationType() {
        NotificationTypes type = new NotificationTypes();
        type.setTypeName("EMAIL");

        NotificationTypes savedType = repository.save(type);

        assertThat(savedType).isNotNull();
        assertThat(savedType.getId()).isNotNull();
        assertThat(savedType.getTypeName()).isEqualTo("EMAIL");
    }

    @Test
    void testFindById() {
        NotificationTypes type = new NotificationTypes();
        type.setTypeName("SMS");
        repository.save(type);

        Optional<NotificationTypes> foundType = repository.findById(type.getId());
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getTypeName()).isEqualTo("SMS");
    }

    @Test
    void testDeleteNotificationType() {
        NotificationTypes type = new NotificationTypes();
        type.setTypeName("Push Notification");
        repository.save(type);

        repository.deleteById(type.getId());

        Optional<NotificationTypes> deletedType = repository.findById(type.getId());
        assertThat(deletedType).isEmpty();
    }
}
