package com.kale.wfalldemo;

public interface ResponseCallback {
	public void onSuccess(Object object);
	public void onError(String msg);
}
