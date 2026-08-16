package lk.ijse.eca.userservice.dto;

import lk.ijse.eca.userservice.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerProfileDTO {
    private Long id;
    private String email;
    private Role role;
    private String fullName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phoneNumber;
    private String address;
    private PlayingRole playingRole;
    private BattingStyle battingStyle;
    private String bowlingStyle;
    private ExperienceLevel experienceLevel;
    private String image;
}
