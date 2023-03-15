# smart-repay-homeloan
A project for repaying loan smartly and track the progress

# Technologies
- Spring Boot Reactive (WebFlux)
- Postgres DB for backend
- Thymeleaf for UI

# Workflow
Sample workflow for Azure WebApp deployment

# Features
- Calculate loan EMI
- Calculate EMI bnased on factors like yearly pre-payment and/or yearly EMI amount increase
- See the previous calculations

# Samples
- Landing
![image](https://user-images.githubusercontent.com/42411137/225261498-4cef481b-8d6a-4512-81a0-33652ef09ea9.png)

- Calculate EMI
![image](https://user-images.githubusercontent.com/42411137/225261703-96190027-5124-40cf-909d-fe4ef6ba44fa.png)

- Reactive Flux of data retrieval
![image](https://user-images.githubusercontent.com/42411137/225261887-03264557-e6b1-4481-b54e-93f1c287a434.png)

# How to run
- clone or download the repository
- Download and install the postgres server from here https://www.postgresql.org/download/
- Repace the user/password with your Postgres credentials
- `spring-boot:run`
