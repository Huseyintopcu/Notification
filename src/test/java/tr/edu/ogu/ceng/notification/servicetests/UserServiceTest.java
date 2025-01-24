package tr.edu.ogu.ceng.notification.servicetests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tr.edu.ogu.ceng.notification.dto.UserDTO;
import tr.edu.ogu.ceng.notification.entity.User;
import tr.edu.ogu.ceng.notification.repository.UserRepo;
import tr.edu.ogu.ceng.notification.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepo userRepository;

    @Autowired
    private UserService userService;

    private User sampleUser;
    private UserDTO sampleUserDTO;

    @BeforeEach
    void setUp() {


        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setName("John Doe");
        sampleUser.setEmail("john.doe@example.com");
        sampleUser.setPhoneNumber("123456789");

        sampleUserDTO = new UserDTO(sampleUser.getId(), sampleUser.getName(), sampleUser.getEmail(), sampleUser.getPhoneNumber());
    }

    @Test
    void getAllUsers_ShouldReturnListOfUserDTO() {
        List<User> userList = new ArrayList<>();
        userList.add(sampleUser);
        when(userRepository.findAll()).thenReturn(userList);

        List<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleUser.getId(), result.get(0).getId());
        assertEquals(sampleUser.getName(), result.get(0).getName());
        assertEquals(sampleUser.getEmail(), result.get(0).getEmail());
        assertEquals(sampleUser.getPhoneNumber(), result.get(0).getPhoneNumber());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_ShouldReturnUserDTO_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(sampleUserDTO.getName(), result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void createUser_ShouldSaveAndReturnUserDTO() {
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        UserDTO result = userService.createUser(sampleUserDTO);

        assertNotNull(result);
        assertEquals(sampleUserDTO.getName(), result.getName());
        assertEquals(sampleUserDTO.getEmail(), result.getEmail());
        assertEquals(sampleUserDTO.getPhoneNumber(), result.getPhoneNumber());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_ShouldUpdateAndReturnUpdatedUserDTO_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        sampleUserDTO.setEmail("updated.email@example.com");
        UserDTO result = userService.updateUser(1L, sampleUserDTO);

        assertNotNull(result);
        assertEquals("updated.email@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(1L, sampleUserDTO));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}

