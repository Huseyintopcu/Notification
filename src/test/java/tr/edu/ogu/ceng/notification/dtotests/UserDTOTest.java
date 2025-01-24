package tr.edu.ogu.ceng.notification.dtotests;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import tr.edu.ogu.ceng.notification.dto.UserDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOTest {


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
    void testUserDTOWithAllArgsConstructor() {

        Long id = 1L;
        String name = "John Doe";
        String email = "johndoe@example.com";
        String phoneNumber = "1234567890";
        UserDTO userDTO = new UserDTO(id, name, email, phoneNumber);

        assertEquals(id, userDTO.getId());
        assertEquals(name, userDTO.getName());
        assertEquals(email, userDTO.getEmail());
        assertEquals(phoneNumber, userDTO.getPhoneNumber());
    }

    @Test
    void testUserDTOSettersAndGetters() {

        UserDTO userDTO = new UserDTO(null, null, null, null);

        Long newId = 2L;
        String newName = "Jane Doe";
        String newEmail = "janedoe@example.com";
        String newPhoneNumber = "0987654321";
        userDTO.setId(newId);
        userDTO.setName(newName);
        userDTO.setEmail(newEmail);
        userDTO.setPhoneNumber(newPhoneNumber);

        assertEquals(newId, userDTO.getId());
        assertEquals(newName, userDTO.getName());
        assertEquals(newEmail, userDTO.getEmail());
        assertEquals(newPhoneNumber, userDTO.getPhoneNumber());
    }
}

