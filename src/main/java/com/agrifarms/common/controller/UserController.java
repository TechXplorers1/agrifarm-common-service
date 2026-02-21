package com.agrifarms.common.controller;

import com.agrifarms.common.dto.DtoMapper;
import com.agrifarms.common.dto.UserDTO;
import com.agrifarms.common.entity.User;
import com.agrifarms.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final DtoMapper dtoMapper;

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

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String userId) {
        return userService.getUserById(userId)
                .map(dtoMapper::toUserDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<UserDTO> getUserByPhone(@PathVariable String phoneNumber) {
        return userService.getUserByPhoneNumber(phoneNumber)
                .map(dtoMapper::toUserDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String userId, @RequestBody UserDTO userDTO) {
        User updatedUser = dtoMapper.toUserEntity(userDTO);
        try {
            User savedUser = userService.updateUser(userId, updatedUser);
            return ResponseEntity.ok(dtoMapper.toUserDTO(savedUser));
        } catch (org.springframework.web.server.ResponseStatusException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
