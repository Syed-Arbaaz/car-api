# 🚗 Car Dealership Management System

A full-stack **Car Dealership Management System** built with **Spring Boot, Spring Security, JWT Authentication, MySQL, Docker, and AWS**. The application supports secure 
user authentication, role-based authorization, online car booking, invoice generation, email notifications, and an admin dashboard for managing cars and bookings.

The application is fully deployed using **AWS EC2**, **Docker**, **Nginx**, and **Amazon S3**.

---

## 🌐 Live Demo

**Frontend**

http://car-rental-frontend-2026.s3-website-us-east-1.amazonaws.com

> Backend is hosted on AWS EC2 behind Nginx.

---

# ✨ Features

## 👤 User Features

- User Registration
- Email Verification
- Secure Login using JWT Authentication
- Browse Available Cars
- Search Cars by Brand
- View Car Details
- Book a Car
- View Booking History
- Download Booking Invoice (PDF)
- Email Notifications

---

## 🛠️ Admin Features

- Secure Admin Login
- Add New Cars
- Edit Existing Cars
- Delete Cars
- View All Bookings
- Update Booking Status
- Manage Vehicle Inventory

---

## 🔒 Security Features

- Spring Security
- JWT Authentication
- Role-Based Authorization
- BCrypt Password Encryption
- Stateless Authentication
- Protected REST APIs
- CORS Configuration

---

# 🏗️ Architecture

```
                +----------------------+
                |     Web Browser      |
                +----------+-----------+
                           |
                           |
                Amazon S3 Static Website
                           |
                           |
                        Nginx
                           |
                           |
              Spring Boot REST API
                   (Docker Container)
                           |
                           |
                        MySQL
```

---

# 🛠️ Tech Stack

## Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT Authentication
- Java Mail
- Maven

## Frontend

- HTML5
- CSS3
- JavaScript (Vanilla JS)

## Database

- MySQL

## Deployment

- Docker
- Docker Volumes
- Docker Hub
- AWS EC2
- Amazon S3 Static Website Hosting
- Nginx Reverse Proxy
- GitHub Actions (CI/CD)

---

# 📂 Project Structure

```
car-rental-system
│
├── backend
│   ├── controller
│   ├── service
│   ├── repository
│   ├── dto
│   ├── model
│   ├── config
│   ├── filter
│   └── exception
│
├── frontend
│   ├── css
│   ├── js
│   ├── pages
│   └── images
│
└── README.md
```

---

# 🔑 Authentication Flow

```
Register
      │
      ▼
Email Verification
      │
      ▼
Login
      │
      ▼
JWT Access Token
      │
      ▼
Protected APIs
```

---

# 📌 REST API Endpoints

## Authentication

| Method | Endpoint | Description |
|----------|----------------------|----------------|
| POST | /api/auth/register | Register User |
| POST | /api/auth/login | Login |
| POST | /api/auth/refresh | Refresh JWT Token |
| GET | /api/auth/verify | Verify Email |

---

## Cars

| Method | Endpoint |
|----------|-------------------------|
| GET | /api/cars |
| GET | /api/cars/{id} |
| GET | /api/cars/brand/{brand} |
| POST | /api/cars |
| PUT | /api/cars/{id} |
| DELETE | /api/cars/{id} |

---

## Bookings

| Method | Endpoint |
|----------|------------------------------|
| POST | /api/bookings |
| GET | /api/bookings |
| GET | /api/bookings/my-bookings |
| PUT | /api/bookings/{id}/status |
| GET | /api/bookings/{id}/invoice |

---

# 🚀 Deployment

The project is deployed using AWS.

- Frontend hosted on Amazon S3
- Backend deployed on AWS EC2
- Dockerized Spring Boot application
- Nginx Reverse Proxy
- Docker Volume for uploaded images
- GitHub Actions CI/CD Pipeline
- Environment Variables managed using GitHub Secrets

---


# 🔄 CI/CD

This project uses **GitHub Actions** to automate deployment.

Workflow:

```
Developer
    │
git push
    │
    ▼
GitHub Actions
    │
    ▼
Build Maven Project
    │
    ▼
Build Docker Image
    │
    ▼
Push Image to Docker Hub
    │
    ▼
Deploy Automatically to AWS EC2
```

---

# 📈 Future Improvements

- AWS CloudFront CDN
- Custom Domain
- HTTPS with SSL
- Store Images in Amazon S3
- Redis Caching
- Unit & Integration Testing
- Monitoring with CloudWatch
- Kubernetes Deployment

---

# 👨‍💻 Author

**Arbaaz Mohiuddin Syed**

GitHub: https://github.com/Syed-Arbaaz

LinkedIn: https://linkedin.com/in/arbaazmohiuddinsyed

---

# 📄 License

This project is developed for learning purposes and portfolio demonstration.
