//package br.com.badr.comm.kafka;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.kafka.clients.admin.AdminClientConfig;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.KafkaAdmin;
//
//@Configuration
//public class KafkaTopicConfig {
//    
//    @Value(value = "${spring.kafka.bootstrap-servers}")
//    private String bootstrapAddress;
//
//    @Value(value="${app.type}")
//    private String appType;
//    
//    @Value(value="${app.partition}")
//    private String appPartition;
//    
//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        return new KafkaAdmin(configs);
//    }
//    
//    @Bean
//    public NewTopic topic1() {
//         return new NewTopic(appType + ".request", 1, (short) 1);
//    }
//    
//    @Bean
//    public NewTopic topic2() {
//    	return new NewTopic(appType + ".handler", 1, (short) 1);
//    }
//    
//    @Bean
//    public NewTopic topic3() {
//    	return new NewTopic(appType + ".broadcast", 1, (short) 1);
//    }
//}