@echo off
echo 🔐 Generating Secure JWT Secret for MasChat Backend
echo ==================================================

REM Generate a secure random string using PowerShell
for /f "delims=" %%i in ('powershell -Command "[System.Web.Security.Membership]::GeneratePassword(64, 10)"') do set JWT_SECRET=%%i

echo ✅ Generated JWT Secret:
echo ========================
echo %JWT_SECRET%
echo.
echo 📋 Copy this secret and use it as the JWT_SECRET environment variable in Render
echo.
echo ⚠️  Important Security Notes:
echo =============================
echo 1. Keep this secret secure and private
echo 2. Never commit it to version control
echo 3. Use different secrets for different environments
echo 4. Store it securely in Render's environment variables
echo.
echo 🔧 To use in Render:
echo ===================
echo 1. Go to your Render service settings
echo 2. Add environment variable:
echo    - Key: JWT_SECRET
echo    - Value: %JWT_SECRET%
echo.
echo 🎉 JWT Secret generated successfully!
pause 