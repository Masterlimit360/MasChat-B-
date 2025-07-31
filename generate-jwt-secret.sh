#!/bin/bash

# Generate a secure JWT secret for MasChat Backend
echo "🔐 Generating Secure JWT Secret for MasChat Backend"
echo "=================================================="

# Generate a secure random string
JWT_SECRET=$(openssl rand -base64 64 | tr -d "=+/" | cut -c1-64)

echo "✅ Generated JWT Secret:"
echo "========================"
echo "$JWT_SECRET"
echo ""
echo "📋 Copy this secret and use it as the JWT_SECRET environment variable in Render"
echo ""
echo "⚠️  Important Security Notes:"
echo "============================="
echo "1. Keep this secret secure and private"
echo "2. Never commit it to version control"
echo "3. Use different secrets for different environments"
echo "4. Store it securely in Render's environment variables"
echo ""
echo "🔧 To use in Render:"
echo "==================="
echo "1. Go to your Render service settings"
echo "2. Add environment variable:"
echo "   - Key: JWT_SECRET"
echo "   - Value: $JWT_SECRET"
echo ""
echo "🎉 JWT Secret generated successfully!" 