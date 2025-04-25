package com.lmlasmo.shrul.mapper;

import java.math.BigInteger;

import org.mapstruct.Mapper;

import com.lmlasmo.shrul.dto.UserDTO;
import com.lmlasmo.shrul.dto.register.SignupDTO;
import com.lmlasmo.shrul.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	public User dtoToUser(SignupDTO dto);
	
	public UserDTO userToDTO(User user);
	
	public default User idToUser(BigInteger id) {
		return new User(id);
	}
	
	public default BigInteger userToId(User user) {
		return user.getId();
	}

}
