package tr.edu.ogu.ceng.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String username;

}
