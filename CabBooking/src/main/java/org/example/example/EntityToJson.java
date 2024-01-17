package org.example.example;

public class EntityToJson
{
	public static String jsonResponse(String status, String message) {
		return String.format("{\"status\": \"%s\", \"message\": \"%s\"}", status, message);
	}
}
