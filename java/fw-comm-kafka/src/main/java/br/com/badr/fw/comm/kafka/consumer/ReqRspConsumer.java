package br.com.badr.fw.comm.kafka.consumer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import br.com.badr.fw.comm.context.RequestResponseContextHandlerIf;
import br.com.badr.fw.common.config.FwConfigIf;

public class ReqRspConsumer extends FwConsumer {
	private final Duration defaultPoolTimeout;
	private final RequestResponseContextHandlerIf reqRspCtxHandler;
	private Consumer<String, byte[]> consumer;

	public ReqRspConsumer(FwConfigIf fwConfig, RequestResponseContextHandlerIf reqRspCtxHandler) {
		super(fwConfig);
		this.reqRspCtxHandler = reqRspCtxHandler;
		
		String bootstrapHost = fwConfig.get("fw.comm.host", String.class);
		int bootstrapPort = fwConfig.get("fw.comm.port", int.class);
		
		int poolTimeout = fwConfig.get("fw.comm.kafka.pool.timeout", int.class);
		defaultPoolTimeout = poolTimeout == 0 ? Duration.ofMillis(1) : Duration.ofMillis(poolTimeout);
		
		Properties props = new Properties();
		props.put("bootstrap.servers", bootstrapHost + ":" + bootstrapPort);
		props.put("transactional.id", "my-transactional-id");
		
		consumer = new KafkaConsumer<>(props, new StringDeserializer(), new ByteArrayDeserializer());
	}
	
	@Override
	public void run() {
		while (isNotCancel) {
	         ConsumerRecords<String, byte[]> records = consumer.poll(defaultPoolTimeout);
	         for (ConsumerRecord<String, byte[]> record : records) {
	        	 HashMap<String, String> headers = new HashMap<String, String>();
	        	 for (Header header : record.headers()) {
	        		 headers.put(header.key(), new String(header.value(), StandardCharsets.UTF_8));
	        	 }
	        	 
	        	 String contentType = headers.get("contentType");
	        	 record.value();
	        	 
	        	 reqRspCtxHandler.receivedRsp(headers.get("id"), , headers.get("last"));
	             //System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
	         }
	     }
	}

}
