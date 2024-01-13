package org.chainoptim.core.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSearchResultDTO {
    private String id;
    private String username;
    private String email;
}
