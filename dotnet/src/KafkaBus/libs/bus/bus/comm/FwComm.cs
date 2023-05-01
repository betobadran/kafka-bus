namespace br.com.badr.fw.bus.comm {
    using br.com.badr.framework.common.config;
    using br.com.badr.fw.bus.controller;
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Reflection;

    public abstract class FwComm : IFwComm {
        private readonly static String assemblyLocation = Assembly.GetAssembly(typeof(FwComm)).Location;
        
        protected readonly IFwConfig _fwConfig;
        protected readonly IDictionary<string, MethodInfo> _resourcesMap;

        protected FwComm(IFwConfig fwConfig) {
            _fwConfig = fwConfig;

            var allDlls = new FileInfo(assemblyLocation).Directory.GetFiles("*.dll", SearchOption.TopDirectoryOnly);
            foreach(var dll in allDlls) {
                Assembly.LoadFile(dll.FullName);
            }

            _resourcesMap = new Dictionary<string, MethodInfo>();
            Assembly assembly = Assembly.GetEntryAssembly();
            foreach (Type type in assembly.GetTypes()) {
                var fwController = type.GetCustomAttributes<FwControllerAttribute>();
                if (fwController.Any()) {
                    var resource = fwController.First().GetResource();

                    var methods = type.GetMethods();
                    foreach (var memberInfo in methods) {
                        var fwMethod = memberInfo.GetCustomAttributes<FwMethodAttribute>();
                        if (fwMethod.Any()) {
                            var method = fwMethod.First().GetResource();
                            _resourcesMap.Add(method, memberInfo);
                        }
                    }
                }
            }
        }

        public abstract void Publish(string resource, object payload);
        public abstract T Request<T>(string resource, object payload, TimeSpan? timeout);
        public abstract void Start(bool imAdminServer = false);
        public abstract void Stop();
        public abstract void Subscribe<T>(string resource, Action<T> callback);
    }
}
