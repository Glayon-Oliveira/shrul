package com.lmlasmo.shrul.data.link;

import java.math.BigInteger;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public interface UpdateLinkProvider {

	public static Stream<Arguments> provider(){
		return Stream.of(
				Arguments.of(null, null, 200),
				Arguments.of(null, BigInteger.ZERO, 200),
				Arguments.of(null, BigInteger.valueOf(1000), 404),
				Arguments.of(null, BigInteger.valueOf(-1), 400),
				Arguments.of("", null, 400),
				Arguments.of("SWOPTEST", null, 400),
				Arguments.of("SWOPTESTRU", null, 404));
	}

}
