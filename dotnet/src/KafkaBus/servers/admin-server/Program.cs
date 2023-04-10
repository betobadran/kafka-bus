namespace br.com.badr.server.admin {
    using br.com.badr.framework.common.config;
    using br.com.badr.fw.bus.comm;
    using br.com.badr.fw.bus.kafka.comm;
    using br.com.badr.server.admin.services;

    public class Program {
        public static void Main(string[] args) {
            var fwConfig = new FwConfig();

            var builder = WebApplication.CreateBuilder(args);
            builder.WebHost.UseKestrel(o => {
                o.AddServerHeader = false;
                o.ListenAnyIP(fwConfig.Get<int>("http.port"));
            });

            // Add services to the container.
            builder.Services.AddSingleton<IFwConfig>(fwConfig);
            builder.Services.AddSingleton<IConfigService, ConfigService>();
            builder.Services.AddSingleton<IFwComm, FwCommKafka>();

            builder.Services.AddControllers();
            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            var app = builder.Build();

            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment()) {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseAuthorization();


            app.MapControllers();

            var runTask = app.RunAsync();
            ForceLoad(app.Services);
            runTask.Wait();
        }

        private static void ForceLoad(IServiceProvider services) {
            services.GetService<IFwComm>();
        }
    }
}