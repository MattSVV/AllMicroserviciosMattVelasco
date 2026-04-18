using appVelascoDTOs.DTOs;
using appVelascoServices.EventMQ;
using appVelascoServices.Interfaces;
using Microsoft.AspNetCore.Mvc;
using System.ComponentModel.DataAnnotations;

namespace appDistriVelascoM.Controllers
{
    [ApiController]
    [Route("api/[controller]")]

    public class DireccionController : Controller
    {
        private readonly IDireccionServices _direccionService;

        public DireccionController(IDireccionServices direccionService)
        {
            _direccionService = direccionService;
        }

        [HttpGet]
        public async Task<ActionResult> ListarDirecciones()
        {
            var result = await _direccionService.ListarDirecciones();
            if (result.success)
            {
                return Ok(result.Result);
            }
            return BadRequest(result.ErrorMessage);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult> ObtenerDireccionId(int id)
        {
            var result = await _direccionService.LeerDireccionPorID(id);
            if (result.success)
            {

                return Ok(result.Result);
            }
            return BadRequest(result.ErrorMessage);
        }

        [HttpPost]
        public async Task<ActionResult> CrearDireccion([FromBody] DireccionDTOs direccion)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var result = await _direccionService.CrearDireccion(direccion);
            if (result.success)
            {

                return Ok(result.Result);
            }
            return BadRequest(result.ErrorMessage);
        }

        [HttpPut("{id}")]
        public async Task<ActionResult> ActualizarDireccion(int id, [FromBody] DireccionDTOs direccion)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var result = await _direccionService.ActualizarDireccion(id, direccion);
            if (result.success)
            {

                return Ok(result.Result);
            }
            return BadRequest(result.ErrorMessage);
        }

        // Importante: Usamos {id} en la ruta para evitar ambigüedad en Swagger
        [HttpDelete("{id}")]
        public async Task<ActionResult> EliminarDireccion(int id)
        {
            var result = await _direccionService.EliminarDireccion(id);
            if (result.success)
            {

                return Ok(result.Result);
            }
            return BadRequest(result.ErrorMessage);
        }
    }
}
