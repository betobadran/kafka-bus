namespace br.com.badr.server.admin.services {
    public interface IConfigService {
        public IDictionary<String, IDictionary<String, String>> Get();
    }
}
