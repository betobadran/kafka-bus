package br.com.badr.fw.comm;

import br.com.badr.fw.common.config.FwConfigIf;

public abstract class CommImpl implements CommIf {
	protected final FwConfigIf fwConfig;
	
	protected CommImpl(FwConfigIf fwConfig) {
		this.fwConfig = fwConfig; 
	}
}
