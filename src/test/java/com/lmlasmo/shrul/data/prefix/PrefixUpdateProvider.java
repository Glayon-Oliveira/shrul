package com.lmlasmo.shrul.data.prefix;

import java.math.BigInteger;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public interface PrefixUpdateProvider {

	public static Stream<Arguments> provider(){
		return Stream.of(
				Arguments.of(null, "prefixupdate", 200),
				Arguments.of(null, null, 409),
				Arguments.of(BigInteger.valueOf(100), null, 404),
				Arguments.of(BigInteger.ZERO, null, 400),
				Arguments.of(null, "", 400));
	}

}
