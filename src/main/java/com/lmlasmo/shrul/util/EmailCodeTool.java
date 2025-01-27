package com.lmlasmo.shrul.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.lmlasmo.shrul.dto.CodeHashDTO;
import com.lmlasmo.shrul.infra.erro.EmailConfirmationException;

@Component
public class EmailCodeTool {

	@Value("${EMAIL_CONFIRMATION_KEY}")
	private String key;

	public static final int EXPIRES_MINUTES = 5;
	private AtomicLong logicKey = new AtomicLong(0);

	public EmailCodeTool() {}

	private byte somaByte(long value) {

		byte[] bytes = Long.toString(value).getBytes();
		value = 0;

		for(byte b: bytes) {
			value += b;
		}

		return (byte) value;
	}

	private byte setLogicKeyAndReturnLogicProduct(int code, LocalDateTime time){

		long logicKey = this.logicKey.get();
		long miles = time.toInstant(ZoneOffset.UTC).toEpochMilli();
		int random = new Random().nextInt(1, 1000);

		long toKey = miles/random;
		logicKey += (Long.MAX_VALUE-random > toKey) ? -toKey : toKey;
		logicKey = (Long.MAX_VALUE/code > logicKey) ? logicKey/code : logicKey*code;
		logicKey = Math.abs(logicKey);

		this.logicKey.set(logicKey);

		return somaByte(logicKey);
	}

	private String gerateCode(LocalDateTime timestamp) {

		int random = new Random().nextInt(1, 1000000);
		int product = setLogicKeyAndReturnLogicProduct(random, timestamp);

		return String.format("%06d", random+product);
	}

	public CodeHashDTO create(String email) {

		LocalDateTime timestamp = LocalDateTime.now().withNano(0);

		String code = gerateCode(timestamp);

		String toHash = email+code+key+timestamp;

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hash = encoder.encode(toHash);

		return new CodeHashDTO(timestamp, hash, code);
	}

	public void confirm(String email, CodeHashDTO codeHash) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		LocalDateTime timestamp = LocalDateTime.now().withNano(0);
		String compiled = email+codeHash.getCode()+key+codeHash.getTimestamp();

		if(!encoder.matches(compiled, codeHash.getHash())) {
			throw new EmailConfirmationException("Invalid code");
		}

		if(timestamp.isAfter(codeHash.getTimestamp().plusMinutes(EXPIRES_MINUTES))) {
			throw new EmailConfirmationException("Expired code");
		}

	}

}
