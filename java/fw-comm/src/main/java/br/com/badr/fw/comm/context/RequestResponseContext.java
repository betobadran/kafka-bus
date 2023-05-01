package br.com.badr.fw.comm.context;

import java.time.Duration;

public class RequestResponseContext<T, U> {
	private Object monitor;
	private boolean isMonitorSet = false;
	
	private String id;
	private T reqPayload;
	private U rspPayload;
	
	public RequestResponseContext(String id, T reqPayload) {
		monitor = new Object();
		this.id = id;
	}
	
	public boolean waitOne(Duration timeout) {
		try {
			monitor.wait(timeout.toMillis());
			return isMonitorSet;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void set() {
		isMonitorSet = true;
		monitor.notifyAll();
	}
	
	public String getId() {
		return id;
	}
	
	public T getReqPayload() {
		return reqPayload;
	}
	
	public RequestResponseContext<T, U> setRspPayload(U rspPayload) {
		this.rspPayload = rspPayload;
		return this;
	}
	
	public U getRspPayload() {
		return rspPayload;
	}
}
