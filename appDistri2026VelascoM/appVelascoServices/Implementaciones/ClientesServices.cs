using appVelascoApi.accessData.Repository;
using appVelascoApi.accessData.Repository.Interfaces;
using appVelascoDTOs.DTOs;
using appVelascoEntitys.Models;
using appVelascoServices.EventMQ;
using appVelascoServices.Interfaces;
using Azure;


namespace appVelascoServices.Implementaciones
{

    public class ClientesServices : IClienteServices
    {

        private readonly IClienteRepository _clienteRepository;
        private readonly IRabbitMQServices _rabbitMQServices;

        public ClientesServices(IClienteRepository clienteRepository, IRabbitMQServices rabbitMQService)
        {
            _clienteRepository = clienteRepository;
            _rabbitMQServices = rabbitMQService;
        }
        public async Task<BaseResponse<ClienteDTOs>> ActualizarCliente(int id, ClienteDTOs cliente)
        {
            var response = new BaseResponse<ClienteDTOs>();
            try
            {
                Clientes clienteActualizar = new()
                {
                    Id = cliente.Id,
                    nombreCliente = cliente.nombreCliente,
                    apellidoCliente = cliente.apellidoCliente,
                    emailCliente = cliente.emailCliente,
                    cedulaCliente = cliente.cedulaCliente,
                    fechaNacimientoCliente = cliente.fechaNacimientoCliente,
                    telefonoCliente = cliente.telefonoCliente,
                    Estado = true,
                    CreatedAt = DateTime.Now,
                };

                clienteActualizar = await _clienteRepository.ActualizarClienteR(id, clienteActualizar);
                

                var resultDto = new ClienteDTOs()
                {
                    Id = clienteActualizar.Id,
                    nombreCliente = clienteActualizar.nombreCliente,
                    apellidoCliente = clienteActualizar.apellidoCliente,
                    emailCliente = clienteActualizar.emailCliente,
                    cedulaCliente = clienteActualizar.cedulaCliente,
                    fechaNacimientoCliente = clienteActualizar.fechaNacimientoCliente,
                    telefonoCliente = clienteActualizar.telefonoCliente,
                    Estado = true,
                };



                response.success = true;
                response.Result = resultDto;
                

                await _rabbitMQServices.PublishMessage(response.Result, "cliente_actualizar");


            }
            catch (Exception ex) {
                return new BaseResponse<ClienteDTOs>()
                {
                    success = false,
                    ErrorMessage = ex.Message,
                    Result = null
                };
            }
            return response;
            
        }

        public async Task<BaseResponse<ClienteDTOs>> CrearCliente(ClienteDTOs cliente)
        {
            var response = new BaseResponse<ClienteDTOs>();

            try
            {

                Clientes nuevoCliente = new()
                {
                    nombreCliente = cliente.nombreCliente,
                    apellidoCliente = cliente.apellidoCliente,
                    emailCliente = cliente.emailCliente,
                    cedulaCliente = cliente.cedulaCliente,
                    fechaNacimientoCliente = cliente.fechaNacimientoCliente,
                    telefonoCliente = cliente.telefonoCliente,
                    Estado = true,
                    CreatedAt = DateTime.Now,
                };

                nuevoCliente = await _clienteRepository.CrearClienteR(nuevoCliente);

                ClienteDTOs nuevoClienteDto = new()
                {
                    nombreCliente = nuevoCliente.nombreCliente,
                    apellidoCliente = nuevoCliente.apellidoCliente,
                    emailCliente = nuevoCliente.emailCliente,
                    cedulaCliente = nuevoCliente.cedulaCliente,
                    fechaNacimientoCliente = nuevoCliente.fechaNacimientoCliente,
                    telefonoCliente = nuevoCliente.telefonoCliente,
                    Estado = true
                };

                response.success = true;
                response.Result = nuevoClienteDto;

                await _rabbitMQServices.PublishMessage(response.Result, "cliente_creado");


            }
            catch (Exception ex) {
                return new BaseResponse<ClienteDTOs>()
                {
                    success = false,
                    ErrorMessage = ex.Message,
                    Result = null
                };
            }
            return response;
        }

        public async Task<BaseResponse<bool>> EliminarCliente(int id)
        {
            try { 
                bool result = await _clienteRepository.EliminarClienteR(id);
                return new BaseResponse<bool>()
                {
                    success = result,
                    Result = result
                };
            }
            catch (Exception ex) {
                return new BaseResponse<bool>()
                {
                    success = false,
                    ErrorMessage = ex.Message,
                    Result = false
                };
            }
        }

        public async Task<BaseResponse<ClienteDTOs>> LeerClientePorID(int id)
        {
            try {
                Clientes clienteEncontrado = await _clienteRepository.LeerClientePorIDR(id);

                ClienteDTOs clienteDTOs = new()
                {
                    nombreCliente = clienteEncontrado.nombreCliente,
                    apellidoCliente = clienteEncontrado.apellidoCliente,
                    emailCliente = clienteEncontrado.emailCliente,
                    cedulaCliente = clienteEncontrado.cedulaCliente,
                    fechaNacimientoCliente = clienteEncontrado.fechaNacimientoCliente,
                    telefonoCliente = clienteEncontrado.telefonoCliente,
                    Estado = true
                };

                return new BaseResponse<ClienteDTOs>() {
                    success = true,
                    Result = clienteDTOs
                };

            } catch (Exception ex) { 
                return new BaseResponse<ClienteDTOs>() {
                    success = false,
                    ErrorMessage = ex.Message,
                    Result = null
                };
            }
        }

        public async Task<BaseResponse<List<ClienteDTOs>>> ListarClientes()
        {

            try
            {
                List<Clientes> clientesEncontrados = await _clienteRepository.ListarClientesR();

                var clientesListadosDTOs = new List<ClienteDTOs>();

                foreach (var cliente in clientesEncontrados)
                {
                    ClienteDTOs clienteDTOs = new()
                    {
                        Id = cliente.Id,
                        nombreCliente = cliente.nombreCliente,
                        apellidoCliente = cliente.apellidoCliente,
                        emailCliente = cliente.emailCliente,
                        cedulaCliente = cliente.cedulaCliente,
                        fechaNacimientoCliente = cliente.fechaNacimientoCliente,
                        telefonoCliente = cliente.telefonoCliente,
                        Estado = true
                    };
                    clientesListadosDTOs.Add(clienteDTOs);
                }

                return new BaseResponse<List<ClienteDTOs>>()
                {
                    success = true,
                    Result = clientesListadosDTOs
                };


            }
            catch (Exception ex)
            {
                return new BaseResponse<List<ClienteDTOs>>()
                {
                    success = false,
                    ErrorMessage = ex.Message,
                    Result = null
                };


            }   
        }
    }
}
