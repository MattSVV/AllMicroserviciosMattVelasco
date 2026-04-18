using appVelascoEntitys.Models;

namespace appVelascoApi.accessData.Repository.Interfaces
{
    public interface IClienteRepository
    {
        Task<Clientes> CrearClienteR(Clientes entity);

        Task<Clientes> ActualizarClienteR(int id, Clientes entity);

        Task<bool> EliminarClienteR(int id);

        Task<Clientes> LeerClientePorIDR(int id);

        Task<List<Clientes>> ListarClientesR();
    }
}
