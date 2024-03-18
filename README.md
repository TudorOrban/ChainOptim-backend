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
1. Download the [database schema](https://github.com/SorinPopteanu/ChainOptim-DesktopApp/blob/main/database/schema/schema.sql) and import it into MySQL. Create a user and an organization for later login.
2. Ensure you have JDK 21 installed and fetch the [Spring Boot backend](https://github.com/TudorOrban/ChainOptim-backend) repository.
3. Create an application.properties from the [application.properties.example](https://github.com/TudorOrban/ChainOptim-backend/blob/main/src/main/resources/application.properties.example), replacing the database user and password appropriately, and the JWT secret as needed. Then run the project from your IDE of choice.
4. Fetch this repository, run it and log in.

### Status
In mid stages of development.

### Contributing
All contributions are warmly welcomed. Head over to [CONTRIBUTING.md](https://github.com/TudorOrban/ChainOptim-backend/blob/main/CONTRIBUTING.md) for details.
