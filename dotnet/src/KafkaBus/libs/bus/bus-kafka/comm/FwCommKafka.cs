namespace br.com.badr.fw.bus.kafka.comm {
    using br.com.badr.framework.common.config;
    using br.com.badr.fw.bus.comm;
    using System;

    public class FwCommKafka : IFwComm {
        private readonly IFwConfig _fwConfig;

        public void Publish(string resource, object payload) {
            throw new NotImplementedException();
        }

        public T Request<T>(string app, string resource, object payload, TimeSpan? timeout) {
            throw new NotImplementedException();
        }

        public void Start() {
            throw new NotImplementedException();
        }

        public void Stop() {
            throw new NotImplementedException();
        }

        public void Subscribe<T>(string app, string resource, Action<T> callback) {
            throw new NotImplementedException();
        }
    }
}
