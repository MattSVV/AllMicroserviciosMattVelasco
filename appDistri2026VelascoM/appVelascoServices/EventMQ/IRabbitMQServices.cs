namespace appVelascoServices.EventMQ
{
    public interface IRabbitMQServices
    {
        Task PublishMessage<T>(T message, string queueName);
    }
}
