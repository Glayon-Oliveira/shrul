package com.lmlasmo.shrul.data.user;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public interface UserUpdateProvider {

	public static Stream<Arguments> provider(){
		return Stream.of(
				Arguments.of("Test", "Qrepu", 200),
				Arguments.of("Test", null, 200),
				Arguments.of(null, "Qrupu", 200),
				Arguments.of(null, null, 200),
				Arguments.of(" Test", null, 400),
				Arguments.of("Test ", null, 400),
				Arguments.of(" Test ", null, 400),
				Arguments.of(null, " Qrupu", 400),
				Arguments.of(null, "Qrupu ", 400),
				Arguments.of(null, " Qrupu ", 400));
	}

}
