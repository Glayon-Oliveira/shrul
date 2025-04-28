package com.lmlasmo.shrul.data.user;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public interface LoginProvider {

	public static Stream<Arguments> provider(){
		return Stream.of(
				Arguments.of(null, null, 200),
				Arguments.of(null, "12345678", 401),
				Arguments.of(null, "1234567", 400),
				Arguments.of(null, "", 400),
				Arguments.of("test1gmail.com", null, 400),
				Arguments.of(" test1@gmail.com", null, 400),
				Arguments.of("test1@gmail.com ", null, 400),
				Arguments.of(" test1@gmail.com ", null, 400));
	}

}
