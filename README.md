#Food App

Link Diagram: https://app.diagrams.net/#G1TOIKzHGu_4nWQNlsasaVHy8Tqj_ithJN#%7B%22pageId%22%3A%22j9AC-HuFatAEg-fNP-JM%22%7D

~FE: React + TS + MUI + Vite

Run app: npm run dev

~BE: Java Spring Boot

Run microservice: mvn spring-boot:run

Technology:

*Framework: Spring boot, Spring security, Spring Cloud

*Database: MysQL, Redis, DbMongo

*Dependency: Hibernate/JPA, Lombok, MapStruct, Oauth2, Nimbus, OpenFeign, Gateway, WebSocket

*API integration: Brevo, OpenRouterAI

Project Description:

Food App is a food ordering software with 4 user roles:

*Admin: General management of the system.

*Shipper: Changes the status of orders.

*Customer: Manages cart, creates orders, and handles payments.

*Restaurant: Manages the food menu and updates the order status.

Microservices: Each small service is designed to match the permissions and actions associated with each role.

*ApiGateway: The API Gateway validates the client's token and acts as the intermediary between the client and the microservices.

*AccountService: Manages users and handles actions related to user creation and authentication.

*OrderService: Handles the main functions of each role, such as managing orders, order statuses, and related actions.

*NotificationService: Manages sending email notifications.

*ChatService: Manages chat functionality between two users, including a ChatBotAI feature for automated responses.
