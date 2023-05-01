package br.com.badr.fw.comm.kafka;

import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import br.com.badr.fw.comm.CommImpl;
import br.com.badr.fw.comm.context.RequestResponseContext;
import br.com.badr.fw.comm.context.RequestResponseContextHandler;
import br.com.badr.fw.comm.context.RequestResponseContextHandlerIf;
import br.com.badr.fw.comm.kafka.consumer.ReqRspConsumer;
import br.com.badr.fw.common.config.FwConfigIf;

public class CommKafkaImpl extends CommImpl implements CommKafkaIf {
	private static final String DOT = ".";
	private final String envPrefix;
	
	private Duration defaultRequestTimeout;

	private RequestResponseContextHandlerIf reqRspCtxHandler;
	private Producer<String, byte[]> reqRspProducer;
	private ReqRspConsumer reqRspConsumer;
	private Thread reqRspConsumerThread;
	
	private Producer<String, byte[]> eventsProducer;
	private Consumer<String, byte[]> eventsConsumer;
	private Thread eventsConsumerThread;
	
	private Producer<String, byte[]> controllerProducer;
	private Consumer<String, byte[]> controllerConsumer;
	private Thread controlelrConsumerThread;
	
	protected CommKafkaImpl(FwConfigIf fwConfig) {
		super(fwConfig);
		
		envPrefix = fwConfig.get("fw.comm.name", String.class);
		defaultRequestTimeout = Duration.ofMillis(fwConfig.get("fw.comm.default.request.timeout", long.class));
		
		
		buildProducersAndConsumers();
	}
	
	private void buildProducersAndConsumers() {
		String bootstrapHost = fwConfig.get("fw.comm.host", String.class);
		int bootstrapPort = fwConfig.get("fw.comm.port", int.class);
		
		Properties props = new Properties();
		props.put("bootstrap.servers", bootstrapHost + ":" + bootstrapPort);
		props.put("transactional.id", "my-transactional-id");
		reqRspProducer = new KafkaProducer<>(props, new StringSerializer(), new ByteArraySerializer());
		
		reqRspCtxHandler = new RequestResponseContextHandler();
		reqRspConsumer = new ReqRspConsumer(fwConfig, reqRspCtxHandler); 
		reqRspConsumerThread = new Thread(reqRspConsumer, "fw-comm-req-rsp-consumer");
	}

	@Override
	public void start() {
		reqRspConsumerThread.start();
		eventsConsumerThread.start();
		controlelrConsumerThread.start();
	}
	
	@Override
	public void stop() {
		
	}

	@Override
	public void send(String method, Object payload) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subscribe(String app, int part, String method, Object callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T, U> U request(String app, int part, String method, T payload, Class<U> type) {
		return request(app, part, method, payload, type, defaultRequestTimeout);
	}
	
	@Override
	public <T, U> U request(String app, int part, String method, T payload, Class<U> type, Duration timeout) {
		if (timeout == null) {
			timeout = defaultRequestTimeout;
		}
		
		// Get target topic
		String targetAppTopic = envPrefix + DOT + app + part + method;
		
		// Create context and add to wait queue
		String id = UUID.randomUUID().toString();
		RequestResponseContext<T, U> ctx = new RequestResponseContext<T, U>(id, payload);
		reqRspCtxHandler.addReq(ctx);
		
		publish(targetAppTopic, null, null);
		
		if (ctx.waitOne(timeout)) {
			return ctx.getRspPayload();			
		}
		
		return null;
	}
	
	private void publish(String topic, byte[] payload, Map<String, String> headers) {
		// Serialize payload
	    // payload
	}
	
}
