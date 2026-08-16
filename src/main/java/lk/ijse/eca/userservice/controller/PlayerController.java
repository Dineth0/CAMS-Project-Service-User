package lk.ijse.eca.userservice.controller;

import lk.ijse.eca.userservice.dto.PlayerProfileDTO;
import lk.ijse.eca.userservice.dto.PlayerProfileUpdateRequest;
import lk.ijse.eca.userservice.dto.ResponseDTO;
import lk.ijse.eca.userservice.service.PlayerService;
import lk.ijse.eca.userservice.util.ResponseUtil;
import lk.ijse.eca.userservice.util.VarList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {

    private final PlayerService playerService;
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    @PostMapping(value = "/upgrade", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDTO> upgradeToPlayer(
            @RequestPart("data") PlayerProfileUpdateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @RequestHeader("X-User-Email") String email) {
        try {
            int res = playerService.upgradeUserToPlayer(email, request, imageFile);

            if (res == VarList.Created) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.Created, "Successfully Upgrade Player", null));
            } else if (res == 403) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ResponseDTO(403, "Forbidden: Only USERs can upgrade", null));
            } else if (res == VarList.Not_Found) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
            } else if (res == VarList.Conflict) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDTO(VarList.Conflict, "User Already Player", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.Bad_Request, "Error", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }

    }
    @PutMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDTO> updatePlayerProfile(
            @RequestPart("data") PlayerProfileUpdateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @RequestHeader("X-User-Email") String email) {

        try {
            int res = playerService.updatePlayerProfile(email, request, imageFile);

            if (res == VarList.OK) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "Successfully Updated Player Profile", null));
            } else if (res == 403) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ResponseDTO(403, "Forbidden: Only Players can update their profile", null));
            } else if (res == VarList.Not_Found) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "User or Profile Not Found", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.Bad_Request, "Error Updating Profile", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllPlayers() {
        try {
            List<PlayerProfileDTO> players = playerService.GetAllPlayers();
        return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Success", players));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseDTO> deletePlayer(@PathVariable Long id) {
        try {
            int res = playerService.DeletePlayer(id);

            if (res == VarList.OK) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "User Deleted Successfully", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getPlayerById(@PathVariable Long id) {
        try {
            PlayerProfileDTO playerDTO = playerService.getPlayerById(id);
            if (playerDTO != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "Success", playerDTO));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "Player Not Found", null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
    @GetMapping("/count")
    public ResponseEntity<ResponseDTO> getCount() {
        try {
            long count = playerService.getPlayerCount();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Success", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
}