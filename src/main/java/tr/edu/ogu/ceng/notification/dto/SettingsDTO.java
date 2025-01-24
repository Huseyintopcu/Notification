package tr.edu.ogu.ceng.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettingsDTO {

    private Long id;
    private String settingKey;
    private String value;


}
