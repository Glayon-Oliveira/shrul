package com.lmlasmo.shrul.data.user;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.lmlasmo.shrul.model.UserRole;

public interface LockedProvider {

	public static Stream<Arguments> provider(){
		return Stream.of(
				Arguments.of(UserRole.ROLE_ADMIN, true, 200),
				Arguments.of(UserRole.ROLE_ADMIN, false, 409),
				Arguments.of(UserRole.ROLE_USER, true, 403),
				Arguments.of(UserRole.ROLE_USER, false, 403),
				Arguments.of(UserRole.ROLE_USER, null, 400));
	}

}
