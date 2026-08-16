package lk.ijse.eca.userservice.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.eca.userservice.dto.PlayerProfileDTO;
import lk.ijse.eca.userservice.dto.ResponseDTO;
import lk.ijse.eca.userservice.dto.UserDTO;
import lk.ijse.eca.userservice.entity.PlayerProfile;
import lk.ijse.eca.userservice.entity.Role;
import lk.ijse.eca.userservice.entity.User;
import lk.ijse.eca.userservice.repo.UserRepo;
import lk.ijse.eca.userservice.service.UserService;
import lk.ijse.eca.userservice.util.VarList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO AddUser(UserDTO userDTO) {
        if (userRepo.existsByEmail(userDTO.getEmail())) {
            return null;
        }

        User user = modelMapper.map(userDTO, User.class);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO authenticateUser(String email, String password) {
        User user = userRepo.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTO.setRole(user.getRole());
            return userDTO;
        }
        return null;
    }

    @Override
    public UserDTO loadUserDetailsByUsername(String email) {
        User user =  userRepo.findByEmail(email);
        if(user == null){
            throw new RuntimeException("user not found");
        }
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    @Override
    public List<UserDTO> GetAllUsers() {
        List<User> users = userRepo.findAll();

        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepo.findById(id);
        return user.map(p -> modelMapper.map(p, UserDTO.class)).orElse(null);
    }

}