from api.jobs.consumer import start
from api.core.database import engine, Base

# Code First también aquí
Base.metadata.create_all(bind=engine)

if __name__ == "__main__":
    start()