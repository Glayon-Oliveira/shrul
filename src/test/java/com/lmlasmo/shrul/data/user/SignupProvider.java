package com.lmlasmo.shrul.data.user;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import com.lmlasmo.shrul.dto.auth.CodeHashDTO;

public interface SignupProvider {

	public static Stream<Arguments> provider(){
		return Stream.of(
				Arguments.of("test1@gmail.com", "Testador", "Qrepu", null, null, 200),
				Arguments.of("test@gmail.com", "Testador", "Qrepu", null, null, 409),
				Arguments.of("test1@gmail.com", " Testador", "Qrepu", null, null, 400),
				Arguments.of("test1@gmail.com", "Testador ", "Qrepu", null, null, 400),
				Arguments.of("test1@gmail.com", " Testador ", "Qrepu", null, null, 400),
				Arguments.of("test1@gmail.com", "Testador", " Qrepu", null, null, 400),
				Arguments.of("test1@gmail.com", "Testador", "Qrepu ", null, null, 400),
				Arguments.of("test1@gmail.com", "Testador", " Qrepu ", null, null, 400),
				Arguments.of("test1@gmail.com", "Testador", "Qrepu", null, new CodeHashDTO(), 400));
	}

}
