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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        return email.equals(userDto.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
