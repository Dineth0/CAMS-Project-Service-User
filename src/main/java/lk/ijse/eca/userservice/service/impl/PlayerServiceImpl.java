package lk.ijse.eca.userservice.service.impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import jakarta.transaction.Transactional;
import lk.ijse.eca.userservice.dto.PlayerProfileDTO;
import lk.ijse.eca.userservice.dto.PlayerProfileUpdateRequest;
import lk.ijse.eca.userservice.entity.PlayerProfile;
import lk.ijse.eca.userservice.entity.Role;
import lk.ijse.eca.userservice.entity.User;
import lk.ijse.eca.userservice.repo.PlayerRepo;
import lk.ijse.eca.userservice.repo.UserRepo;
import lk.ijse.eca.userservice.service.PlayerService;
import lk.ijse.eca.userservice.util.VarList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.UUID;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.nio.file.StandardCopyOption;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final ModelMapper modelMapper;
    private final PlayerRepo playerRepo;
    private final UserRepo userRepo;
    private final Storage storage;

    @Value("${app.gcp.bucket}")
    private String bucketName;

    private String uploadImageToGCS(MultipartFile imageFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(imageFile.getContentType())
                .build();

        storage.create(blobInfo, imageFile.getBytes());

        return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
    }
    @Override
    public int upgradeUserToPlayer(String email, PlayerProfileUpdateRequest request, MultipartFile imageFile) {
        User user = userRepo.findByEmail(email);

        if (user == null) {
            return VarList.Not_Found;
        }
        if (user.getRole() != Role.USER) {
            return 403;
        }

        if (playerRepo.existsById(user.getId())) {
            return VarList.Conflict;
        }

        user.setRole(Role.PLAYER);
        PlayerProfile playerProfile = modelMapper.map(request, PlayerProfile.class);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageUrl = uploadImageToGCS(imageFile);
                playerProfile.setImage(imageUrl);

            } catch (IOException e) {
                e.printStackTrace();
                return VarList.Internal_Server_Error;
            }
        }

        playerProfile.setUser(user);
        user.setPlayerProfile(playerProfile);

        userRepo.save(user);

        return VarList.Created;
    }

    @Override
    public int DeletePlayer(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getPlayerProfile() != null) {

                PlayerProfile profile = user.getPlayerProfile();
                user.setPlayerProfile(null);

                user.setRole(Role.USER);

                playerRepo.delete(profile);

                userRepo.save(user);

                return VarList.OK;
            }
        }
        return VarList.Not_Found;
    }

    @Override
    public List<PlayerProfileDTO> GetAllPlayers() {
        List<PlayerProfile> playerProfiles = playerRepo.findAll();

        return playerProfiles.stream()
                .map(player -> {
                    PlayerProfileDTO dto = modelMapper.map(player, PlayerProfileDTO.class);

                    if (player.getUser() != null) {
                        dto.setEmail(player.getUser().getEmail());
                        dto.setRole(player.getUser().getRole());
                        dto.setFullName(player.getUser().getUserName());
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Override
    public PlayerProfileDTO getPlayerById(Long id) {
        Optional<PlayerProfile> playerOptional = playerRepo.findById(id);
        if (playerOptional.isPresent()) {
            PlayerProfile player = playerOptional.get();
            PlayerProfileDTO dto = modelMapper.map(player, PlayerProfileDTO.class);

            if (player.getUser() != null) {
                dto.setEmail(player.getUser().getEmail());
                dto.setRole(player.getUser().getRole());
                dto.setFullName(player.getUser().getUserName());
            }

            return dto;
        }

        return null;    }
    @Override
    public long getPlayerCount() {
        return playerRepo.count();
    }

    @Override
    public int updatePlayerProfile(String email, PlayerProfileUpdateRequest request, MultipartFile imageFile) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return VarList.Not_Found;
        }
        if (user.getRole() != Role.PLAYER) {
            return 403;
        }
        PlayerProfile playerProfile = user.getPlayerProfile();
        if (playerProfile == null) {
            return VarList.Not_Found;
        }
        if (request.getDateOfBirth() != null) playerProfile.setDateOfBirth(request.getDateOfBirth());
        if (request.getGender() != null) playerProfile.setGender(request.getGender());
        if (request.getPhoneNumber() != null) playerProfile.setPhoneNumber(request.getPhoneNumber());
        if (request.getAddress() != null) playerProfile.setAddress(request.getAddress());
        if (request.getPlayingRole() != null) playerProfile.setPlayingRole(request.getPlayingRole());
        if (request.getBattingStyle() != null) playerProfile.setBattingStyle(request.getBattingStyle());
        if (request.getBowlingStyle() != null) playerProfile.setBowlingStyle(request.getBowlingStyle());
        if (request.getExperienceLevel() != null) playerProfile.setExperienceLevel(request.getExperienceLevel());

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageUrl = uploadImageToGCS(imageFile);
                playerProfile.setImage(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
                return VarList.Internal_Server_Error;
            }
        }
        playerRepo.save(playerProfile);
        return VarList.OK;
    }
}
