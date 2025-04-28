package com.lmlasmo.shrul.data.link;

import java.math.BigInteger;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public interface LinkProvider {

	public static Stream<Arguments> provider(){
		return Stream.of(
				Arguments.of(null, "http://www.google.com/", 200),
				Arguments.of(null, null, 400),
				Arguments.of(null, "", 400),
				Arguments.of(null, "www.google.com/", 400),
				Arguments.of(BigInteger.valueOf(1000), "http://www.google.com", 404),
				Arguments.of(BigInteger.valueOf(-1), "http://www.google.com", 400));
	}

}
