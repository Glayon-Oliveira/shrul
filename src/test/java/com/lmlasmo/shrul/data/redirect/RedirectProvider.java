package com.lmlasmo.shrul.data.redirect;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public interface RedirectProvider {

	public static Stream<Arguments> provider(){
		return Stream.of(
				Arguments.of(null, null, 303),
				Arguments.of(null, "/LINKIDTEST", 404),
				Arguments.of(null, "", 404),
				Arguments.of(null, "/", 404),
				Arguments.of("/PREFIXTEST", null, 404),
				Arguments.of("", null, 303),
				Arguments.of("/", null, 404),
				Arguments.of("", "", 404));
	}

}
