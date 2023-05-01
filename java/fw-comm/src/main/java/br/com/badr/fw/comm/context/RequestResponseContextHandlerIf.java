package br.com.badr.fw.comm.context;

public interface RequestResponseContextHandlerIf {
	void receivedRsp(String id, Object rsp, boolean last);
	void addReq(RequestResponseContext<?, ?> reqCtx);
}
