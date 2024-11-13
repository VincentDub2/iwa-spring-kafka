# Discovery Service

<a target="_blank" href="https://github.com/VincentDub2/Biv-Quack">
  <img alt="github link" src="https://img.shields.io/badge/Biv'Quack-global-orange?logo=github&style=for-the-badge">
</a>
<a target="_blank" href="https://github.com/VincentDub2/iwa-spring-kafka/tree/main">
  <img alt="github link" src="https://img.shields.io/badge/Biv'Quack-microservices-red?logo=github&style=for-the-badge">
</a>

## Description
The Discovery Service is a central component in our microservices architecture, implemented as a **Spring Cloud Eureka Server**. Its primary role is to manage service registration and discovery within the **Biv'Quack** ecosystem.

### Key Responsibilities
- **Service Registration**: Allows microservices to register themselves, enabling dynamic service scaling and flexibility.
- **Service Discovery**: Enables microservices to locate and communicate with each other without hardcoded service addresses, supporting an agile and adaptable architecture.
- **Load Balancing and Failover**: With Eureka, services are dynamically balanced, and failover is automatically managed, ensuring high availability across the system.

This service is foundational for enabling loose coupling between microservices, reducing dependencies and configuration overhead by handling service locations dynamically. As a result, microservices can be deployed, scaled, and updated independently, allowing the **Biv'Quack** platform to evolve seamlessly.

---

@ Vincent Dubuc, Matéo Iori, Kylian Thezenas & Alexandre Lagorce – IG5 Polytech Montpellier – 2024
