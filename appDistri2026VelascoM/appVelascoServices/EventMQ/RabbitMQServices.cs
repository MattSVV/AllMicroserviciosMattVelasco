using appVelascoDTOs.EventMQ;
using Microsoft.Extensions.Options;
using Newtonsoft.Json;
using RabbitMQ.Client;
using System.Text;

namespace appVelascoServices.EventMQ
{
    public class RabbitMQServices : IRabbitMQServices
    {
        private readonly RabbitMQSettings _settings;

        // Corregido: El IOptions debe apuntar a la clase de configuración, no al servicio
        public RabbitMQServices(IOptions<RabbitMQSettings> options)
        {
            _settings = options.Value;
        }

        public async Task PublishMessage<T>(T message, string queueName)
        {
            var factory = new ConnectionFactory()
            {
                HostName = _settings.Hostname,
                UserName = _settings.Username,
                Password = _settings.Password
            };

            // Creamos la conexión y el canal
            using var connection = await factory.CreateConnectionAsync();
            using var channel = await connection.CreateChannelAsync();

            // Declaramos la cola (aseguramos que exista)
            await channel.QueueDeclareAsync(
                queue: queueName,
                durable: true,
                exclusive: false,
                autoDelete: false,
                arguments: null);

            // Serializamos el objeto a JSON
            var json = JsonConvert.SerializeObject(message);
            var body = Encoding.UTF8.GetBytes(json);

            // Publicamos el mensaje
            // En versiones nuevas de RabbitMQ.Client se usa BasicPublishAsync
            await channel.BasicPublishAsync(
                exchange: string.Empty, // Exchange por defecto
                routingKey: queueName,
                mandatory: false,
                basicProperties: new BasicProperties(), // Propiedades básicas
                body: body);

            Console.WriteLine($" [x] Enviado a {queueName}: {json}");
        }
    }
}
