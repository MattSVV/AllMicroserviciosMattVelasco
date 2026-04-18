using System.ComponentModel.DataAnnotations;

namespace appVelascoEntitys.Models
{
    public class EntityBase
    {
        [Key]
        public int Id { get; set; }
        public bool Estado { get; set; }
        public DateTime CreatedAt { get; set; }

    }

    public class Clientes : EntityBase
    {
        [Required]
        [StringLength(50)]
        public String? nombreCliente { get; set; }

        [Required]
        [StringLength(50)]
        public String? apellidoCliente { get; set; }


        [Required]
        [StringLength(50)]
        public String? emailCliente { get; set; }


        [Required]
        [StringLength(50)]
        public String? cedulaCliente { get; set; }

        [Required]
        [StringLength(50)]
        public String? telefonoCliente { get; set; }

        [Required]
        [StringLength(50)]
        public DateTime fechaNacimientoCliente { get; set; }

        public ICollection<Direcciones>? DireccionesCliente { get; set; }
    }

    public class Direcciones : EntityBase
    {

        [Required]
        public int idCliente { get; set; }

        [Required]
        [StringLength(50)]
        public String? provinciaDireccion { get; set; }

        [Required]
        [StringLength(50)]
        public String? ciudadDireccion { get; set; }

        [Required]
        [StringLength(50)]
        public String? calleDireccion { get; set; }

        [Required]
        [StringLength(50)]
        public String? codigoPostalDireccion { get; set; }

        public Clientes? cliente { get; set; }
    }
}
