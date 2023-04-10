namespace br.com.badr.server.admin.controllers {
    using br.com.badr.fw.bus.controller;
    using Microsoft.AspNetCore.Mvc;

    //[Route("api/[controller]")]
    //[ApiController]
    [FwController("controllers")]
    public class ControllersController { 
        //: ControllerBase {

        //[HttpGet]
        [FwMethodAttribute("get-many")]
        public ICollection<object> Get() {
            return new List<object>();
        }
    }
}
