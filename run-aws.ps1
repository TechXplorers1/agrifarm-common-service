# AgriFarm AWS Connection Startup Script
Write-Host "=== Starting AgriFarms Backend Connected to AWS RDS ===" -ForegroundColor Green

# 1. Prompt for password securely at command line
$passwd = Read-Host "Enter the AWS RDS password for 'agri_db_admin_user'" -AsSecureString
$BSTR = [System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($passwd)
$plainPassword = [System.Runtime.InteropServices.Marshal]::PtrToStringAuto($BSTR)

if ([string]::IsNullOrEmpty($plainPassword)) {
    Write-Error "Password cannot be empty."
    Exit 1
}

# 2. Set environment variables to override the local database configurations
$env:SPRING_DATASOURCE_URL = "jdbc:postgresql://agri-prod-database.cr262csoswwh.ap-south-2.rds.amazonaws.com:5432/agri"
$env:SPRING_DATASOURCE_USERNAME = "agri_db_admin_user"
$env:SPRING_DATASOURCE_PASSWORD = $plainPassword

Write-Host "Target AWS RDS: agri-prod-database.cr262csoswwh.ap-south-2.rds.amazonaws.com" -ForegroundColor Cyan
Write-Host "Launching Spring Boot Server..." -ForegroundColor Green

# 3. Launch the Spring Boot Server
mvn spring-boot:run
