package tr.edu.ogu.ceng.notification.dtotests;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import tr.edu.ogu.ceng.notification.dto.NotificationsDTO;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


class NotificationsDTOTest {


    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    static GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest"))
            .withExposedPorts(6379);

    static {
        postgresContainer.start();
        redisContainer.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);

        registry.add("spring.redis.host", () -> redisContainer.getHost());
        registry.add("spring.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    @Test
    void testNotificationsDTO() {

        Long id = 1L;
        String message = "Test Message";
        String status = "SENT";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime sentAt = LocalDateTime.now().plusHours(1);

        NotificationsDTO notificationsDTO = new NotificationsDTO(id, message, status, createdAt, sentAt);

        assertEquals(id, notificationsDTO.getId());
        assertEquals(message, notificationsDTO.getMessage());
        assertEquals(status, notificationsDTO.getStatus());
        assertEquals(createdAt, notificationsDTO.getCreatedAt());
        assertEquals(sentAt, notificationsDTO.getSentAt());

        Long newId = 2L;
        String newMessage = "Updated Message";
        String newStatus = "FAILED";
        LocalDateTime newCreatedAt = LocalDateTime.now().minusDays(1);
        LocalDateTime newSentAt = LocalDateTime.now().minusHours(1);

        notificationsDTO.setId(newId);
        notificationsDTO.setMessage(newMessage);
        notificationsDTO.setStatus(newStatus);
        notificationsDTO.setCreatedAt(newCreatedAt);
        notificationsDTO.setSentAt(newSentAt);

        assertEquals(newId, notificationsDTO.getId());
        assertEquals(newMessage, notificationsDTO.getMessage());
        assertEquals(newStatus, notificationsDTO.getStatus());
        assertEquals(newCreatedAt, notificationsDTO.getCreatedAt());
        assertEquals(newSentAt, notificationsDTO.getSentAt());
    }
}

