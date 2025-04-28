package com.lmlasmo.shrul.data.prefix;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public interface PrefixProvider {

	public static Stream<Arguments> provider(){
		return Stream.of(
				Arguments.of("testprefix", 200),
				Arguments.of("testPrefix", 400),
				Arguments.of(" testprefix", 400),
				Arguments.of("testprefix ", 400),
				Arguments.of("test prefix", 400),
				Arguments.of("", 400),
				Arguments.of(null, 400));
	}

}
