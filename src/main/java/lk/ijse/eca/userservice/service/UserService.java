package lk.ijse.eca.userservice.service;

import lk.ijse.eca.userservice.dto.PlayerProfileDTO;
import lk.ijse.eca.userservice.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public interface UserService {
    UserDTO AddUser(UserDTO userDTO);

    public UserDTO loadUserDetailsByUsername(String email);

    public UserDTO authenticateUser(String email, String password);
    public List<UserDTO> GetAllUsers();
    public UserDTO getUserById(Long id);
}
