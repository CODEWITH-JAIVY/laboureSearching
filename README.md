# Labourse Searching Platform 🚧👷

A production-grade, full-stack labour hiring platform inspired by gig-economy systems like Blinkit / Swiggy.

This platform enables customers to discover nearby labourers in real-time, post jobs, track job acceptance, and communicate securely across Web and Mobile applications.

---

## 🚀 Tech Stack

### Backend (Spring Boot)
- Java 17
- Spring Boot
- Spring Security (JWT + OAuth2)
- Redis (Geo-spatial queries)
- Kafka (Event-driven architecture)
- MySQL
- AWS S3 (Image storage)
- WebSockets (Real-time notifications)

### Frontend – Web
- React (Vite)
- Tailwind CSS
- Axios
- Context API
- i18n (Multi-language support)

### Frontend – Mobile
- React Native (Expo)
- TypeScript
- Location Services
- Real-time notifications

---

## 📁 Project Structure

labourseSearching/
├── LabourseSearhing/        (Spring Boot Backend)
├── frontend-web/            (React Web Application)
├── frontend-mobile/         (React Native Mobile Application)
├── .gitignore               (Security & build exclusions)
└── README.md                (Project documentation)

---

## 🌍 Core Features

- Nearby labour discovery using Redis GEO
- Live location tracking
- Job posting & acceptance workflow
- Real-time notifications using Kafka and WebSocket
- Image upload using AWS S3
- Multi-language support
- Secure authentication and authorization
- Role-based access (Customer / Labour / Admin)

---

## 🔐 Security Practices

- No secrets committed to GitHub
- Environment-based configuration
- JWT-based authentication
- OAuth2 social login
- Secure CORS configuration
- Role-based access control
- Production-grade .gitignore

---

## 🛠️ Setup Instructions

### Backend (Spring Boot)

cd LabourseSearhing  
cp application.yml.example application.yml

Environment Variables:
DB_URL=  
DB_USERNAME=  
DB_PASSWORD=  
JWT_SECRET=  
AWS_ACCESS_KEY=  
AWS_SECRET_KEY=  
AWS_REGION=  
AWS_S3_BUCKET=

Run backend:
./mvnw spring-boot:run

---

### Frontend Web (React)

cd frontend-web  
npm install  
npm run dev

---

### Frontend Mobile (React Native)

cd frontend-mobile  
npm install  
npx expo start

---

## 🧠 System Design Overview

- Redis GEO is used for fast nearby labour search
- Kafka handles asynchronous job acceptance and notifications
- WebSockets provide real-time updates
- JWT and OAuth2 secure authentication
- AWS S3 stores user-uploaded images
- Modular monolith architecture, microservices-ready

---

## 👨‍💻 Author

Sanjeet kumar  
Backend-focused Full-Stack Developer

Skills:  
Java · Spring Boot · Redis · Kafka · React · System Design

---

## 📄 License

This project is intended for educational , demonstration purposes and new startup project.
