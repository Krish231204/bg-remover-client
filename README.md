# BG Remover - Monorepo

A full-stack application for background removal with separate frontend and backend services.

## Project Structure

```
.
├── frontend/          # React + Vite frontend application
│   ├── src/          # React source code
│   ├── public/       # Static assets
│   ├── package.json  # Frontend dependencies
│   └── vite.config.js
│
└── backend/          # Spring Boot Java backend
    ├── src/         # Java source code
    ├── pom.xml      # Maven configuration
    └── mvnw         # Maven wrapper
```

## Getting Started

### Prerequisites
- Node.js 16+ (for frontend)
- Java 21+ (for backend)
- Maven (or use the mvnw wrapper)

### Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

The frontend will be available at `http://localhost:5173`

### Backend Setup

```bash
cd backend
./mvnw spring-boot:run
```

The backend will be available at `http://localhost:8080`

## Environment Variables

### Frontend (.env)
```
VITE_CLERK_PUBLISHABLE_KEY=your_clerk_key
```

### Backend (application.properties)
Configure in `backend/src/main/resources/application.properties`

## Building

### Frontend
```bash
cd frontend
npm run build
```

### Backend
```bash
cd backend
./mvnw clean package
```

## API Documentation

The backend provides REST APIs for background removal operations. Endpoints are secured with JWT authentication via Clerk.

## Tech Stack

**Frontend:**
- React 19
- Vite
- Tailwind CSS
- Framer Motion
- Clerk Authentication

**Backend:**
- Spring Boot 4.0.1
- Spring Security
- Spring Data JPA
- MySQL
- JWT (JJWT)
- Lombok

## Development Workflow

1. Start the backend server
2. Start the frontend development server
3. Frontend will make API calls to `http://localhost:8080`

## Contributing

Please ensure code follows project conventions and includes appropriate tests.
