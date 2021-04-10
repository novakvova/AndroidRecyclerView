using FluentValidation;
using Planner.Application.Validators;
using System;
using System.Collections.Generic;
using System.Text;

namespace Planner.Application.Account.Login
{
    public class LoginValidation : AbstractValidator<LoginCommand>
    {
        public LoginValidation()
        {
            RuleFor(x => x.Email).NotEmpty()
                .WithMessage("Не може бути пустим!")
                .EmailAddress()
                .WithMessage("Не коректна пошта");
            RuleFor(x => x.Password).Password();
        }
    }
}
