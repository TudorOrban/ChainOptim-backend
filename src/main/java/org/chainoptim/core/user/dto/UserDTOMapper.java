package org.chainoptim.core.user.dto;

import org.chainoptim.core.user.model.User;

public class UserDTOMapper {

    private UserDTOMapper() {}

    public static User setUpdateUserProfileDTOToUser(UpdateUserProfileDTO userDTO, User user) {
        user.setUsername(userDTO.getUsername());
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setImageUrl(userDTO.getImageUrl());
        user.setIsProfilePublic(userDTO.getIsProfilePublic());
        return user;
    }
}
