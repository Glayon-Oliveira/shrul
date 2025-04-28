package com.lmlasmo.shrul.data.prefix;

import java.math.BigInteger;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public interface PrefixDeleteProvider {

	public static Stream<Arguments> provider(){
		return Stream.of(
				Arguments.of(null, 200),
				Arguments.of(BigInteger.ZERO, 500),
				Arguments.of(BigInteger.valueOf(1000), 404),
				Arguments.of(BigInteger.valueOf(-1), 400));
	}

}
