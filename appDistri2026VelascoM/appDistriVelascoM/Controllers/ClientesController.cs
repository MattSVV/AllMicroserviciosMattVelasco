using appVelascoDTOs.DTOs;
using appVelascoServices.EventMQ;
using appVelascoServices.Interfaces;
using Microsoft.AspNetCore.Mvc;

namespace appDistriVelascoM.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class ClientesController : Controller
    {

        private readonly IClienteServices _clienteService;
        private readonly IRabbitMQServices _rabbitMQService;
        public ClientesController(IClienteServices clienteService, IRabbitMQServices rabbitMQServices)
        {
            _clienteService = clienteService;
            _rabbitMQService = rabbitMQServices;
        }
        
        [HttpGet]
        public async Task<ActionResult> ListarClientes()
        {
            var result = await _clienteService.ListarClientes();
            if (result.success)
            {
                return Ok(result.Result);
            }
            else
            {
                return BadRequest(result.ErrorMessage);
            }
        }

        [HttpGet("{id}")]
        public async Task<ActionResult> obtenerClienteId(int id)
        {
            var result = await _clienteService.LeerClientePorID(id);
            if (result.success)
            {
                return Ok(result.Result);
            }
            else
            {
                return BadRequest(result.ErrorMessage);
            }
        }

        [HttpPost]
        public async Task<ActionResult> CrearCliente([FromBody] ClienteDTOs cliente)
        {

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var result = await _clienteService.CrearCliente(cliente);
            if (result.success)
            {
                return Ok(result.Result);
            }
            else
            {
                return BadRequest(result.ErrorMessage);
            }
        }

        [HttpPut("{id}")]
        public async Task<ActionResult> ActualizarCliente(int id, [FromBody] ClienteDTOs cliente)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            var result = await _clienteService.ActualizarCliente(id, cliente);
            if (result.success)
            {
                return Ok(result.Result);
            }
            else
            {
                return BadRequest(result.ErrorMessage);
            }
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult> EliminarCliente(int id)
        {
            var result = await _clienteService.EliminarCliente(id);
            if (result.success)
            {
                return Ok(result.Result);
            }
            else
            {
                return BadRequest(result.ErrorMessage);
            }
        }
    }
}
