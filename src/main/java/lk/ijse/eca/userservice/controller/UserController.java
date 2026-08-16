package lk.ijse.eca.userservice.controller;

import lk.ijse.eca.userservice.dto.AuthDTO;
import lk.ijse.eca.userservice.dto.PlayerProfileDTO;
import lk.ijse.eca.userservice.dto.ResponseDTO;
import lk.ijse.eca.userservice.dto.UserDTO;
import lk.ijse.eca.userservice.entity.Role;
import lk.ijse.eca.userservice.entity.User;
import lk.ijse.eca.userservice.repo.UserRepo;
import lk.ijse.eca.userservice.service.UserService;
import lk.ijse.eca.userservice.util.ResponseUtil;
import lk.ijse.eca.userservice.util.VarList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;


    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO savedUserDTO = userService.AddUser(userDTO);

            if (savedUserDTO == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDTO(409, "User already exists", null));
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(201, "User Created", savedUserDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> Login( @RequestBody UserDTO userDTO) {
        try {
            UserDTO loggedInUser = userService.authenticateUser(userDTO.getEmail(), userDTO.getPassword());

            if (loggedInUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseDTO(VarList.Unauthorized, "Invalid Credentials! Please try again.", null));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Login Success", loggedInUser));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }

    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllPlayers() {
        try {
            List<UserDTO> users = userService.GetAllUsers();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Success", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/role/{email}")
    public ResponseEntity<Role> getUserRole(@PathVariable String email) {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user.getRole());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getPlayerById(@PathVariable Long id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            if (userDTO != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "Success", userDTO));
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
    @GetMapping("/me")
    public ResponseEntity<ResponseDTO> getCurrentUser(@RequestHeader("X-User-Email") String email) {
        try {
            UserDTO userDTO = userService.loadUserDetailsByUsername(email);

            if (userDTO != null) {
                userDTO.setPassword(null);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "User details fetched successfully", userDTO));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "User Not Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }


}