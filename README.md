
<body>
  <h1 align="center">
  <br>
  <a><img src="https://github.com/khandelwal-arpit/springboot-starterkit/blob/master/docs/images/spring-framework.png" alt="spring boot"></a>
  <br>
  Spring Boot JWT Authentication & Authorization Kit
  <br>
</h1>
  <p align="center">
    <a alt="Java">
        <img src="https://img.shields.io/badge/Java-v23-orange.svg" />
    </a>
    <a alt="Spring Boot">
        <img src="https://img.shields.io/badge/Spring%20Boot-v3.4.2-brightgreen.svg" />
    </a>
    <a alt="Bootstrap">
        <img src="https://img.shields.io/badge/JWT-v0.12.5-yellowgreen.svg">
    </a>
    <a alt="Material">
        <img src="https://img.shields.io/badge/Spring%20Security-v6-orange.svg">  
    </a>      
</p>
  <p>This repository provides a complete <strong>JWT (JSON Web Token) authentication and authorization system</strong> built with <strong>Spring Boot</strong> and <strong>Spring Security</strong>. It serves as a starter kit for projects requiring authentication and authorization using JWT.</p>

  <h2>Features</h2>
  <ul>
    <li><strong>User Authentication</strong></li>
    <li><strong>JWT-Based Authentication</strong></li>
    <li><strong>Refresh Token Mechanism</strong></li>
    <li><strong>Role-Based Access Control (RBAC)</strong></li>
    <li><strong>Global Exception Handling</strong></li>
    <li><strong>Spring Security Configuration</strong></li>
    <li><strong>Secure API Endpoints</strong></li>
    <li><strong>CORS Configuration</strong></li>
    <li><strong>Log4j2 for Logging</strong></li>
    <li><strong>Docker Support</strong></li>
    <li><strong>Email Sending Support</strong></li>
  </ul>

  <h2>Technologies Used</h2>
  <ul>
    <li><strong>Spring Boot 3</strong></li>
    <li><strong>Spring Security</strong></li>
    <li><strong>Spring Data JPA</strong></li>
    <li><strong>PostgreSQL</strong></li>
    <li><strong>JWT (JSON Web Tokens)</strong></li>
    <li><strong>Lombok</strong></li>
    <li><strong>Log4j2</strong></li>
    <li><strong>Docker & Docker Compose</strong></li>
    <li><strong>Spring Boot Mail</strong></li>
  </ul>

  <h2>Security</h2>
  <ul>
    <li>Implements Spring Security with <code>SecurityFilterChain</code> for authentication.</li>
    <li>JWT Authentication via <code>JwtAuthenticationFilter</code>.</li>
    <li>Role-Based Access Control (RBAC) for endpoint restrictions.</li>
    <li>Secure password storage using BCrypt hashing.</li>
    <li>Refresh token mechanism for extended sessions.</li>
  </ul>

  <h2>API Endpoints</h2>
  <h3>Authentication</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Token Required</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td>/auth/login</td>
      <td>User Login</td>
      <td>No</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>/auth/register</td>
      <td>User Registration</td>
      <td>No</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>/auth/refresh-token</td>
      <td>Refresh Access Token</td>
      <td>Refresh Token</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>/auth/logout</td>
      <td>User Logout</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<h3>User Operations</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Role</th>
      <th>Token Required</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>GET</td>
      <td>/users/health</td>
      <td>User</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>

<h3>Admin Operations</h3>
<table>
  <thead>
    <tr>
      <th>Method</th>
      <th>Endpoint</th>
      <th>Role</th>
      <th>Token Required</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>GET</td>
      <td>/admin/dashboard</td>
      <td>Admin Only</td>
      <td>Yes</td>
    </tr>
  </tbody>
</table>



  <h1>Installation</h1>
  <ol>
    <li>
      <p><strong>Clone the repository</strong></p>
      <pre><code>git clone https://github.com/mustafkrca/Spring-Boot-JWT-Starter-Kit.git
cd Spring-Boot-JWT-Starter-Kit</code></pre>
    </li>
    <li>
      <p><strong>Configure Application Properties</strong></p>
<p>Modify <code>application.properties</code> to set up your database, JWT, and email configurations. <strong>Note:</strong> If you change your database connection settings, be sure to update the corresponding values in <code>docker-compose.yml</code> as well.</p>
      <pre><code># JWT Configuration
app.jwtSecret=your-secret-key
app.jwtExpirationInMs=3600000  # 1 Hour
app.jwtIssuer=your-app

app.refreshTokenDurationMs=86400000  # 24 Hours

spring.datasource.url=jdbc:postgresql://localhost:5432/straterKitDB
spring.datasource.username=us
spring.datasource.password=pass
spring.jpa.hibernate.ddl-auto=update

spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your-email
spring.mail.password=your-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true</code></pre>
    </li>
  </ol>

  


  <h2>Build and Run Locally</h2>
  <p>Run the following commands to build and run the application:</p>
  <pre><code>docker-compose up -d db
mvn clean install
mvn spring-boot:run</code></pre>

  <h2>Run with Docker</h2>
  <p>To start the application using Docker Compose:</p>
  <pre><code>docker-compose up -d db
mvn clean install 
docker-compose up --build</code></pre>
</html>
