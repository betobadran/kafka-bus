namespace br.com.badr.fw.bus.kafka.comm {
    using br.com.badr.framework.common.config;
    using br.com.badr.fw.bus.comm;
    using Confluent.Kafka;
    using System;
    using System.Reflection;
    using System.Threading;

    public class FwCommKafka : FwComm, IFwComm {
        private Thread controllerConsumerThread;
        private IConsumer<String, byte[]> controllerConsumer;
        private IProducer<String, byte[]> controllerProducer;

        private Thread eventsProducerThread;
        private IProducer<String, byte[]> eventsProducer;
        private IConsumer<String, byte[]> eventsConsumer;

        private Thread reqRspConsumerThread;
        private IProducer<String, byte[]> reqRspProducer;
        private IConsumer<String, byte[]> reqRspConsumer;

        public FwCommKafka(IFwConfig fwConfig) : base(fwConfig) {

        }

        public override void Start(bool imAdminServer = false) {
            
        }

        public override void Stop() {
            throw new NotImplementedException();
        }

        public override void Publish(string resource, object payload) {
            throw new NotImplementedException();
        }

        public override T Request<T>(string resource, object payload, TimeSpan? timeout) {
            throw new NotImplementedException();
        }

        public override void Subscribe<T>(string resource, Action<T> callback) {
            throw new NotImplementedException();
        }
    }
}
