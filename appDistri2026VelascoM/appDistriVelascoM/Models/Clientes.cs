namespace appDistriVelascoM.Models
{
    public class Clientes
    {
        public int idCliente { get; set; }
        public String? nombreCliente { get; set; }

        public String? apellidoCliente { get; set; }

        public int edadCliente { get; set; }

        public int idDireccion { get; set; }

        public virtual Direcciones Direccion { get; set; }
    }
}
