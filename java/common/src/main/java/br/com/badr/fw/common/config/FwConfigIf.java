package br.com.badr.fw.common.config;

import java.util.Map;

public interface FwConfigIf {
	FwConfigModel getLocalFwConfig(); 
	Map<String, Map<String, Object>> get();
	<T extends Object> T get(String configKey, Class<T> type);
}
