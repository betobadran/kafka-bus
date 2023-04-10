namespace br.com.badr.fw.bus.serializer {
    using System;

    public interface ISerializer {
        byte[] Serialize(Object payload, out String type, out String serializeType);
        T Deserialize<T>(byte[] payload, String serializeType);
    }
}
