namespace br.com.badr.fw.bus.controller {
    using System;

    [AttributeUsage(AttributeTargets.Class, AllowMultiple = false)]
    public class FwControllerAttribute : Attribute {
        private readonly String _resource;

        public FwControllerAttribute(String resource) {
            _resource = resource;
        }

        public String GetResource() { 
            return _resource; 
        }
    }
}
