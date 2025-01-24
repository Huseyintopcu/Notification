package tr.edu.ogu.ceng.notification.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationsSettingsDTO {

    private Long id;
    private Boolean enabled = true;
}
