from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    # Database
    DATABASE_URL: str

    # RabbitMQ
    RABBITMQ_USER: str
    RABBITMQ_PASSWORD: str
    RABBITMQ_VHOST: str
    RABBITMQ_PORT: int
    RABBITMQ_HOSTNAME: str
    RABBITMQ_QUEUE: str

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"


settings = Settings()

RABBITMQ = {
    "username": settings.RABBITMQ_USER,
    "password": settings.RABBITMQ_PASSWORD,
    "virtualHost": settings.RABBITMQ_VHOST,
    "port": settings.RABBITMQ_PORT,
    "hostname": settings.RABBITMQ_HOSTNAME,
    "queue": settings.RABBITMQ_QUEUE
}