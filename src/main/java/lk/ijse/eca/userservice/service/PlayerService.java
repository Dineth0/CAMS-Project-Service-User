package lk.ijse.eca.userservice.service;

import lk.ijse.eca.userservice.dto.PlayerProfileDTO;
import lk.ijse.eca.userservice.dto.PlayerProfileUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlayerService {
    public int upgradeUserToPlayer(String email, PlayerProfileUpdateRequest request, MultipartFile imageFile);
    public int DeletePlayer(Long id);
    public List<PlayerProfileDTO> GetAllPlayers();
    public PlayerProfileDTO getPlayerById(Long id);
    public long getPlayerCount();
    int updatePlayerProfile(String email, PlayerProfileUpdateRequest request, MultipartFile imageFile);
}
