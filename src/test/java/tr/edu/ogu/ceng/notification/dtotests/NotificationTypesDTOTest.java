package tr.edu.ogu.ceng.notification.dtotests;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import tr.edu.ogu.ceng.notification.dto.NotificationTypesDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationTypesDTOTest {


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
    void testNotificationTypesDTOWithArgsConstructor() {

        Long id = 1L;
        String typeName = "Type A";
        NotificationTypesDTO typesDTO = new NotificationTypesDTO(id, typeName);

        // Verify the fields using getters
        assertEquals(id, typesDTO.getId());
        assertEquals(typeName, typesDTO.getTypeName());
    }

    @Test
    void testNotificationTypesDTOWithNoArgsConstructorAndSetters() {

        NotificationTypesDTO typesDTO = new NotificationTypesDTO();

        Long newId = 2L;
        String newTypeName = "Type B";
        typesDTO.setId(newId);
        typesDTO.setTypeName(newTypeName);

        assertEquals(newId, typesDTO.getId());
        assertEquals(newTypeName, typesDTO.getTypeName());
    }
}

