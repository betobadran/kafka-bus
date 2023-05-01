namespace br.com.badr.fw.bus.kafka.config {
    using System;

    public interface IKafkaCommConfig {
        String Host { get; }
        int Port { get; }
    }
}
