package br.com.badr.fw.common.config;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class FwConfigTest {
	
	@Test
	public void getFileTest() throws IOException {
		FwConfigIf fwConfig = new FwConfigImpl("app.type", -1, "standalone", "file://fw-common/src/test/resources/environment.json");
		fwConfig.get();
	}
	
}
