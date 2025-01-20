package com.lmlasmo.shrul.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface EmailCodeTool {

	public static Map<String, String> create(String email) {
		
		String key = System.getenv("EMAIL_CONFIRMATION_KEY");
		
		if(key == null) {
			throw new IllegalStateException("Environment variable 'MY_ENV_VAR' is not set but is required.");
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();		
		
		String code = Integer.toString(new Random().nextInt(10000000, 99999999));
		
		String hash = encoder.encode(email+code+key);
		
		Map<String, String> codeHash = new HashMap<>();
		
		codeHash.put("code", code);
		codeHash.put("hash", hash);		
		
		return Map.copyOf(codeHash);
	}
	
	public static boolean confirm(String email, String code, String hash) {
		
		String key = System.getenv("EMAIL_CONFIRMATION_KEY");
		
		if(key == null) {
			throw new IllegalStateException("Environment variable 'MY_ENV_VAR' is not set but is required.");
		}
		
		String compiled = email+code+key;
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.matches(compiled, hash);		
	}
	
}
