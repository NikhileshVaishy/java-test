package com.sevenre.triastest.service;

import com.sevenre.triastest.User;
import com.sevenre.triastest.dto.UserDto;
import com.sevenre.triastest.dto.UserRequestDto;
import com.sevenre.triastest.dto.UserResponseDto;
import com.sevenre.triastest.exceptions.SrNotFoundException;
import com.sevenre.triastest.repository.UserRepository;
import com.sevenre.triastest.utils.Constants;
import com.sevenre.triastest.utils.JwtUtils;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikhilesh on 14/07/17.
 */
@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();

    public UserResponseDto verifyUser(UserRequestDto userRequestDto) {
        User user = userRepository.findByUserEmail(userRequestDto.getUserName());
        if (user != null && user.getPassword().equals(userRequestDto.getPassword())) {
            UserDto userDto = mapper.map(user, UserDto.class);
            return this.getToken(userDto);
        } else {
            throw new SrNotFoundException("User not found in records");
        }
    }

    private UserResponseDto getToken(UserDto userDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("firstName", userDto.getFirstName());
        claims.put("lastName", userDto.getLastName());
        claims.put("userEmail", userDto.getUserEmail());
        claims.put("active", userDto.isActive());
        String token = JwtUtils.encode(Constants.SECRET_KEY, claims);
        userDto.setToken(token);
        updateTokenInDb(userDto);
        return new UserResponseDto(token);
    }

    @Transactional
    public boolean updateTokenInDb(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        userRepository.save(user);
        return true;
    }
}
