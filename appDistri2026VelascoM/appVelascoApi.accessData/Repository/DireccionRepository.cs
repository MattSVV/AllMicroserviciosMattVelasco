using appVelascoApi.accessData.context;
using appVelascoApi.accessData.Repository.Interfaces;
using appVelascoEntitys.Models;
using appVelascoServices;

namespace appVelascoApi.accessData.Repository
{
    public class DireccionRepository : CrudGenericServices<Direcciones>, IDireccionRepository
    {
        public DireccionRepository(DataBaseVelascoContext context) : base(context)
        {

        }

        public Task<Direcciones> ActualizarDireccionR(int id, Direcciones entity)
        {
            return UpdateEntity(id, entity); ;
        }

        public Task<Direcciones> CrearDireccionR(Direcciones entity)
        {
            return InsertEntity(entity);
        }

        public Task<bool> EliminarDireccionR(int id)
        {
            return DeleteEntity(id);
        }

        public Task<Direcciones> LeerDireccionPorIDR(int id)
        {
            return SelectEntity(id);
        }

        public Task<List<Direcciones>> ListarDireccionR()
        {
            return SelectEntityAll();
        }
    }
}
