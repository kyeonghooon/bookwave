package com.library.bookwave.service;

import java.security.SecureRandom;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

	// 랜덤비밀번호
	public String getRamdomPassword(int size) {
		String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
		String specialKey = "!@#$%";
		StringBuffer sb = new StringBuffer();
		SecureRandom sr = new SecureRandom();
		sr.setSeed(new Date().getTime());

		int idx = 0;
		int len = charSet.length();
		for (int i = 0; i < size; i++) {
			// idx = (int) (len * Math.random());
			idx = sr.nextInt(len); // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
			if (i == 0) {
				sb.append(specialKey.charAt(sr.nextInt(specialKey.length())));
			} else {
				sb.append(charSet.charAt(idx));
			}
		}
		return sb.toString();
	}

}//
