# Biv-Quack

<div style="display:flex; flex-direction: row; justify-content: center; align-items: center; width: 100vw">
  <a target="_blank" href="https://github.com/VincentDub2/Biv-Quack">
  <img alt="github link" src="https://img.shields.io/badge/Biv'Quack-global-orange?logo=github&style=for-the-badge">
</a>
<a target="_blank" href="https://github.com/VincentDub2/ReactNativeIWA">
  <img alt="github link" src="https://img.shields.io/badge/Biv'Quack-frontend-blue?logo=github&style=for-the-badge">
</a>
<a target="_blank" href="https://github.com/VincentDub2/iwa-spring-kafka">
  <img alt="github link" src="https://img.shields.io/badge/Biv'Quack-microservices-blue?logo=github&style=for-the-badge">
</a>
</div>

---

# **Documentation for Dockerized Microservices**

## **Introduction**
This setup defines a microservice architecture using `docker-compose.yml`. The services include Kafka, Zookeeper, PostgreSQL databases, and Spring Boot microservices for user management, reservations, emplacements, messaging, evaluations, notifications, and an API Gateway. It also includes a Eureka Discovery Service for service registry.

---

## **How to Run Locally**

### **Prerequisites**
1. **Install Docker**: [Download Docker](https://docs.docker.com/get-docker/).
2. **Install Docker Compose**: [Install Docker Compose](https://docs.docker.com/compose/install/).

### **Steps to Run**
1. **Clone the Repository**:
   ```bash
   git clone <repository_url>
   cd <repository_directory>
2. **Start Services: Run the start-docker.sh script to build, pull, and start all services:**:
   ```bash
   ./start-docker.sh
   ```
3. **View Logs**: To view logs of all services, run the following command:
   ```bash
   docker-compose logs -f
   ```
4. **Stop Services**: To stop all services, run the following command:
   ```bash
    docker-compose down
    ```
5. **Verify Services**: To verify that all services are running, run the following command:
   ```bash
   docker-compose ps
   ```
6. **Access Api Gateway**: To access the API Gateway, open the following URL in your browser:
   ```bash
   http://localhost:8090
   ```
7. **Access Eureka Dashboard**: To access the Eureka Dashboard, open the following URL in your browser:
   ```bash
   http://localhost:8761
   ```

## **What the Code Does**

### **Services Overview**
1. **Kafka and Zookeeper**:
   - **Kafka**: Message broker for communication between microservices.
   - **Zookeeper**: Coordinates Kafka brokers.

2. **PostgreSQL Databases**:
   - Each microservice has its dedicated database, initialized using SQL scripts.

3. **Spring Boot Microservices**:
   - `service-users`: Manages user accounts.
   - `service-reservations`: Handles reservations.
   - `service-emplacements`: Manages location data.
   - `service-messaging`: Sends and retrieves messages.
   - `service-evaluations`: Handles user evaluations.
   - `service-notifications`: Sends notifications to users.
   - `api-gateway`: Acts as a single entry point for all services.
   - `discovery-service`: Registers and tracks microservices using Eureka.

4. **Docker Networks**:
   - **api-network**: For API Gateway and microservices.
   - **kafka-network**: Connects Kafka and Zookeeper.
   - **db-network**: Connects databases to their respective microservices.

### **Healthchecks**
Each service includes health checks using `actuator/health` for monitoring service status.

## **Vulnerabilities**

### **Security Risks**
1. **Plaintext Credentials**:
    - Database credentials (`SPRING_DATASOURCE_USERNAME` and `SPRING_DATASOURCE_PASSWORD`) are exposed in `docker-compose.yml`.
    - Solution: Use Docker secrets or environment files.

2. **PLAINTEXT Kafka Listener**:
    - Kafka allows plaintext connections (`ALLOW_PLAINTEXT_LISTENER: "yes"`).
    - Solution: Use TLS for encrypted communication.

3. **Anonymous Zookeeper Login**:
    - `ALLOW_ANONYMOUS_LOGIN: 'yes'` allows anyone to access Zookeeper.
    - Solution: Restrict access using authentication and ACLs.

4. **Exposed Ports**:
    - Ports are exposed without access control.
    - Solution: Use a reverse proxy (e.g., NGINX) and firewall rules.

### **Configuration Risks**
1. **Missing Rate Limits**:
    - APIs lack rate limiting, exposing them to potential DoS attacks.
    - Solution: Implement rate limiting in the API Gateway.

2. **Database Initialization**:
    - SQL files are directly mounted. Any modification will affect production data.
    - Solution: Use a controlled migration tool like Liquibase.

3. **Service Restart Policies**:
    - `restart: unless-stopped` may cause unwanted restarts in production.
    - Solution: Fine-tune restart policies for stability.

## **Directory Structure**

### Key Components

```
    ├── docker-compose.yml          # Defines microservices, databases, and Kafka/Zookeeper.
    ├── start-docker.sh             # Automates the deployment process.
    ├── biv_quack_ms_*              # Each microservice directory.
    └── update-ms.sh                # Script for updating microservices.
```

## **CI/CD Workflow**

### **GitHub Actions Workflow**
The workflow automates deployments to a Google Cloud VM for both staging and production environments based on specific tag prefixes.

#### **Workflow Configuration**
1. **Trigger**: The workflow is triggered on tag creation with prefixes `stage-*` and `prod-*`.


## **Further Improvements**
1. **Monitoring and Logging**:
    - Integrate tools like Prometheus and Grafana for monitoring.
    - Centralize logs using ELK stack.

2. **Secure Configuration**:
    - Store sensitive data in `.env` or secret management tools (e.g., Vault).

3. **Deployment**:
    - Use Kubernetes for better orchestration and scalability.

4. **Testing**:
    - Include unit and integration tests for all microservices.
    - 
5. **Enhanced CI/CD**:
    - Add a step to run tests (unit, integration, or smoke tests) before deploying to staging or production.
    - Automate rollback for failed deployments.

6. **Container Security**:
    - Scan Docker images for vulnerabilities using tools like Clair or Trivy.
    - keycloak for authentication and authorization.

7. **Backup and Recovery**:
    - Implement regular backups for databases and Kafka topics.
    - Define a disaster recovery plan.
---
## Contributors
@ [Vincent Dubuc](https://github.com/VincentDub2) & [Matéo Iori](https://github.com/mati0ri) & [Kylian Thezenas](https://github.com/kylian-thezenas) & [Alexandre Lagorce](https://github.com/alexlagorce)- IG5 Polytech Montpellier - 2024
