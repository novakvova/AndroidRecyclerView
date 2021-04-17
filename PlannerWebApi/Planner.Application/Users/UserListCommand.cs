using MediatR;
using Planner.Application.Account;
using System;
using System.Collections.Generic;
using System.Text;

namespace Planner.Application.Users
{
    public class UserListCommand : IRequest<List<UserViewModel>>
    {
        public string UserName { get; set; }
    }
}
