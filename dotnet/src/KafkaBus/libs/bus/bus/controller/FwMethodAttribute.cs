namespace br.com.badr.fw.bus.controller {
    using System;

    [AttributeUsage(AttributeTargets.Method, AllowMultiple = false)]
    public class FwMethodAttribute : Attribute {
        private readonly String _resource;
        public FwMethodAttribute(String resource) {
            _resource = resource;
        }

        public String GetResource() {
            return _resource;
        }
    }
}
