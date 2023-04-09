namespace br.com.badr.server.admin.services {
    using br.com.badr.framework.common.config;

    public class ConfigService : IConfigService {
        private readonly IFwConfig _fwConfig;

        public ConfigService(IFwConfig fwConfig) {
            _fwConfig = fwConfig;
        }

        public IDictionary<string, IDictionary<string, string>> Get() {
            return _fwConfig.Get();
        }
    }
}
