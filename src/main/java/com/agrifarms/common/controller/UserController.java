package com.agrifarms.common.controller;

import com.agrifarms.common.dto.DtoMapper;
import com.agrifarms.common.dto.UserDTO;
import com.agrifarms.common.entity.User;
import com.agrifarms.common.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final DtoMapper dtoMapper;

    public UserController(UserService userService, DtoMapper dtoMapper) {
        this.userService = userService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User user = dtoMapper.toUserEntity(userDTO);
        // If ID is provided in DTO for testing purposes (like user-123), we might want
        // to respect it
        // Depending on @UuidGenerator strategy, if ID is null it generates one.
        // If we want to force a specific ID for testing, we can set it.
        User createdUser = userService.createUser(user);
        return dtoMapper.toUserDTO(createdUser);
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(dtoMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") String userId) {
        return userService.getUserById(userId)
                .map(dtoMapper::toUserDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<UserDTO> getUserByPhone(@PathVariable("phoneNumber") String phoneNumber) {
        return userService.getUserByPhoneNumber(phoneNumber)
                .map(dtoMapper::toUserDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") String userId, @RequestBody UserDTO userDTO) {
        User updatedUser = dtoMapper.toUserEntity(userDTO);
        try {
            User savedUser = userService.updateUser(userId, updatedUser);
            return ResponseEntity.ok(dtoMapper.toUserDTO(savedUser));
        } catch (org.springframework.web.server.ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).header("X-Error-Message", e.getMessage()).build();
        }
    }

    @PutMapping("/{userId}/fcm-token")
    public ResponseEntity<Void> updateFcmToken(@PathVariable("userId") String userId, @RequestBody java.util.Map<String, String> body) {
        String token = body.get("fcmToken");
        if (token != null) {
            userService.updateFcmToken(userId, token);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
