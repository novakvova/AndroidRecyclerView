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
        private readonly DataContext _context;

        public LoginHandler(DataContext context, UserManager<AppUser> userManager, IJWTGenerator jwtGenerator)
        {
            _context = context;
            _userManager = userManager;
            _jwtGenerator = jwtGenerator;
        }

        public async Task<UserViewModel> Handle(LoginCommand request, CancellationToken cancellationToken)
        {
            return new UserViewModel
            {
                DisplayName = "Temporary Data for Testing",
                Token = "asdfalflaskdfalsdfj",
                UserName = "Temporary Data for Testing",
                Image = null
            };

            throw new Exception("Client creation failed");
        }
    }
}
