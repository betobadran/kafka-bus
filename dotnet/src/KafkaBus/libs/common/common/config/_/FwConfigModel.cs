namespace br.com.badr.framework.common.config {
    using System;

    public class FwConfigModel {
        public String AppType { get; set; }
        public int AppPartition { get; set; }
        public String AppHaMode { get; set; }
        public String ConfigUrl { get; set; }
    }
}
