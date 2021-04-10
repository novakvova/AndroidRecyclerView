using Microsoft.EntityFrameworkCore.Migrations;

namespace Planner.EFData.Migrations
{
    public partial class AddImageAspNetUsers : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            

            migrationBuilder.AddColumn<string>(
                name: "Image",
                table: "AspNetUsers",
                nullable: true);

           
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Image",
                table: "AspNetUsers");
        }
    }
}
