#!/bin/bash

echo "🔍 MasChat Backend Startup Debug"
echo "================================"

echo "📋 Environment Variables:"
echo "========================="
echo "SPRING_PROFILES_ACTIVE: $SPRING_PROFILES_ACTIVE"
echo "DATABASE_URL: $DATABASE_URL"
echo "DATABASE_USERNAME: $DATABASE_USERNAME"
echo "PORT: $PORT"
echo "JAVA_OPTS: $JAVA_OPTS"

echo ""
echo "🔧 Configuration Check:"
echo "======================"

# Check if render profile is set
if [ "$SPRING_PROFILES_ACTIVE" = "render" ]; then
    echo "✅ SPRING_PROFILES_ACTIVE is set to 'render'"
else
    echo "❌ SPRING_PROFILES_ACTIVE is NOT set to 'render' (current: $SPRING_PROFILES_ACTIVE)"
    echo "⚠️  Setting SPRING_PROFILES_ACTIVE=render"
    export SPRING_PROFILES_ACTIVE=render
fi

# Check if database URL is set
if [ -n "$DATABASE_URL" ]; then
    echo "✅ DATABASE_URL is set"
    echo "   URL: $DATABASE_URL"
else
    echo "❌ DATABASE_URL is NOT set"
fi

# Check if database username is set
if [ -n "$DATABASE_USERNAME" ]; then
    echo "✅ DATABASE_USERNAME is set: $DATABASE_USERNAME"
else
    echo "❌ DATABASE_USERNAME is NOT set"
fi

# Check if port is set
if [ -n "$PORT" ]; then
    echo "✅ PORT is set: $PORT"
else
    echo "⚠️  PORT is NOT set, using default: 8080"
    export PORT=8080
fi

echo ""
echo "🚀 Starting Application..."
echo "=========================="

# Start the application
exec java $JAVA_OPTS -jar app.jar 