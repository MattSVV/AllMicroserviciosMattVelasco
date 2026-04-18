using appVelascoEntitys.Models;
using Microsoft.EntityFrameworkCore;


namespace appVelascoApi.accessData.context
{
    public class DataBaseVelascoContext : DbContext
    {
        public DataBaseVelascoContext(DbContextOptions<DataBaseVelascoContext> options) : base(options)
        {

        }

        public DbSet<Clientes> Clientes { get; set; }
        public DbSet<Direcciones> Direcciones { get; set; }
    }
}
