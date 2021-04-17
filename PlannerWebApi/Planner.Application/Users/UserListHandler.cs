using MediatR;
using Microsoft.AspNetCore.Identity;
using Planner.Application.Account;
using Planner.Application.Exceptions;
using Planner.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Planner.Application.Users
{
    public class UserListHandler : IRequestHandler<UserListCommand, List<UserViewModel>>
    {
        private readonly UserManager<AppUser> _userManager;
        public UserListHandler(UserManager<AppUser> userManager)
        {
            _userManager = userManager;
        }
        public async Task<List<UserViewModel>> Handle(UserListCommand request, CancellationToken cancellationToken)
        {
            var users = _userManager.Users.Select(x =>
            new UserViewModel
            {
                DisplayName = x.DisplayName,
                UserName = x.UserName,
                Image = "no_image.jpg"
            }).ToList();
            if (users == null)
            {
                throw new RestException(HttpStatusCode.NotFound);
            }

            return users;
            //throw new RestException(HttpStatusCode.NotFound);
        }
    }
}
