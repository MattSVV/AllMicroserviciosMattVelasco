

using System.ComponentModel.DataAnnotations;

namespace appVelascoDTOs.DTOs
{
    public class DireccionDTOs
    {
        public int Id { get; set; }
        public bool Estado { get; set; }

        [Required(ErrorMessage = "El id de Cliente es obligatorio")]
        public int idCliente { get; set; }

        [Required(ErrorMessage = "La Provincia es obligatorio")]
        public String? provinciaDireccion { get; set; }

        [Required(ErrorMessage = "La ciudad es obligatorio")]
        public String? ciudadDireccion { get; set; }

        [Required(ErrorMessage = "La calle es obligatorio")]
        public String? calleDireccion { get; set; }

        [Required(ErrorMessage = "El Codigo postal es obligatorio")]
        [RegularExpression(@"^\d+$",
        ErrorMessage = "El codigo postla solo puede contener números")]
        public String? codigoPostalDireccion { get; set; }

    }
}
