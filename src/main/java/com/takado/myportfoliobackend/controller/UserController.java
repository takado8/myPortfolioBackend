package com.takado.myportfoliobackend.controller;

import com.takado.myportfoliobackend.domain.User;
import com.takado.myportfoliobackend.domain.UserDto;
import com.takado.myportfoliobackend.mapper.UserMapper;
import com.takado.myportfoliobackend.service.UserDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
    private final UserDbService dbService;
    private final UserMapper mapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return mapper.mapToDtoList(dbService.getAllUsers());
    }

    @GetMapping({"{email}"})
    public UserDto getUser(@PathVariable String email) {
        User user = dbService.getUserByEmail(email);
        return user != null ? mapper.mapToDto(user) : new UserDto(null, null,
                null,null,null);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return mapper.mapToDto(dbService.saveUser(mapper.mapToUser(userDto)));
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        dbService.deleteUser(id);
    }
}
