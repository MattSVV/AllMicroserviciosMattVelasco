using appVelascoEntitys.Models;

namespace appVelascoApi.accessData.Repository.Interfaces
{
    public interface IDireccionRepository
    {

        Task<Direcciones> CrearDireccionR(Direcciones entity);

        Task<Direcciones> ActualizarDireccionR(int id, Direcciones entity);

        Task<bool> EliminarDireccionR(int id);

        Task<Direcciones> LeerDireccionPorIDR(int id);

        Task<List<Direcciones>> ListarDireccionR();

    }
}
