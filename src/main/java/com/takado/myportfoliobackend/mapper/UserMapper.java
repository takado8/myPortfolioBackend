package com.takado.myportfoliobackend.mapper;

import com.takado.myportfoliobackend.domain.Asset;
import com.takado.myportfoliobackend.domain.User;
import com.takado.myportfoliobackend.domain.UserDto;
import com.takado.myportfoliobackend.service.AssetDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final AssetDbService assetDbService;
    public User mapToUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getEmail(), userDto.getNameHash(), userDto.getDisplayedName(),
                assetDbService.getAllAssetsByUserId(userDto.getId()), Collections.emptyList());
    }

    public UserDto mapToDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getNameHash(), user.getDisplayedName(),
                user.getAssets().stream().map(Asset::getId).collect(Collectors.toList()));
    }

    public List<UserDto> mapToDtoList(List<User> allUsers) {
        return allUsers.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
