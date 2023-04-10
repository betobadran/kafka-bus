namespace br.com.badr.fw.bus.serializer {
    using Newtonsoft.Json;
    using ProtoBuf;
    using System;
    using System.IO;
    using System.Text;

    public class FwSerializer : ISerializer {
        public T Deserialize<T>(byte[] payload, string serializeType) {
            try {
                switch (serializeType) {
                    case "protobuf":
                        using (var stream = new MemoryStream(payload)) {
                            return Serializer.Deserialize<T>(stream);
                        }
                    case "json":
                        String content = Encoding.UTF8.GetString(payload);
                        return JsonConvert.DeserializeObject<T>(content);
                }
            } catch (Exception e) {
                throw new Exception($"Error on try to deserialize type {serializeType}", e);
            }

            throw new ArgumentOutOfRangeException($"SerializeType: {serializeType} can not be handle");
        }

        public byte[] Serialize(object payload, out string type, out string serializeType) {
            byte[] data = null;
            type = null;
            serializeType = null;

            try {
                using (var stream = new MemoryStream()) { 
                    Serializer.Serialize(stream, this);
                    data = stream.ToArray();
                    type = payload.GetType().FullName;
                    serializeType = "protobuf";
                    return data;
                }
            } catch {
            }

            try {
                String content = JsonConvert.SerializeObject(payload);
                data = Encoding.UTF8.GetBytes(content);
                type = payload.GetType().FullName;
                serializeType = "json";
                return data;
            } catch {
            }

            return data;
        }
    }
}
