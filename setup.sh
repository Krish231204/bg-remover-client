#!/bin/bash

# BG Remover Development Setup Script

echo "🚀 BG Remover - Monorepo Setup"
echo "================================"
echo ""

# Check prerequisites
echo "📋 Checking prerequisites..."

# Check Node.js
if ! command -v node &> /dev/null; then
    echo "❌ Node.js is not installed. Please install Node.js 16+"
    exit 1
fi
echo "✅ Node.js $(node -v)"

# Check Java
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 21+"
    exit 1
fi
echo "✅ Java $(java -version 2>&1 | grep version | awk '{print $3}')"

# Check MySQL
if ! command -v mysql &> /dev/null; then
    echo "⚠️  MySQL client not found (optional if using Docker)"
fi

echo ""
echo "📦 Installing Frontend Dependencies..."
cd frontend
npm install
if [ $? -ne 0 ]; then
    echo "❌ Frontend installation failed"
    exit 1
fi
echo "✅ Frontend dependencies installed"

cd ..

echo ""
echo "✅ Setup complete!"
echo ""
echo "📝 Next steps:"
echo "   1. Configure backend database:"
echo "      Edit: backend/src/main/resources/application.properties"
echo "      - Set database credentials"
echo "      - Update Clerk issuer and JWKS URL"
echo ""
echo "   2. Configure frontend environment:"
echo "      Edit: frontend/.env"
echo "      - Add VITE_CLERK_PUBLISHABLE_KEY"
echo ""
echo "   3. Start development servers:"
echo "      Terminal 1: cd backend && ./mvnw spring-boot:run"
echo "      Terminal 2: cd frontend && npm run dev"
echo ""
echo "   Frontend: http://localhost:5173"
echo "   Backend:  http://localhost:8080"
echo ""
