package lk.ijse.eca.userservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthDTO {
    private String username;
    private String token;
    private String refreshToken;
}
