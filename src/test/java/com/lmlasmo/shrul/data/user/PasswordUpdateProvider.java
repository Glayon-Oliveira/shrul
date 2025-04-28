package com.lmlasmo.shrul.data.user;

import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public interface PasswordUpdateProvider {

	public static Stream<Arguments> provider(){
		return Stream.of(
				Arguments.of(null, 409),
				Arguments.of(UUID.randomUUID().toString(), 200));
	}

}
