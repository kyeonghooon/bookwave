package com.library.bookwave.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public abstract class ValueFormatter {
	
	public static String timestampToString(Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(timestamp);
	}
	
	public String timestampToStringDate(Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(timestamp);
	}
	
	public String amountToString(Long amount) {
		DecimalFormat df = new DecimalFormat("###,###");
		return df.format(amount);
	}

}
