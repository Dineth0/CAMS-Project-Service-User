package lk.ijse.eca.userservice.dto;

import lk.ijse.eca.userservice.entity.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;
    private String email;
    private String password;
    private Role role;
}
