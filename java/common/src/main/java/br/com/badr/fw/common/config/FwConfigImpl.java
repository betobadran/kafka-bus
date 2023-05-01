package br.com.badr.fw.common.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class FwConfigImpl implements FwConfigIf {
	private static final String DOT = ".";

	private final FwConfigModel fwConfig;
	private final String appPartHaKey;
	private final String appPartKey;
	private final String appKey;

	private Map<String, Map<String, Object>> configCache;

	public FwConfigImpl(@Value("app.type") String appType, @Value("app.partition") int appPartition,
			@Value("app.ha.mode") String appHaMode, @Value("config.url") String configUrl) throws IOException {
		fwConfig = new FwConfigModel().setAppType(appType).setAppPartition(appPartition).setAppHaMode(appHaMode)
				.setConfigUrl(configUrl);

		appPartHaKey = fwConfig.getAppType() + DOT + fwConfig.getAppPartition() + DOT + fwConfig.getAppHaMode();
		appPartKey = fwConfig.getAppType() + DOT + fwConfig.getAppPartition();
		appKey = fwConfig.getAppType();

		configCache = new HashMap<String, Map<String, Object>>();

		load();
	}

	private void load() throws IOException {
		if (fwConfig.getConfigUrl().toLowerCase().startsWith("http://")
				|| fwConfig.getConfigUrl().toLowerCase().startsWith("https://")) {
			loadFromHttp();
		} else if (fwConfig.getConfigUrl().toLowerCase().startsWith("file://")) {
			loadFromFile();
		} else {
			throw new FileNotFoundException("Unable to handle config access url: " + fwConfig.getConfigUrl());
		}
	}

	@SuppressWarnings("unchecked")
	private void loadFromFile() throws IOException {
		File file = new File(fwConfig.getConfigUrl().substring(7));
		if (!file.exists()) {
			throw new FileNotFoundException("File not found at: " + fwConfig.getConfigUrl());
		}
		FileInputStream fis = new FileInputStream(file.getAbsolutePath());
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line);
			}

			Gson gson = new Gson();
			configCache = gson.fromJson(resultStringBuilder.toString(), configCache.getClass());
		}
	}

	@SuppressWarnings("unchecked")
	private void loadFromHttp() throws IOException {
		URL url = new URL(fwConfig.getConfigUrl());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);

		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine);
			}

			Gson gson = new Gson();
			configCache = gson.fromJson(response.toString(), configCache.getClass());
		}
	}

	@Override
	public Map<String, Map<String, Object>> get() {
		return new HashMap<>(configCache);
	}

	@Override
	public <T> T get(String configKey, Class<T> type) {
		Map<String, Object> appPartHaConfig = configCache.get(appPartHaKey);
		if (appPartHaConfig != null && appPartHaConfig.containsKey(configKey)) {
			return type.cast(appPartHaConfig.get(configKey));
		}

		Map<String, Object> appPartConfig = configCache.get(appPartKey);
		if (appPartConfig != null && appPartConfig.containsKey(configKey)) {
			return type.cast(appPartConfig.get(configKey));
		}

		Map<String, Object> appConfig = configCache.get(appKey);
		if (appConfig != null && appConfig.containsKey(configKey)) {
			return type.cast(appConfig.get(configKey));
		}

		return null;
	}

	@Override
	public FwConfigModel getLocalFwConfig() {
		return fwConfig;
	}
}
