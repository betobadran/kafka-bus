package br.com.badr.fw.comm.serializer;

public interface FwSerializerFacadeIf {
	FwSerializerModel Serialize(Object payload);
	<T extends Object> T Deserialize(FwSerializerModel payload, Class<T> type);
}
