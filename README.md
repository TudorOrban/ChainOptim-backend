## ChainOptim

ChainOptim is a comprehensive Enterprise Resource Planning (ERP) system designed to streamline and optimize the end-to-end processes of modern businesses. Designed to meet the diverse needs of companies across various sizes and sectors, ChainOptim delivers robust management tools and detailed insights into every aspect of the supply chain, enhancing operational efficiency and decision-making capabilities.

This repository contains the source code for the [Spring Boot backend](https://github.com/TudorOrban/ChainOptim-backend). For the Angular web frontend, see [this](https://github.com/TudorOrban/ChainOptim-frontend) repository, and for the JavaFX desktop frontend, visit [this](https://github.com/SorinPopteanu/ChainOptim-DesktopApp) link.
### Features

There are six main domains in ChainOptim: **Organization**, **Goods**, **Supply**, **Production**, **Storage** and **Demand**.

![Chain Optim screenshots](/screenshots/chainoptim-4screenshots.png)

#### **Organization**: 
This domain focuses on managing the company's profile, subscription plan, members and their security roles.

#### **Goods**:
**Components** and **Products** are goods manufactured and/or sold by the company. The user can configure a blueprint for the manufacturing process of a product, define pricing models and set up transportation routes.

#### **Supply**:
**Suppliers** are organizations that provide components to the company. This domain is responsible with tracking their orders, shipments, and analyzing their performance over time.

#### **Production**:
This domain is concerned with **Factories** and their underlying operations. Several solutions are provided for optimizing the production process, such as establishing resource allocation plans, seeking missing resources or storing and analyzing the production history.

#### **Storage**:
**Warehouses** are company facilities used for storing goods. This domain provides tools for managing their inventories, including levels of goods and storage space.

#### **Demand**:
**Clients** are organizations that are provided products by the company. Similar to suppliers, ChainOptim allows tracking of their orders, shipments, and evaluating them.

### How to use
ChainOptim is not yet deployed. To use it, you will have to set up locally the database, backend server and chosen frontend.
1. Ensure you have installed: JDK21, Maven and Docker.
2. Fetch the Spring Boot backend. In the root, create:
- an application-docker.properties from the [application-docker-dev.properties.example](https://github.com/TudorOrban/ChainOptim-backend/blob/main/src/main/resources/application-docker-dev.properties.example), replacing the database user and password appropriately, and the JWT secret as needed. 
- a docker-compose.dev.yml from the [docker-compose.dev.yml.example](https://github.com/TudorOrban/ChainOptim-backend/blob/main/docker-compose.dev.yml.example), replacing the database user and password.
3. Build the Spring Boot image and spin up the necessary Docker containers (MySQL, Spring Boot, Redis, Kafka). You can find a script that does this for your operating system in the [scripts](https://github.com/TudorOrban/ChainOptim-backend/blob/main/scripts) folder (Restart.ps1 or Restart.sh). *Run it twice*, as the first time around the database will be empty and Spring Boot won't start.
4. Fetch the [Angular web frontend](https://github.com/TudorOrban/ChainOptim-frontend), run `ng serve` and log in with:
- Username: ExampleUser
- Password: example-password

Now you can interact with the application. Alternatively, fetch the [JavaFX desktop frontend](https://github.com/SorinPopteanu/ChainOptim-DesktopApp), run it in your IDE of choice and log in.

### Status
In late stages of development.

### Contributing
All contributions are warmly welcomed. Head over to [CONTRIBUTING.md](https://github.com/TudorOrban/ChainOptim-backend/blob/main/CONTRIBUTING.md) for details.
