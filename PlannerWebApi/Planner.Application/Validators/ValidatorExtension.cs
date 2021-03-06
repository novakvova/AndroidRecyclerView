using FluentValidation;
using System;
using System.Collections.Generic;
using System.Text;

namespace Planner.Application.Validators
{
    public static class ValidatorExtensions
    {
        public static IRuleBuilder<T, string> Password<T>(this IRuleBuilder<T, string> ruleBuilder)
        {
            var options = ruleBuilder
              .NotEmpty().WithMessage("Не може бути пустим")
              .MinimumLength(6).WithMessage("Password must be at least 6 characters")
              .Matches("[A-Z]").WithMessage("Password must contain 1 uppercase letter")
              .Matches("[0-9]").WithMessage("Password must contain a number")
              .Matches("[^a-zA-Z0-9]").WithMessage("Password must contain non alphanumeric");

            return options;
        }
    }
}
