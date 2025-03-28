package com.example.booking.transformers;

import com.example.booking.dto.UserDTO;
import com.example.booking.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Transformer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserToUserDTOTransformer implements Transformer<User, UserDTO> {
    @Override
    public UserDTO transform(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
