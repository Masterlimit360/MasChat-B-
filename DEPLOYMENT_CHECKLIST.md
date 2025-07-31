# MasChat Backend Deployment Checklist

## ✅ Pre-Deployment Checklist

### Repository Setup
- [ ] All deployment files are present in `MasChat-B-` directory
- [ ] `Dockerfile.render` exists and is configured
- [ ] `render.yaml` exists and is configured
- [ ] `application-render.properties` exists and is configured
- [ ] `pom.xml` has production profile
- [ ] `.dockerignore` is configured
- [ ] Database migration file exists

### Database Setup
- [x] PostgreSQL database created on Render
- [x] Database credentials saved securely
- [x] Database is in Oregon region (same as service will be)

## 🔧 Render Deployment Steps

### Step 1: Create Web Service
- [ ] Go to Render dashboard
- [ ] Click "New" → "Web Service"
- [ ] Connect GitHub repository
- [ ] Set service name: `maschat-backend`
- [ ] Set environment: `Docker`
- [ ] Set region: `Oregon` (same as database)
- [ ] Set branch: `main`
- [ ] Set root directory: `MasChat-B-`
- [ ] Leave build command empty
- [ ] Leave start command empty

### Step 2: Configure Environment Variables
- [ ] Add `SPRING_PROFILES_ACTIVE` = `render` ⚠️ **CRITICAL**
- [ ] Add `DATABASE_URL` = `postgresql://maschatdb_user:YZ95BM0wLpPCDMGoBy8lHBWKRvQdJXzr@dpg-d25f0jnfte5s73847gc0-a/maschatdb`
- [ ] Add `DATABASE_USERNAME` = `maschatdb_user`
- [ ] Add `DATABASE_PASSWORD` = `YZ95BM0wLpPCDMGoBy8lHBWKRvQdJXzr`
- [ ] Generate and add `JWT_SECRET` (run `generate-jwt-secret.bat`)
- [ ] Add `JWT_EXPIRATION` = `86400000` (optional)
- [ ] Add `PORT` = `8080` (optional)

### Step 3: Deploy
- [ ] Click "Create Web Service"
- [ ] Monitor build logs for errors
- [ ] Wait for deployment to complete (5-10 minutes)

## ✅ Post-Deployment Verification

### Health Checks
- [ ] Test root endpoint: `https://your-service.onrender.com/`
- [ ] Test health endpoint: `https://your-service.onrender.com/api/health`
- [ ] Test actuator health: `https://your-service.onrender.com/actuator/health`

### Database Verification
- [ ] Check logs for successful Flyway migration
- [ ] Verify database connection is working
- [ ] Check that tables were created successfully

### API Testing
- [ ] Test user registration endpoint
- [ ] Test user login endpoint
- [ ] Test basic API functionality

## 🚨 Troubleshooting

### Common Issues
- [ ] **"UnknownHostException: postgres"** → Set `SPRING_PROFILES_ACTIVE=render` ⚠️ **CRITICAL**
- [ ] Database connection failed → Check DATABASE_URL and credentials
- [ ] Build failed → Check Root Directory is set to `MasChat-B-`
- [ ] Application startup failed → Check environment variables
- [ ] Migration failed → Check database permissions

### Logs to Check
- [ ] Build logs in Render dashboard
- [ ] Application logs for startup errors
- [ ] Database connection logs
- [ ] Flyway migration logs

## 📞 Support

If deployment fails:
1. Check the logs in Render dashboard
2. Verify all environment variables are set correctly
3. Ensure database is accessible
4. Review this checklist for missed steps
5. Check `DEPLOYMENT.md` for detailed troubleshooting

## 🎉 Success Indicators

Your deployment is successful when:
- ✅ All health endpoints return 200 OK
- ✅ Database migrations complete successfully
- ✅ Application starts without errors
- ✅ API endpoints respond correctly
- ✅ No connection errors in logs 