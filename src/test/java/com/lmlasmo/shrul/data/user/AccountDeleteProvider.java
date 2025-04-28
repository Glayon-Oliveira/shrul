package com.lmlasmo.shrul.data.user;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.lmlasmo.shrul.dto.auth.CodeHashDTO;

public interface AccountDeleteProvider {

	public static Stream<Arguments> provider(){
		return Stream.of(
				Arguments.of(null, null, 200),
				Arguments.of(null, new CodeHashDTO(), 400),
				Arguments.of("12345678", null, 403));
	}

}
