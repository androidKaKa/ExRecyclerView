package com.kale.wfalldemo;

public interface ResponseCallback {
	public void onResponse(Object object);
	public void onError(String msg);
}
