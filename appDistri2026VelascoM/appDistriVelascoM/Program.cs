using appVelascoApi.accessData.context;
using appVelascoApi.accessData.Repository;
using appVelascoApi.accessData.Repository.Interfaces;
using appVelascoDTOs.EventMQ;
using appVelascoServices.EventMQ;
using appVelascoServices.Implementaciones;
using appVelascoServices.Interfaces;
using Microsoft.EntityFrameworkCore;

DotNetEnv.Env.Load();


var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var server = Environment.GetEnvironmentVariable("DB_SERVER");
var database = Environment.GetEnvironmentVariable("DB_NAME");
var user = Environment.GetEnvironmentVariable("DB_USER");
var password = Environment.GetEnvironmentVariable("DB_PASSWORD");

var connectionString = $"Server={server};Database={database};User Id={user};Password={password};TrustServerCertificate=True;Encrypt=False";


builder.Services.AddDbContext<DataBaseVelascoContext>(options =>
{
    options.UseSqlServer(connectionString);
    options.LogTo(Console.WriteLine, LogLevel.Information).EnableSensitiveDataLogging();

});



builder.Services.Configure<RabbitMQSettings>(builder.Configuration.GetSection("rabbitmq"));


//declarar servicio y repositorios
builder.Services.AddScoped<IClienteRepository, ClienteRepository>();
builder.Services.AddScoped<IDireccionRepository, DireccionRepository>();

builder.Services.AddScoped<IClienteServices, ClientesServices>();
builder.Services.AddScoped<IDireccionServices, DireccionServices>();

builder.Services.AddSingleton<IRabbitMQServices, RabbitMQServices>();

builder.Services.AddCors(options => {
    options.AddPolicy("AllowAll", builder => {
        builder.AllowAnyOrigin().AllowAnyMethod().AllowAnyHeader();
    });
});

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.UseCors("AllowAll");

app.MapControllers();

app.Run();
