package com.haojinxi.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
	public static int SUCCESS_CODE = 0;
	public static int FAIL_CODE = 1;

	int code;
	String message;
	Object data;

	public static Result success() {
		return new Result(SUCCESS_CODE,null,null);
	}
	public static Result success(Object data) {
		return new Result(SUCCESS_CODE,"",data);
	}
	public static Result fail(String message) {
		return new Result(FAIL_CODE,message,null);
	}

}