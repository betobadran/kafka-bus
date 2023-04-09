namespace br.com.badr.framework.common.config {
    using Newtonsoft.Json;
    using System.Reflection;

    public class FwConfig : IFwConfig {
        private readonly static String assemblyLocation = Assembly.GetAssembly(typeof(FwConfig)).Location;

        private readonly FwConfigModel _fwConfig;
        private readonly string _appPartHaKey;
        private readonly string _appPartKey;
        private readonly string _appKey;

        private IDictionary<String, IDictionary<String, String>> _configCache;
        private HttpClient _httpClient;

        public FwConfig() {
            var fileInfo = new FileInfo(assemblyLocation);
            var filePath = Path.Combine(fileInfo.DirectoryName, "config", "fwconfig.json");
            if (!File.Exists(filePath)) {
                throw new FileNotFoundException($"FwConfig file not fount at: {filePath}");
            }
            var configStr = File.ReadAllText(filePath);
            if (String.IsNullOrEmpty(configStr)) {
                throw new FileLoadException("Fail to load FwConfig, content was null");
            }
            _fwConfig = JsonConvert.DeserializeObject<FwConfigModel>(configStr);

            _appPartHaKey = $"{_fwConfig.AppType}.{_fwConfig.AppPartition}.{_fwConfig.AppHaMode}";
            _appPartKey = $"{_fwConfig.AppType}.{_fwConfig.AppPartition}";
            _appKey = _fwConfig.AppType;

            Load();
        }

        private void Load() {
            if (_fwConfig.ConfigUrl.ToLower().StartsWith("http://") || _fwConfig.ConfigUrl.ToLower().StartsWith("https://")) {
                LoadFromHttp();
            } else if (_fwConfig.ConfigUrl.ToLower().StartsWith("file://")) {
                LoadFromFile();
            } else {
                throw new FileLoadException($"Unable to handle config access url: {_fwConfig.ConfigUrl}");
            }
        }

        private void LoadFromFile() {
            var filePath = _fwConfig.ConfigUrl.Substring("file://".Length);
            if (!File.Exists(filePath)) {
                throw new FileNotFoundException($"FwConfig file not fount at: {filePath}");
            }
            var configStr = File.ReadAllText(filePath);
            ParseAndStoreConfig(configStr);
        }

        private void LoadFromHttp() {
            if (_httpClient == null) {
                _httpClient = new HttpClient();
            }

            var httpRspTask = _httpClient.GetAsync(_fwConfig.ConfigUrl);
            httpRspTask.Wait();
            if (!httpRspTask.Result.IsSuccessStatusCode) {
                throw new FileLoadException($"Fail to load config from url: {_fwConfig.ConfigUrl}");
            }

            var contentTask = httpRspTask.Result.Content.ReadAsStringAsync();
            contentTask.Wait();
            ParseAndStoreConfig(contentTask.Result);
        }

        private void ParseAndStoreConfig(String content) {
            if (String.IsNullOrEmpty(content)) {
                throw new FileLoadException("Fail to generate config map, content was null");
            }
            _configCache = JsonConvert.DeserializeObject<IDictionary<String, IDictionary<String, String>>>(content);
        }

        public T? Get<T>(string configKey) {
            String? value = null;
            if (_configCache.TryGetValue(_appPartHaKey, out var appPartHaConfig) && appPartHaConfig.TryGetValue(configKey, out value)) {
                return (T)Convert.ChangeType(value, typeof(T));
            } else if (_configCache.TryGetValue(_appPartKey, out var appPartConfig) && appPartConfig.TryGetValue(configKey, out value)) {
                return (T)Convert.ChangeType(value, typeof(T));
            } else if (_configCache.TryGetValue(_appKey, out var appConfig) && appConfig.TryGetValue(configKey, out value)) {
                return (T)Convert.ChangeType(value, typeof(T));
            } else if (_configCache.TryGetValue("all", out var allConfig) && allConfig.TryGetValue(configKey, out value)) {
                return (T)Convert.ChangeType(value, typeof(T));
            }

            return default;
        }

        public IDictionary<String, IDictionary<String, String>> Get() {
            return new Dictionary<String, IDictionary<String, String>>(_configCache);
        }
    }
}
