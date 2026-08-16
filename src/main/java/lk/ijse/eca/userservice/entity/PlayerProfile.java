package lk.ijse.eca.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "player_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerProfile {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phoneNumber;
    private String address;

    @Enumerated(EnumType.STRING)
    private PlayingRole playingRole;
    @Enumerated(EnumType.STRING)
    private BattingStyle battingStyle;
    private String bowlingStyle;
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;

    private String image;
}
