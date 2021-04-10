using MediatR;
using Microsoft.AspNetCore.Identity;
using Planner.Application.Exceptions;
using Planner.Application.Interfaces;
using Planner.Domain;
using Planner.EFData;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Planner.Application.Account.Login
{
    class LoginHandler : IRequestHandler<LoginCommand, UserViewModel>
    {
        private readonly UserManager<AppUser> _userManager;
        private readonly IJWTGenerator _jwtGenerator;
        private readonly SignInManager<AppUser> _signInManager;

        public LoginHandler(SignInManager<AppUser> signInManager, 
            UserManager<AppUser> userManager, IJWTGenerator jwtGenerator)
        {
            _signInManager = signInManager;
            _userManager = userManager;
            _jwtGenerator = jwtGenerator;
        }

        public async Task<UserViewModel> Handle(LoginCommand request, CancellationToken cancellationToken)
        {
            var user = await _userManager.FindByNameAsync(request.Email);
            if (user == null)
            {
                throw new RestException(HttpStatusCode.Unauthorized, 
                    new { Invalid = new[] { "Дані вказано не вірно!" } });
            }
            var result = await _signInManager
                .CheckPasswordSignInAsync(user, request.Password, false);

            return new UserViewModel
            {
                DisplayName = user.DisplayName,
                Token = _jwtGenerator.CreateToken(user),
                UserName = user.UserName,
                Image = null
            };

            throw new Exception("Client creation failed");
        }
    }
}
