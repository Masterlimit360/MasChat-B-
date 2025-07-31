#!/bin/bash

# MasChat Backend Render Deployment Script
# This script helps set up the deployment environment for Render

echo "🚀 MasChat Backend Render Deployment Setup"
echo "=========================================="

# Check if we're in the right directory
if [ ! -f "pom.xml" ]; then
    echo "❌ Error: pom.xml not found. Please run this script from the MasChat-B- directory."
    exit 1
fi

echo "✅ Found pom.xml - we're in the right directory"

# Check if Dockerfile.render exists
if [ ! -f "Dockerfile.render" ]; then
    echo "❌ Error: Dockerfile.render not found. Please ensure all deployment files are present."
    exit 1
fi

echo "✅ Found Dockerfile.render"

# Check if application-render.properties exists
if [ ! -f "src/main/resources/application-render.properties" ]; then
    echo "❌ Error: application-render.properties not found."
    exit 1
fi

echo "✅ Found application-render.properties"

# Check if render.yaml exists
if [ ! -f "render.yaml" ]; then
    echo "❌ Error: render.yaml not found."
    exit 1
fi

echo "✅ Found render.yaml"

echo ""
echo "📋 Deployment Checklist:"
echo "========================"
echo "1. ✅ All configuration files are present"
echo "2. ⏳ Create PostgreSQL database on Render"
echo "3. ⏳ Set up environment variables in Render"
echo "4. ⏳ Deploy the application"
echo ""
echo "🔧 Next Steps:"
echo "=============="
echo "1. Go to your Render dashboard"
echo "2. Create a new PostgreSQL database"
echo "3. Create a new Web Service"
echo "4. Connect your GitHub repository"
echo "5. Set the root directory to: MasChat-B-"
echo "6. Configure environment variables:"
echo "   - SPRING_PROFILES_ACTIVE: render"
echo "   - DATABASE_URL: (from your PostgreSQL service)"
echo "   - DATABASE_USERNAME: (from your PostgreSQL service)"
echo "   - DATABASE_PASSWORD: (from your PostgreSQL service)"
echo "   - JWT_SECRET: (generate a secure random string)"
echo ""
echo "📖 For detailed instructions, see README.md"
echo ""
echo "🎉 Setup complete! Ready for deployment." 