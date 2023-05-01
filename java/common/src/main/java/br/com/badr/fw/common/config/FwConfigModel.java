package br.com.badr.fw.common.config;

public class FwConfigModel {
	private String appType;
	private int appPartition;
	private String appHaMode;
	private String configUrl;
	
	public String getAppType() {
		return appType;
	}
	
	public int getAppPartition() {
		return appPartition;
	}
	
	public String getAppHaMode() {
		return appHaMode;
	}
	
	public String getConfigUrl() {
		return configUrl;
	}
	
	public FwConfigModel setAppType(String appType) {
		this.appType = appType;
		return this;
	}
	
	public FwConfigModel setAppPartition(int appPartition) {
		this.appPartition = appPartition;
		return this;
	}
	
	public FwConfigModel setAppHaMode(String appHaMode) {
		this.appHaMode = appHaMode;
		return this;
	}
	
	public FwConfigModel setConfigUrl(String configUrl) {
		this.configUrl = configUrl;
		return this;
	}
}
