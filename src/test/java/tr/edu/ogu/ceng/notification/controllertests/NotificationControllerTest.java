package tr.edu.ogu.ceng.notification.controllertests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tr.edu.ogu.ceng.notification.dto.NotificationsDTO;
import tr.edu.ogu.ceng.notification.service.NotificationsService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    NotificationsService notificationsService;

    @Test
    void testGetAllNotifications() throws Exception {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        NotificationsDTO notification1 = new NotificationsDTO(1L, "Message1", "SENT", now.minusDays(1), now);
        NotificationsDTO notification2 = new NotificationsDTO(2L, "Message2", "PENDING", now.minusHours(2), null);
        List<NotificationsDTO> notifications = Arrays.asList(notification1, notification2);

//        Mockito.when(notificationsService.getAllNotifications()).thenReturn(notifications);

        // Act & Assert
        mockMvc.perform(get("/api/v1/notification"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].message").value("Message1"))
                .andExpect(jsonPath("$[0].status").value("SENT"))
                .andExpect(jsonPath("$[0].createdAt").isNotEmpty())
                .andExpect(jsonPath("$[0].sentAt").isNotEmpty())
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].message").value("Message2"))
                .andExpect(jsonPath("$[1].status").value("PENDING"))
                .andExpect(jsonPath("$[1].sentAt").isEmpty());
    }

    @Test
    void testGetNotificationById() throws Exception {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        NotificationsDTO notification = new NotificationsDTO(1L, "Message1", "SENT", now.minusDays(1), now);

        Mockito.when(notificationsService.getNotificationById(1L)).thenReturn(notification);

        // Act & Assert
        mockMvc.perform(get("/api/v1/notification/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("Message1"))
                .andExpect(jsonPath("$.status").value("SENT"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.sentAt").isNotEmpty());
    }

    @Test
    void testCreateNotification() throws Exception {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        NotificationsDTO newNotification = new NotificationsDTO(null, "New Message", "PENDING", now, null);
        NotificationsDTO savedNotification = new NotificationsDTO(3L, "New Message", "PENDING", now, null);

        Mockito.when(notificationsService.createNotification(any(NotificationsDTO.class))).thenReturn(savedNotification);

        // Act & Assert
        mockMvc.perform(post("/api/v1/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"New Message\", \"status\": \"PENDING\", \"createdAt\": \"" + now + "\", \"sentAt\": null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.message").value("New Message"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.sentAt").isEmpty());
    }

    @Test
    void testUpdateNotification() throws Exception {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        NotificationsDTO updatedNotification = new NotificationsDTO(1L, "Updated Message", "SENT", now.minusDays(1), now);

        Mockito.when(notificationsService.updateNotification(eq(1L), any(NotificationsDTO.class)))
                .thenReturn(updatedNotification);

        // Act & Assert
        mockMvc.perform(put("/api/v1/notification/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"Updated Message\", \"status\": \"SENT\", \"createdAt\": \"" + now.minusDays(1) + "\", \"sentAt\": \"" + now + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("Updated Message"))
                .andExpect(jsonPath("$.status").value("SENT"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.sentAt").isNotEmpty());
    }

    @Test
    void testDeleteNotification() throws Exception {
        // Arrange
        Mockito.doNothing().when(notificationsService).deleteNotification(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/notification/1"))
                .andExpect(status().isOk());
    }
}
