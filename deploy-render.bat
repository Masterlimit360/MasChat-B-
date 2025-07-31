@echo off
echo 🚀 MasChat Backend Render Deployment Setup
echo ==========================================

REM Check if we're in the right directory
if not exist "pom.xml" (
    echo ❌ Error: pom.xml not found. Please run this script from the MasChat-B- directory.
    pause
    exit /b 1
)

echo ✅ Found pom.xml - we're in the right directory

REM Check if Dockerfile.render exists
if not exist "Dockerfile.render" (
    echo ❌ Error: Dockerfile.render not found. Please ensure all deployment files are present.
    pause
    exit /b 1
)

echo ✅ Found Dockerfile.render

REM Check if application-render.properties exists
if not exist "src\main\resources\application-render.properties" (
    echo ❌ Error: application-render.properties not found.
    pause
    exit /b 1
)

echo ✅ Found application-render.properties

REM Check if render.yaml exists
if not exist "render.yaml" (
    echo ❌ Error: render.yaml not found.
    pause
    exit /b 1
)

echo ✅ Found render.yaml

echo.
echo 📋 Deployment Checklist:
echo ========================
echo 1. ✅ All configuration files are present
echo 2. ⏳ Create PostgreSQL database on Render
echo 3. ⏳ Set up environment variables in Render
echo 4. ⏳ Deploy the application
echo.
echo 🔧 Next Steps:
echo ==============
echo 1. Go to your Render dashboard
echo 2. Create a new PostgreSQL database
echo 3. Create a new Web Service
echo 4. Connect your GitHub repository
echo 5. Set the root directory to: MasChat-B-
echo 6. Configure environment variables:
echo    - SPRING_PROFILES_ACTIVE: render
echo    - DATABASE_URL: (from your PostgreSQL service)
echo    - DATABASE_USERNAME: (from your PostgreSQL service)
echo    - DATABASE_PASSWORD: (from your PostgreSQL service)
echo    - JWT_SECRET: (generate a secure random string)
echo.
echo 📖 For detailed instructions, see README.md
echo.
echo 🎉 Setup complete! Ready for deployment.
pause 