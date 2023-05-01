namespace br.com.badr.framework.common.config {
    using System;
    using System.Collections.Generic;

    public interface IFwConfig {
        IDictionary<String, IDictionary<String, Object>> Get();
        T Get<T>(String configKey);
    }
}
