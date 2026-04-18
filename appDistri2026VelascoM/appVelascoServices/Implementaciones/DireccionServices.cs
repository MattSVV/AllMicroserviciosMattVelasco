using appVelascoApi.accessData.Repository.Interfaces;
using appVelascoDTOs.DTOs;
using appVelascoEntitys.Models;
using appVelascoServices.EventMQ;
using appVelascoServices.Interfaces;

namespace appVelascoServices.Implementaciones
{
    public class DireccionServices : IDireccionServices
    {
        private readonly IDireccionRepository _direccionRepository;
        private readonly IClienteRepository _clienteRepository;
        private readonly IRabbitMQServices _rabbitMQServices;

        
        public DireccionServices(IDireccionRepository direccionRepository, IClienteRepository clienteRepository, IRabbitMQServices rabbitMQServices)
        {
            _direccionRepository = direccionRepository;
            _clienteRepository = clienteRepository;
            _rabbitMQServices = rabbitMQServices;
        }

        public async Task<BaseResponse<DireccionDTOs>> CrearDireccion(DireccionDTOs direccion)
        {
            try
            {
                Direcciones nuevaDireccion = new()
                {
                    idCliente = direccion.idCliente,
                    provinciaDireccion = direccion.provinciaDireccion,
                    ciudadDireccion = direccion.ciudadDireccion,
                    calleDireccion = direccion.calleDireccion,
                    codigoPostalDireccion = direccion.codigoPostalDireccion,
                    Estado = true,
                    CreatedAt = DateTime.Now
                };

                var cliente = await _clienteRepository.LeerClientePorIDR(direccion.idCliente);

                if (cliente == null) throw new Exception("Cliente no encontrado");

                var resultado = await _direccionRepository.CrearDireccionR(nuevaDireccion);

                direccion.Id = resultado.Id;
                direccion.Estado = true;

                await EnviarMensajeClienteDireccion(resultado);


                return new BaseResponse<DireccionDTOs> { success = true, Result = direccion };
            }
            catch (Exception ex)
            {
                return new BaseResponse<DireccionDTOs> { success = false, ErrorMessage = ex.Message };
            }
        }

        public async Task<BaseResponse<DireccionDTOs>> ActualizarDireccion(int id, DireccionDTOs direccion)
        {
            try
            {
                Direcciones direccionActualizar = new()
                {
                    Id = direccion.Id,
                    idCliente = direccion.idCliente,
                    provinciaDireccion = direccion.provinciaDireccion,
                    ciudadDireccion = direccion.ciudadDireccion,
                    calleDireccion = direccion.calleDireccion,
                    codigoPostalDireccion = direccion.codigoPostalDireccion,
                    Estado = true
                };

                var result = await _direccionRepository.ActualizarDireccionR(id, direccionActualizar);
                await _rabbitMQServices.PublishMessage(result, "clienteDireccionEvent");


                return new BaseResponse<DireccionDTOs> { success = true, Result = direccion };
            }
            catch (Exception ex)
            {
                return new BaseResponse<DireccionDTOs> { success = false, ErrorMessage = ex.Message };
            }
        }

        public async Task<BaseResponse<bool>> EliminarDireccion(int id)
        {
            try
            {
                bool result = await _direccionRepository.EliminarDireccionR(id);
                return new BaseResponse<bool> { success = result, Result = result };
            }
            catch (Exception ex)
            {
                return new BaseResponse<bool> { success = false, ErrorMessage = ex.Message, Result = false };
            }
        }

        public async Task<BaseResponse<DireccionDTOs>> LeerDireccionPorID(int id)
        {
            try
            {
                var direccion = await _direccionRepository.LeerDireccionPorIDR(id);
                if (direccion == null) throw new Exception("Dirección no encontrada");

                var dto = new DireccionDTOs
                {
                    Id = direccion.Id,
                    idCliente = direccion.idCliente,
                    provinciaDireccion = direccion.provinciaDireccion,
                    ciudadDireccion = direccion.ciudadDireccion,
                    calleDireccion = direccion.calleDireccion,
                    codigoPostalDireccion = direccion.codigoPostalDireccion,
                    Estado = direccion.Estado
                };

                return new BaseResponse<DireccionDTOs> { success = true, Result = dto };
            }
            catch (Exception ex)
            {
                return new BaseResponse<DireccionDTOs> { success = false, ErrorMessage = ex.Message };
            }
        }

        public async Task<BaseResponse<List<DireccionDTOs>>> ListarDirecciones()
        {
            try
            {
                var lista = await _direccionRepository.ListarDireccionR();
                var listaDto = lista.Select(d => new DireccionDTOs
                {
                    Id = d.Id,
                    idCliente = d.idCliente,
                    provinciaDireccion = d.provinciaDireccion,
                    ciudadDireccion = d.ciudadDireccion,
                    calleDireccion = d.calleDireccion,
                    codigoPostalDireccion = d.codigoPostalDireccion,
                    Estado = d.Estado
                }).ToList();



                return new BaseResponse<List<DireccionDTOs>> { success = true, Result = listaDto };
            }
            catch (Exception ex)
            {
                return new BaseResponse<List<DireccionDTOs>> { success = false, ErrorMessage = ex.Message };
            }
        }

        private async Task EnviarMensajeClienteDireccion(Direcciones direccion)
        {
            var cliente = await _clienteRepository.LeerClientePorIDR(direccion.idCliente);

            var direccionClienteEvent = new DireccionClienteEventDto();
            direccionClienteEvent.ClienteId = cliente.Id;
            //consultar cliente por id
            direccionClienteEvent.NombreCompleto = $"{cliente.nombreCliente} {cliente.apellidoCliente}";
            direccionClienteEvent.Email = cliente.emailCliente!;
            direccionClienteEvent.DireccionCompleta = $"{direccion.ciudadDireccion} - {direccion.provinciaDireccion} - {direccion.calleDireccion} - {direccion.codigoPostalDireccion}";
            await _rabbitMQServices.PublishMessage(direccionClienteEvent, "clienteDireccionEvent");
        }
    }
}
    
