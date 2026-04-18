using System.ComponentModel.DataAnnotations;

namespace appVelascoDTOs.DTOs
{
    public class ClienteDTOs
    {
        public int Id { get; set; }
        public bool Estado { get; set; }

        [Required(ErrorMessage = "El nombre es obligatorio")]
        public String? nombreCliente { get; set; }

        [Required(ErrorMessage = "El apellido es obligatorio")]
        public String? apellidoCliente { get; set; }


        [Required(ErrorMessage = "El email es obligatorio")]
        [EmailAddress(ErrorMessage = "El formato del email no es válido")]
        [RegularExpression(@"^[a-zA-Z0-9._%+-]+@(gmail|outlook|hotmail|yahoo)\.com$",
        ErrorMessage = "Solo se permiten correos gmail, outlook, hotmail o yahoo")]
        public String? emailCliente { get; set; }


        [Required(ErrorMessage = "La cedula es obligatorio")]
        [StringLength(13, MinimumLength = 10,
        ErrorMessage = "La cédula debe tener entre 10 y 13 caracteres")]
        [RegularExpression(@"^\d+$",
        ErrorMessage = "La cédula solo puede contener números")]
        public String? cedulaCliente { get; set; }

        [Required(ErrorMessage = "El telefono es obligatorio")]
        [StringLength(10, MinimumLength = 6,
        ErrorMessage = "el telefono o celular deben ser correctos")]
        [RegularExpression(@"^\d+$",
        ErrorMessage = "El telefono solo puede contener números")]

        public String? telefonoCliente { get; set; }

        [Required(ErrorMessage = "La Fecha de nacimiento es obligatorio")]
        public DateTime fechaNacimientoCliente { get; set; }

        
    }
}
