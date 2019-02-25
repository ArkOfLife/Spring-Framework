package com.example.table;

public class Allignment {

	public static String getAllignment(float xcor) {
		if(xcor>100) {
			return "CENTER";
		}else {
			return "LEFT";
		}
	}
}
