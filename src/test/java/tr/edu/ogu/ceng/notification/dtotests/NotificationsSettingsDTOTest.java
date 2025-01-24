package tr.edu.ogu.ceng.notification.dtotests;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import tr.edu.ogu.ceng.notification.dto.NotificationsSettingsDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationsSettingsDTOTest {


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
    void testNotificationsSettingsDTO() {

        Long id = 1L;
        Boolean enabled = true;
        NotificationsSettingsDTO settingsDTO = new NotificationsSettingsDTO(id, enabled);

        assertEquals(id, settingsDTO.getId());
        assertEquals(enabled, settingsDTO.getEnabled());

        Long newId = 2L;
        Boolean newEnabled = false;
        settingsDTO.setId(newId);
        settingsDTO.setEnabled(newEnabled);

        assertEquals(newId, settingsDTO.getId());
        assertEquals(newEnabled, settingsDTO.getEnabled());
    }
}

