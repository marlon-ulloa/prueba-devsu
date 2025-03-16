# Prueba Técnica/Práctica

## Instrucciones para levantar el proyecto completo

1. Dentro del directorio del proyecto, abrir una terminal y ejecutar el siguietne comando:
   ```
   docker compose up --build
   ```
2. Una vez ejecutado el comando, se iniciará la descarga de los contenedores y posterior el levantamiento de todos los aplicativos necesarios.

## Instrucciones para ejecutar las pruebas unitarias y de integración
Las pruebas unitarias se realizaron con la librería de Junit 5, y las pruebas de integhración con Mvc de Spring y Karate DSL.
Todas las pruebas se ejecutan a través del comando `mvn test` en ambos microservicios. Tomar en cuenta que debe ser la última version de maven 3.9.9 y la versión 21 de jdk de JAVA.
**Importante: Para la ejecucion de las pruebas es necesario primero levantar todos los contenedores, posterior a ello, en los archivos `application.properties` (ruta: nombremicroservicio/src/main/resources/) de ambos microservicios se debe cambiar en ambos microservicios la línea: `spring.kafka.bootstrap-servers=kafka:9092` por `spring.kafka.bootstrap-servers=localhost:29092`. Adicional, en el mismo archivo, en el microservicio msclient cambiar la línea `spring.datasource.url=jdbc:postgresql://db_cliente:5432/clientedb` por `spring.datasource.url=jdbc:postgresql://localhost:5433/clientedb` y en el microservicio msaccount cambiar la línea `spring.datasource.url=jdbc:postgresql://db_cuenta:5432/cuentadb` por `spring.datasource.url=jdbc:postgresql://localhost:5434/cuentadb`. COn esos cambios se puede conectar a los contenedores el momento de realizar las pruebas**
Para ejecutar las pruebas de forma individual, es decir, primero las pruebas unitarias y luego las pruebas de integración se deben seguir los siguientes pasos:
### Microservicio msclient
1. Ejecución de pruebas unitarias:
   ```
   mvn -Dtest=ServiceClientTest test
   ```
<img width="1216" alt="pruebas unitarias msclient" src="https://github.com/user-attachments/assets/a6f53cf5-d0b3-42a3-9c85-22ad49fdf90c" />

2. Ejecución de pruebas de integración con Mvc de Spring:
  ```
   mvn -Dtest=ClientControllerIntegrationTest test
   ```
<img width="1240" alt="pruebas de integracion spring" src="https://github.com/user-attachments/assets/387da82b-c889-40fc-8528-bd33953a80b4" />

3. Ejecución de pruebas de integración con Karate:
  ```
   mvn -Dtest=ClientTest test
   ```
<img width="1130" alt="pruebas de integracion karate dsl" src="https://github.com/user-attachments/assets/c74335af-93f4-43d3-a176-ce9d889d7852" />

### Microservicio msaccount
1. Ejecución de pruebas unitarias:
   ```
   mvn -Dtest=AccountServiceTest test
   mvn -Dtest=ConversionsTest test
   ```
<img width="1223" alt="pruebas unitarias msaccount" src="https://github.com/user-attachments/assets/dcbcc49d-4753-44ee-8686-2c72f7a88902" />

2. Ejecución de pruebas de integración con Mvc de Spring:
  ```
   mvn -Dtest=AccountControllerIntegrationTest test
   ```
<img width="1208" alt="prueba integracion mvc msaccount" src="https://github.com/user-attachments/assets/5e642123-1fe9-4aaa-9993-690b7ce44955" />

3. Ejecución de pruebas de integración con Karate:
  ```
   mvn -Dtest=AccountTest test
   ```
<img width="1079" alt="pruebas de integracion karate dsl msaccount" src="https://github.com/user-attachments/assets/54b4dbaa-63fb-42c3-9db8-65461f0dd488" />

