namespace br.com.badr.framework.common.config {
    public interface IFwConfig {
        IDictionary<String, IDictionary<String, String>> Get();
        T? Get<T>(String configKey);
    }
}
