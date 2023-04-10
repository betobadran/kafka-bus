namespace br.com.badr.fw.bus.comm {
    using System;

    public interface IFwComm {
        void Start(bool imAdminServer = false);
        void Stop();
        T Request<T>(String app, String resource, Object payload, TimeSpan? timeout);
        void Publish(String resource, Object payload);
        void Subscribe<T>(String app, String resource, Action<T> callback);
    }
}
