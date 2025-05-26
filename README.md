# SmartJob User Manager

Reto API de usuarios. 

## Pasos para probarlo:

1. Establecer el JDK 11 en el IDE
2. Ejecutar el comando de maven para generar las clases y el archivo jar:
``` shell
mvn clean install
```
3. Ejecutar el comando de maven para iniciar la aplicación:
``` shell
mvn spring-boot:run
```
4. En caso se requiera desplegar el microservicio en Docker, ejecutar el archivo Dockerfile de la carpeta raiz.



## Validación en BD H2

1. Ingresar a la ruta http://localhost:8082/h2/ con los accesos indicados en el archivo /resources/application.properties
2. Consultar las tablas y ejecutar consultas a las tablas user y user_phone


### DDL asociado al JPA:

```sql
CREATE TABLE "user"
(
id         VARCHAR(255)                       NOT NULL PRIMARY KEY,
name       VARCHAR(100)                        NOT NULL,
email      VARCHAR(100)                        NOT NULL UNIQUE,
password   VARCHAR(255)                        NOT NULL,
created    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
modified   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
last_login TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
token      VARCHAR(255),
isactive   BOOLEAN   DEFAULT TRUE              NOT NULL
);


CREATE TABLE "user_phone"
(
id         VARCHAR(255) NOT NULL PRIMARY KEY,
number     VARCHAR(15)  NOT NULL,
citycode   VARCHAR(5)   NOT NULL,
contrycode VARCHAR(5)   NOT NULL,
user_id    VARCHAR(255),
FOREIGN KEY (user_id) REFERENCES "user" (id)
);
```
