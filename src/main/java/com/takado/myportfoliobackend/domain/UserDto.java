package com.takado.myportfoliobackend.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
public class UserDto {
    private final Long id;
    private final String email;
    private final String nameHash;
    private final String displayedName;
    private final List<Long> assetsId;
}
