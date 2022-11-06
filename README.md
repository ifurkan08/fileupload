
# File Upload

File uplaod with JWT Authentication and Swagger




## Before Run

DB server url in ApplicationProperties must be change. 
If DB server will run on Local. PostgreSQL must be installed


## Run Locally

Clone the project

```bash
  git clone https://github.com/ifurkan08/fileupload.git
```

Go to the project directory

```bash
  cd fileupload
```

Run and start the server spring-boot application

```bash
   mvn spring-boot:run -P dev
```

## Test

- Test  to api on local swagger-ui(http://localhost:8080/swagger-ui/) 
- Also Postman Collection is in this repo.
- You should login and get Bearer token when testing Authentication needed methods. 
   





