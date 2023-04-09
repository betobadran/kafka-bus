namespace br.com.badr.server.admin.controllers {
    using br.com.badr.server.admin.services;
    using Microsoft.AspNetCore.Mvc;

    [Route("api/[controller]")]
    [ApiController]
    public class ConfigController : ControllerBase {
        private readonly IConfigService _configService;

        public ConfigController(IConfigService configService) {
            _configService = configService;
        }

        [HttpGet]
        public IActionResult Get() {
            return Ok(_configService.Get());
        }
    }
}
