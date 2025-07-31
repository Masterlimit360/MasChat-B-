# MasChat Backend Deployment Guide for Render

This guide will help you deploy the MasChat backend to Render successfully.

## Prerequisites

- GitHub repository with your code
- Render account
- Basic understanding of environment variables

## Step 1: Prepare Your Repository

Ensure your repository has the following files in the `MasChat-B-` directory:

- ✅ `Dockerfile.render` - Optimized Dockerfile for Render
- ✅ `render.yaml` - Render service configuration
- ✅ `src/main/resources/application-render.properties` - Render-specific configuration
- ✅ `pom.xml` - Maven configuration with production profile
- ✅ `.dockerignore` - Docker build optimization
- ✅ `src/main/resources/db/migration/V1__complete_schema.sql` - Database schema

## Step 2: Create PostgreSQL Database on Render

1. **Go to Render Dashboard**
   - Visit [render.com](https://render.com)
   - Sign in to your account

2. **Create New PostgreSQL Database**
   - Click "New" → "PostgreSQL"
   - **Name**: `maschat-db` (or your preferred name)
   - **Database**: `maschat` (or your preferred name)
   - **User**: Leave as default (will be auto-generated)
   - **Region**: Choose closest to your users (e.g., Oregon)
   - **Plan**: Free (for testing) or Starter (for production)
   - Click "Create Database"

3. **Note Database Credentials**
   - After creation, note down:
     - **Internal Database URL** (starts with `postgresql://`)
     - **External Database URL** (if needed)
     - **Database Name**
     - **Username**
     - **Password**

## Step 3: Deploy the Backend Service

1. **Create New Web Service**
   - Click "New" → "Web Service"
   - Connect your GitHub repository

2. **Configure Service Settings**
   - **Name**: `maschat-backend`
   - **Environment**: `Docker`
   - **Region**: Same as your database
   - **Branch**: `main` (or your default branch)
   - **Root Directory**: `MasChat-B-` (important!)
   - **Build Command**: Leave empty
   - **Start Command**: Leave empty

3. **Set Environment Variables**
   Click "Advanced" and add these environment variables:

   **Required Variables:**
   ```
   SPRING_PROFILES_ACTIVE=render
   DATABASE_URL=<your-internal-database-url>
   DATABASE_USERNAME=<your-database-username>
   DATABASE_PASSWORD=<your-database-password>
   JWT_SECRET=<generate-a-secure-random-string>
   ```

   **Optional Variables:**
   ```
   JWT_EXPIRATION=86400000
   PORT=8080
   ```

4. **Create Service**
   - Click "Create Web Service"
   - Render will start building and deploying

## Step 4: Monitor Deployment

1. **Watch Build Logs**
   - The first build may take 5-10 minutes
   - Monitor the logs for any errors

2. **Check Health Endpoints**
   Once deployed, test these endpoints:
   - `https://your-service-name.onrender.com/` - Root endpoint
   - `https://your-service-name.onrender.com/api/health` - Health check
   - `https://your-service-name.onrender.com/actuator/health` - Spring Boot health

## Step 5: Troubleshooting Common Issues

### Database Connection Issues

**Error**: `Unable to obtain connection from database`

**Solutions**:
1. Verify `DATABASE_URL` is correct (use internal URL from Render)
2. Ensure database is in the same region as your service
3. Check that database credentials are correct
4. Verify database is running and accessible

### Build Failures

**Error**: `Build failed`

**Solutions**:
1. Check that `Root Directory` is set to `MasChat-B-`
2. Verify all required files are present
3. Check Maven dependencies in `pom.xml`
4. Review build logs for specific error messages

### Application Startup Issues

**Error**: `Application failed to start`

**Solutions**:
1. Check environment variables are set correctly
2. Verify database is accessible
3. Review application logs for specific errors
4. Ensure all required environment variables are present

## Step 6: Verify Deployment

1. **Test API Endpoints**
   ```bash
   # Test root endpoint
   curl https://your-service-name.onrender.com/
   
   # Test health endpoint
   curl https://your-service-name.onrender.com/api/health
   
   # Test actuator health
   curl https://your-service-name.onrender.com/actuator/health
   ```

2. **Check Database Migration**
   - The application should automatically run Flyway migrations
   - Check logs for migration success/failure

3. **Test Authentication**
   - Try to register a new user
   - Test login functionality

## Environment Variables Reference

| Variable | Required | Description | Example |
|----------|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Yes | Spring profile to use | `render` |
| `DATABASE_URL` | Yes | PostgreSQL connection URL | `postgresql://user:pass@host:5432/db` |
| `DATABASE_USERNAME` | Yes | Database username | `maschat_user` |
| `DATABASE_PASSWORD` | Yes | Database password | `secure_password` |
| `JWT_SECRET` | Yes | Secret for JWT signing | `your-secure-secret-key` |
| `JWT_EXPIRATION` | No | JWT expiration time (ms) | `86400000` |
| `PORT` | No | Application port | `8080` |

## Security Best Practices

1. **Use Strong JWT Secret**
   - Generate a secure random string
   - At least 32 characters long
   - Include letters, numbers, and special characters

2. **Database Security**
   - Use internal database URL when possible
   - Keep database credentials secure
   - Regularly rotate passwords

3. **Environment Variables**
   - Never commit secrets to version control
   - Use Render's secret management
   - Regularly review and update secrets

## Monitoring and Maintenance

1. **Health Checks**
   - Monitor `/actuator/health` endpoint
   - Set up alerts for service downtime
   - Check logs regularly

2. **Database Maintenance**
   - Monitor database performance
   - Set up automated backups
   - Review connection pool settings

3. **Application Updates**
   - Deploy updates through Render dashboard
   - Test changes in staging environment
   - Monitor for any issues after deployment

## Support

If you encounter issues:

1. Check the application logs in Render dashboard
2. Verify all environment variables are set correctly
3. Ensure database is accessible and running
4. Review the troubleshooting section above
5. Check Render's documentation for platform-specific issues

## Next Steps

After successful deployment:

1. Update your frontend application to use the new backend URL
2. Test all major functionality
3. Set up monitoring and alerts
4. Configure custom domain (optional)
5. Set up SSL certificates (automatic with Render) 