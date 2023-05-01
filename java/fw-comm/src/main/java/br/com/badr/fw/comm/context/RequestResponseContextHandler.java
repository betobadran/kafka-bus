package br.com.badr.fw.comm.context;

import java.util.concurrent.ConcurrentHashMap;

public class RequestResponseContextHandler implements RequestResponseContextHandlerIf {
	private final ConcurrentHashMap<String, RequestResponseContext<?, ?>> reqRspCtxQueue;
	
	public RequestResponseContextHandler() {
		reqRspCtxQueue = new ConcurrentHashMap<String, RequestResponseContext<?,?>>();
	}

	@Override
	public void receivedRsp(String id, Object rsp, boolean last) {
		RequestResponseContext<?, ?> ctx = reqRspCtxQueue.get(id);
		if (ctx == null) {
			return;
		}
		
		//ctx.setRspPayload();
		
		
		if (last) {
			ctx.set();
		}
	}

	@Override
	public void addReq(RequestResponseContext<?, ?> reqCtx) {
		reqRspCtxQueue.put(reqCtx.getId(), reqCtx);
	}
}
