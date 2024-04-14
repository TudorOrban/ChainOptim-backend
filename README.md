## ChainOptim

**ChainOptim** is a Supply Chain Manager and Optimizer, consisting of a [Spring Boot backend](https://github.com/TudorOrban/ChainOptim-backend), a [web frontend](https://github.com/TudorOrban/ChainOptim-frontend) in Angular and a [Desktop frontend](https://github.com/SorinPopteanu/ChainOptim-DesktopApp) in JavaFX.
It is designed as a general service that can accommodate the needs of companies from a variety of sectors, providing
detailed insights into their supply chain, from suppliers to production to clients.

### Features

#### **Organizations**: 
ChainOptim employs a multi-tenant architecture that minimizes costs for the clients (while maintaining solid security) and facilitates interorganizational communication.

#### **Products** and **Components**:
Products are the goods manufactured by a company, while components are general goods needed in the manufacturing process.
Each product can have a *Production Pipeline* that configures how the components are assembled into products.

#### **Factories** and **Warehouses**:
An organization's sites for production and storage of goods. Configuring a factory's stages of production
allows the software to provide valuable information into its operations, including allocating resources, seeking solutions to resource deficits and evaluating performance over time. 
![Factory Production Graph](/screenshots/FactoryProductionGraph.png)

#### **Suppliers** and **Clients**:
ChainOptim allows registration of suppliers and clients, tracking orders and shipments and responding in real time to unpredictable disruptions that are more and more common in the present supply chain.

### How to use
ChainOptim is not yet deployed. To use it, you will have to set up locally the database, backend server and chosen frontend.
1. Ensure you have installed: JDK21, Maven and Docker.
2. Fetch the Spring Boot backend. In the root, create:
- an application-docker.properties from the [application-docker.properties.example](https://github.com/TudorOrban/ChainOptim-backend/blob/main/src/main/resources/application.properties.example), replacing the database user and password appropriately, and the JWT secret as needed. 
- a docker-compose.yml from the [docker-compose.yml.example](https://github.com/TudorOrban/ChainOptim-backend/blob/main/docker-compose.yml.example), replacing the database user and password.
3. Build the Spring Boot image and spin up the necessary Docker containers (MySQL, Spring Boot, Redis, Kafka). You can find a script that does this for your operating system in the [scripts](https://github.com/TudorOrban/ChainOptim-backend/blob/main/scripts) folder (Restart.ps1 or Restart.sh). *Run it twice*, as the first time around the database will be empty and Spring Boot won't start.
4. Fetch the [JavaFX desktop frontend](https://github.com/SorinPopteanu/ChainOptim-DesktopApp), run it in your IDE of choice and log in with:
- Username: ExampleUser
- Password: example-password

Now you can interact with the application. Alternatively, fetch the [Angular web frontend](https://github.com/TudorOrban/ChainOptim-frontend), run `ng serve` and log in.

### Status
In mid stages of development.

### Contributing
All contributions are warmly welcomed. Head over to [CONTRIBUTING.md](https://github.com/TudorOrban/ChainOptim-backend/blob/main/CONTRIBUTING.md) for details.
