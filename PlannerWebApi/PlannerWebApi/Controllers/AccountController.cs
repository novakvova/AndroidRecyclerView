using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Planner.Application.Account;
using Planner.Application.Account.Login;
using Planner.Application.Account.Registration;
using PlannerWebApi.DTO;
using PlannerWebApi.Helpers;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace PlannerWebApi.Controllers
{
    [AllowAnonymous]
    public class AccountController : BaseController
    {
        [HttpPost]
        [Route("login")]
        public async Task<ActionResult<UserViewModel>> LoginAsync(LoginCommand command)
        {
            return await Mediator.Send(command);
        }
        [HttpPost("registration")]
        public async Task<ActionResult<UserViewModel>> RegistrationAsync(RegistrationCommand command)
        {
            return await Mediator.Send(command);
        }

        [HttpPost("upload")]
        public async Task<IActionResult> UploadFile(IFormFile file)
        {
            // change file name with its extension
            var fileName = Guid.NewGuid().ToString() +
                System.IO.Path.GetExtension(file.FileName);


            var path = Path.Combine(
                          Directory.GetCurrentDirectory(), "uploads",
                          fileName);

            using (var stream = new FileStream(path, FileMode.Create))
            {
                await file.CopyToAsync(stream);
            }
            return Ok();
        }
    }
}
