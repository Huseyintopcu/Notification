package tr.edu.ogu.ceng.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class NotificationsDTO {

    private Long id;
    private String message;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;

}
