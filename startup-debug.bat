@echo off
echo 🔍 MasChat Backend Startup Debug
echo ================================

echo 📋 Environment Variables:
echo =========================
echo SPRING_PROFILES_ACTIVE: %SPRING_PROFILES_ACTIVE%
echo DATABASE_URL: %DATABASE_URL%
echo DATABASE_USERNAME: %DATABASE_USERNAME%
echo PORT: %PORT%
echo JAVA_OPTS: %JAVA_OPTS%

echo.
echo 🔧 Configuration Check:
echo ======================

REM Check if render profile is set
if "%SPRING_PROFILES_ACTIVE%"=="render" (
    echo ✅ SPRING_PROFILES_ACTIVE is set to 'render'
) else (
    echo ❌ SPRING_PROFILES_ACTIVE is NOT set to 'render' (current: %SPRING_PROFILES_ACTIVE%)
    echo ⚠️  Setting SPRING_PROFILES_ACTIVE=render
    set SPRING_PROFILES_ACTIVE=render
)

REM Check if database URL is set
if defined DATABASE_URL (
    echo ✅ DATABASE_URL is set
    echo    URL: %DATABASE_URL%
) else (
    echo ❌ DATABASE_URL is NOT set
)

REM Check if database username is set
if defined DATABASE_USERNAME (
    echo ✅ DATABASE_USERNAME is set: %DATABASE_USERNAME%
) else (
    echo ❌ DATABASE_USERNAME is NOT set
)

REM Check if port is set
if defined PORT (
    echo ✅ PORT is set: %PORT%
) else (
    echo ⚠️  PORT is NOT set, using default: 8080
    set PORT=8080
)

echo.
echo 🚀 Starting Application...
echo ==========================

REM Start the application
java %JAVA_OPTS% -jar target/*.jar 