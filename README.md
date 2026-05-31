services:
  postgres:
    image: postgres:16-alpine
    container_name: mykart-postgres
    environment:
      POSTGRES_DB: Test
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: database
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./db/init.sql:/docker-entrypoint-initdb.d/init.sql

  backend:
    image: rg6035656/my-kart:v1
    depends_on:
      - postgres
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/Test
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: database
    ports:
      - "8089:8089"

  frontend:
    image: rg6035656/mykart-fe:latest
    ports:
      - "3000:80"

volumes:
  postgres_data:
