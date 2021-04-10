using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace PlannerWebApi.DTO
{
    public class LoginDTO
    {
        [Required(ErrorMessage = "Обов'язкове поле"), EmailAddress(ErrorMessage = "Не валідна пошта")]
        public string Email { get; set; }

       // [RegularExpression(pattern: "", ErrorMessage = "")]
        [Required(ErrorMessage = "Обов'язкове поле")]
        public string Password { get; set; }
    }
}
