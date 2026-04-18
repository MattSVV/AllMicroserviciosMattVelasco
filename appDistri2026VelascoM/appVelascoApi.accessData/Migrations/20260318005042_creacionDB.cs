using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace appVelascoApi.accessData.Migrations
{
    /// <inheritdoc />
    public partial class creacionDB : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Clientes",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    nombreCliente = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: false),
                    apellidoCliente = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: false),
                    emailCliente = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: false),
                    cedulaCliente = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: false),
                    telefonoCliente = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: false),
                    fechaNacimientoCliente = table.Column<DateTime>(type: "datetime2", maxLength: 50, nullable: false),
                    Estado = table.Column<bool>(type: "bit", nullable: false),
                    CreatedAt = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Clientes", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Direcciones",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    idCliente = table.Column<int>(type: "int", nullable: false),
                    provinciaDireccion = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: false),
                    ciudadDireccion = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: false),
                    calleDireccion = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: false),
                    codigoPostalDireccion = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: false),
                    clienteId = table.Column<int>(type: "int", nullable: true),
                    Estado = table.Column<bool>(type: "bit", nullable: false),
                    CreatedAt = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Direcciones", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Direcciones_Clientes_clienteId",
                        column: x => x.clienteId,
                        principalTable: "Clientes",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateIndex(
                name: "IX_Direcciones_clienteId",
                table: "Direcciones",
                column: "clienteId");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Direcciones");

            migrationBuilder.DropTable(
                name: "Clientes");
        }
    }
}
