using appVelascoDTOs.DTOs;

namespace appVelascoServices.Interfaces
{
    public interface IDireccionServices
    {
        Task<BaseResponse<DireccionDTOs>> CrearDireccion(DireccionDTOs direccion);

        Task<BaseResponse<DireccionDTOs>> ActualizarDireccion(int id, DireccionDTOs direccion);

        Task<BaseResponse<bool>> EliminarDireccion(int id);

        Task<BaseResponse<DireccionDTOs>> LeerDireccionPorID(int id);

        Task<BaseResponse<List<DireccionDTOs>>> ListarDirecciones();
    }
}
