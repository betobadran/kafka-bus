package br.com.badr.fw.comm.serializer;

public class FwSerializerModel {
	private byte[] payload;
	private String contentType;
	
	public byte[] getPayload() {
		return payload;
	}
	public String getContentType() {
		return contentType;
	}
	public void setPayload(byte[] payload) {
		this.payload = payload;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
