namespace appDistriVelascoM.Models
{
    public class Direcciones
    {
        public int idDireccion { get; set; }

        public String? provinciaDireccion { get; set; }

        public String? ciudadDireccion { get; set; }

        public String? calleDireccion { get; set; }

        public String? codigoPostalDireccion { get; set; }

        public virtual ICollection<Clientes> Clientes { get; set; }
    }
}