## Cobertura de Código
Se implementó añadió framework de jacoco en el pom de los dos proyectos para obtener la cobertura de código de las pruebas, para ello, una vez que se jecuten las pruebas, sea de manera general o individual, en la carpeta **target** de cada microservicio, ecistirá un directorio **jacoco-report**, dentro del cual se encuentra un archivo **index.html**, si lo abrimos veremos el porcentaje de cobertura:
<img width="853" alt="image" src="https://github.com/user-attachments/assets/909831e7-ce80-4e2b-a4bf-538eb1975d46" />


## Instrucciones para consumir los endpoints
1. Dentro de la carpeta **postmancollection** se encuentra el archivo en formato json, solo se debe importar ese archivo en la herrmaienta postman.
2. Una vez importado el archivo, tendremos algo similar a la siguiente imagen:
<img width="202" alt="image" src="https://github.com/user-attachments/assets/e621eb63-7027-4dd1-ac3e-d672ba16be92" />
   
3. Ejecutar el Endpoint **CREATE CLIENTE**:
<img width="486" alt="image" src="https://github.com/user-attachments/assets/0daf8067-4876-4bad-b556-8d00e4331110" />
   
4. Ejecutar el Endpoint **ACTUALIZAR CLIENTE**:
<img width="514" alt="image" src="https://github.com/user-attachments/assets/7b18df7d-8aa1-4c0e-9144-109994fe1a1e" />

5. Ejecutar el Endpoint **CREATE ACCOUNT**:
<img width="541" alt="image" src="https://github.com/user-attachments/assets/17190f6f-a2ff-4ae7-86c5-c8f74f6b3c86" />

6. Ejecutar el Endpoint **CREAR MOVIMIENTO**:
<img width="538" alt="image" src="https://github.com/user-attachments/assets/9d96c81b-998f-47fd-a7a1-b4ba54710dc2" />

7. Ejecutar el Endpoint **MOVIMIENTOS POR CUENTA**
<img width="561" alt="image" src="https://github.com/user-attachments/assets/539a6ba7-c726-4132-9bfe-8e568ffa006c" />

8. Ejecutar el Endpoint **REPORTE ESTADO CUENTA**:
<img width="655" alt="image" src="https://github.com/user-attachments/assets/9b331775-21d5-4f89-aea4-5f4307ca7619" />

9. Ejecutar el Endpoint **OBTENER TODOS LOS CLIENTES**:
<img width="439" alt="image" src="https://github.com/user-attachments/assets/57132727-ad98-4644-aade-2e2a8e8cf10b" />

10. Ejecutar el Endpoint **BORRAR CLIENTE**:
<img width="482" alt="image" src="https://github.com/user-attachments/assets/1f891b24-36e7-49e8-921a-06d5aa15e297" />
  
11. Ejecutar el Endpoint **OBTENER TODAS LAS CUENTAS**:
<img width="482" alt="image" src="https://github.com/user-attachments/assets/d94c424f-bae3-4b02-9b8a-496046264687" />

12. Ejecutar el Endpoint **ACTUALIZAR CUENTA**:
<img width="490" alt="image" src="https://github.com/user-attachments/assets/e0faedbe-80ca-415e-b4f1-8becf2058eb8" />

## Ejecución de Pruebas de Rendimiento
Para ejecutar las pruebas de rendimiento es neceario instalar Grafana K6.
Ingresar en el directorio performancetest y ejecutar el siguiente comando:
    ```
       k6 run --env TEST_TYPE=loadtest performancetest.js
    ```
Para la variable de entorno TEST_TYPE podemos colocar cualquiera de las siguientes dependiendo del tipo de prueba que deseemos hacer:
    ```
       loadtest
       stresstest
       peaktest
       soakingtest
       capacitytest
       stabilitytest
    ```
<img width="1234" alt="image" src="https://github.com/user-attachments/assets/c5c8bf7e-e7e0-418c-838f-5405a019171a" />



   






