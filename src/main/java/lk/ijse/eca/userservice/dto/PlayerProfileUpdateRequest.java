package lk.ijse.eca.userservice.dto;

import lk.ijse.eca.userservice.entity.BattingStyle;
import lk.ijse.eca.userservice.entity.ExperienceLevel;
import lk.ijse.eca.userservice.entity.Gender;
import lk.ijse.eca.userservice.entity.PlayingRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerProfileUpdateRequest {
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phoneNumber;
    private String address;

    private PlayingRole playingRole; // උදා: WICKET_KEEPER_BATSMAN
    private BattingStyle battingStyle;
    private String bowlingStyle;
    private ExperienceLevel experienceLevel;

    private String image;
}
