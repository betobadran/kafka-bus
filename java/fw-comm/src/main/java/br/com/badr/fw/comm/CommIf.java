package br.com.badr.fw.comm;

import java.time.Duration;

public interface CommIf {
	/**
	 * Start all consumers
	 */
	void start();
	
	/**
	 * Stop all consumers
	 */
	void stop();
	
	/**
	 * Send an event
	 * @param method
	 * @param payload
	 */
	void send(String method, Object payload);
	
	/**
	 * Subscribe for an event
	 * @param app
	 * @param part
	 * @param method
	 * @param callback
	 */
	void subscribe(String app, int part, String method, Object callback);
	
	/**
	 * Sync request response using bus
	 * @param <T>
	 * @param app
	 * @param part
	 * @param method
	 * @param payload
	 * @param type
	 * @return
	 */
	<T, U extends Object> U request(String app, int part, String method, T payload, Class<U> type, Duration timeout);
	<T, U extends Object> U request(String app, int part, String method, T payload, Class<U> type);
}
