package br.com.badr.fw.comm.kafka.consumer;

import br.com.badr.fw.common.config.FwConfigIf;

public abstract class FwConsumer implements Runnable {
	protected final FwConfigIf fwConfig;
	protected boolean isNotCancel = true;
	
	protected FwConsumer(FwConfigIf fwConfig) {
		this.fwConfig = fwConfig;
	}
	
	public void cancel() {
		isNotCancel = false;
	}
}
