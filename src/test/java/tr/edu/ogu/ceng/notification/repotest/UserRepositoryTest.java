package tr.edu.ogu.ceng.notification.repotest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tr.edu.ogu.ceng.notification.CommonTest;
import tr.edu.ogu.ceng.notification.entity.User;
import tr.edu.ogu.ceng.notification.repository.UserRepo;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest extends CommonTest {

    @Autowired
    private UserRepo repository;

    @Test
    void testSaveUser() {
        // Kullanıcı ekleme
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPhoneNumber("123456789");

        User savedUser = repository.save(user);

        // Doğrulama
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("John Doe");
        assertThat(savedUser.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(savedUser.getPhoneNumber()).isEqualTo("123456789");
    }

    @Test
    void testUpdateUser() {
        // Kullanıcı ekleme
        User user = new User();
        user.setName("Jane Doe");
        user.setEmail("jane.doe@example.com");
        user.setPhoneNumber("987654321");

        User savedUser = repository.save(user);

        // Kullanıcıyı güncelleme
        savedUser.setName("Jane Smith");
        savedUser.setEmail("jane.smith@example.com");
        User updatedUser = repository.save(savedUser);

        // Doğrulama
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getName()).isEqualTo("Jane Smith");
        assertThat(updatedUser.getEmail()).isEqualTo("jane.smith@example.com");
        assertThat(updatedUser.getPhoneNumber()).isEqualTo("987654321");
    }

    @Test
    void testFindById() {
        // Kullanıcı ekleme
        User user = new User();
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setPhoneNumber("555444333");

        User savedUser = repository.save(user);

        // Kullanıcıyı ID ile sorgulama
        User foundUser = repository.findById(savedUser.getId()).orElse(null);

        // Doğrulama
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("Alice");
        assertThat(foundUser.getEmail()).isEqualTo("alice@example.com");
        assertThat(foundUser.getPhoneNumber()).isEqualTo("555444333");
    }

    @Test
    void testDeleteUser() {
        // Kullanıcı ekleme
        User user = new User();
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setPhoneNumber("111222333");

        User savedUser = repository.save(user);

        // Kullanıcıyı silme
        repository.delete(savedUser);

        // Kullanıcının silindiğini doğrulama
        boolean exists = repository.findById(savedUser.getId()).isPresent();
        assertThat(exists).isFalse();
    }
    @Test
    void testGetByName() {
        // Kullanıcı oluştur ve kaydet
        User user = new User();
        user.setName("John Doe2");
        user.setEmail("john.doe@example.com");
        user.setPhoneNumber("123456789");
        repository.save(user);

        // getByName metodunu test et
        User result=repository.getByName("John Doe2");

        // Sonuçların doğruluğunu kontrol et
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Doe2");
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
    }
    @Test
    void testFindByPhoneNumber() {
        // Test verisi ekleme
        User user = new User();
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setPhoneNumber("12345678922");
        repository.save(user);

        // Phone number ile kullanıcıyı bulma
        User foundUser = repository.findByPhoneNumber("12345678922");

        // Sonuçları doğrulama
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getPhoneNumber()).isEqualTo("12345678922");
        assertThat(foundUser.getName()).isEqualTo("Test User");
    }

    @Test
    void testFindByEmail() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe2@example.com");
        user.setPhoneNumber("123456789");
        repository.save(user);

        User foundUser = repository.findByEmail("john.doe2@example.com");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("john.doe2@example.com");
        assertThat(foundUser.getName()).isEqualTo("John Doe");
    }

    @Test
    void testFindByNameAndPhoneNumber() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPhoneNumber("12345678912");
        repository.save(user);

        User foundUser = repository.findByNameAndPhoneNumber("John Doe", "12345678912");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("John Doe");
        assertThat(foundUser.getPhoneNumber()).isEqualTo("12345678912");

    }
}
