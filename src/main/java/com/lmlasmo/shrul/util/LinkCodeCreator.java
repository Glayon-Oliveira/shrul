package com.lmlasmo.shrul.util;

import java.util.HexFormat;

import org.bouncycastle.crypto.digests.Blake2bDigest;

public interface LinkCodeCreator {
	
	public static String create(String... values) {
		
		String compilation = "";
		
		for(String value: values) {
			compilation += value;
		}
		
		Blake2bDigest digest = new Blake2bDigest(40);
		
		byte[] compilationBytes = compilation.getBytes();
		digest.update(compilationBytes, 0, compilationBytes.length);
		
		byte[] hash = new byte[digest.getDigestSize()];
		digest.doFinal(hash, 0);
		
		return HexFormat.of().formatHex(hash);				
	}
	
}
