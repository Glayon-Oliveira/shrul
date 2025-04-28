package com.lmlasmo.shrul.data.link;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public interface LinkDeleteProvider {

	public static Stream<Arguments> provider(){
		return Stream.of(
				Arguments.of(null, 200),
				Arguments.of("", 400),
				Arguments.of("SWOPTEST", 400),
				Arguments.of("SWOPTESTRU", 404));
	}

}
