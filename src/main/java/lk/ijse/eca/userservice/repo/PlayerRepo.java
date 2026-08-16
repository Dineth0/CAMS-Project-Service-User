package lk.ijse.eca.userservice.repo;

import lk.ijse.eca.userservice.entity.PlayerProfile;
import lk.ijse.eca.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepo extends JpaRepository<PlayerProfile, Long> {
}
