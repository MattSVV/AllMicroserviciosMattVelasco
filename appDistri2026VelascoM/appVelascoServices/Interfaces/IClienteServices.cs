using appVelascoDTOs.DTOs;

namespace appVelascoServices.Interfaces
{
    public interface IClienteServices
    {
        Task<BaseResponse<ClienteDTOs>> CrearCliente(ClienteDTOs cliente);

        Task<BaseResponse<ClienteDTOs>> ActualizarCliente(int id, ClienteDTOs cliente);

        Task<BaseResponse<bool>> EliminarCliente(int id);

        Task<BaseResponse<ClienteDTOs>> LeerClientePorID(int id);

        Task<BaseResponse<List<ClienteDTOs>>> ListarClientes();

    }
}
