using appVelascoApi.accessData.context;
using appVelascoApi.accessData.Repository.Interfaces;
using appVelascoEntitys.Models;
using appVelascoServices;

namespace appVelascoApi.accessData.Repository
{
    public class ClienteRepository : CrudGenericServices<Clientes>, IClienteRepository
    {

        public ClienteRepository(DataBaseVelascoContext context) : base(context)
        { 

        }

        public Task<Clientes> ActualizarClienteR(int id, Clientes entity)
        {
            return UpdateEntity(id, entity);
        }

        public Task<Clientes> CrearClienteR(Clientes entity)
        {
            return InsertEntity(entity);
        }

        public Task<bool> EliminarClienteR(int id)
        {
            return DeleteEntity(id);
        }

        public Task<Clientes> LeerClientePorIDR(int id)
        {
            return SelectEntity(id);
        }

        public Task<List<Clientes>> ListarClientesR()
        {
            return SelectEntityAll();
        }
    }
}
