@echo off
echo 🚀 MasChat Backend Render Environment Setup
echo ===========================================

echo.
echo 📋 Database Credentials Summary:
echo ================================
echo Hostname: dpg-d25f0jnfte5s73847gc0-a
echo Port: 5432
echo Database: maschatdb
echo Username: maschatdb_user
echo.
echo 🔐 Environment Variables for Render:
echo ====================================
echo.
echo Required Variables:
echo -------------------
echo SPRING_PROFILES_ACTIVE=render
echo DATABASE_URL=postgresql://maschatdb_user:YZ95BM0wLpPCDMGoBy8lHBWKRvQdJXzr@dpg-d25f0jnfte5s73847gc0-a/maschatdb
echo DATABASE_USERNAME=maschatdb_user
echo DATABASE_PASSWORD=YZ95BM0wLpPCDMGoBy8lHBWKRvQdJXzr
echo.
echo Optional Variables:
echo -------------------
echo JWT_EXPIRATION=86400000
echo PORT=8080
echo.
echo 🔧 Next Steps for Render Deployment:
echo ====================================
echo.
echo 1. Go to your Render dashboard
echo 2. Create a new Web Service
echo 3. Connect your GitHub repository
echo 4. Set Root Directory to: MasChat-B-
echo 5. In Environment Variables section, add:
echo.
echo    Key: SPRING_PROFILES_ACTIVE
echo    Value: render
echo.
echo    Key: DATABASE_URL
echo    Value: postgresql://maschatdb_user:YZ95BM0wLpPCDMGoBy8lHBWKRvQdJXzr@dpg-d25f0jnfte5s73847gc0-a/maschatdb
echo.
echo    Key: DATABASE_USERNAME
echo    Value: maschatdb_user
echo.
echo    Key: DATABASE_PASSWORD
echo    Value: YZ95BM0wLpPCDMGoBy8lHBWKRvQdJXzr
echo.
echo    Key: JWT_SECRET
echo    Value: [Generate using generate-jwt-secret.bat]
echo.
echo ⚠️  Security Reminders:
echo ======================
echo 1. Keep these credentials secure
echo 2. Never commit them to version control
echo 3. Use the Internal Database URL for Render
echo 4. Generate a strong JWT_SECRET
echo.
echo 📖 For detailed instructions, see DEPLOYMENT.md
echo.
echo 🎉 Environment setup complete!
pause 