package tr.edu.ogu.ceng.notification.dtotests;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import tr.edu.ogu.ceng.notification.dto.SettingsDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SettingsDTOTest {


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
    void testSettingsDTOWithAllArgsConstructor() {

        Long id = 1L;
        String settingKey = "key1";
        String value = "value1";
        SettingsDTO settingsDTO = new SettingsDTO(id, settingKey, value);

        assertEquals(id, settingsDTO.getId());
        assertEquals(settingKey, settingsDTO.getSettingKey());
        assertEquals(value, settingsDTO.getValue());
    }

    @Test
    void testSettingsDTOWithNoArgsConstructorAndSetters() {

        SettingsDTO settingsDTO = new SettingsDTO();

        Long newId = 2L;
        String newSettingKey = "key2";
        String newValue = "value2";
        settingsDTO.setId(newId);
        settingsDTO.setSettingKey(newSettingKey);
        settingsDTO.setValue(newValue);

        assertEquals(newId, settingsDTO.getId());
        assertEquals(newSettingKey, settingsDTO.getSettingKey());
        assertEquals(newValue, settingsDTO.getValue());
    }
}

