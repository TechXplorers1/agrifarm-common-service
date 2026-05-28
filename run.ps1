# AgriFarm Startup Script
Write-Host "=== Starting AgriFarms Services ===" -ForegroundColor Green

# 1. Check if PostgreSQL port 5435 is already active
$portActive = Get-NetTCPConnection -LocalPort 5435 -ErrorAction SilentlyContinue
if ($portActive) {
    Write-Host "Port 5435 is already active. Assuming PostgreSQL is running." -ForegroundColor Yellow
} else {
    Write-Host "PostgreSQL is not running on port 5435. Starting private instance..." -ForegroundColor Cyan
    
    $pgBin = "C:\Program Files\PostgreSQL\16\bin"
    if (-not (Test-Path "$pgBin\postgres.exe")) {
        # Search for any installed PostgreSQL version
        $searchResult = Get-ChildItem -Path "C:\Program Files\PostgreSQL" -Filter "postgres.exe" -Recurse -ErrorAction SilentlyContinue | Select-Object -First 1
        if ($searchResult) {
            $pgBin = $searchResult.DirectoryName
        } else {
            $pgBin = $null
        }
    }
    
    if (-not $pgBin) {
        Write-Error "PostgreSQL installation not found in C:\Program Files\PostgreSQL. Please ensure PostgreSQL is installed."
        Exit 1
    }
    
    Write-Host "Found PostgreSQL binaries at: $pgBin" -ForegroundColor Gray
    
    $dataDir = Join-Path $PSScriptRoot "pg_dev_data"
    if (-not (Test-Path $dataDir)) {
        Write-Host "Initializing new private database cluster in pg_dev_data..." -ForegroundColor Cyan
        & "$pgBin\initdb.exe" -D $dataDir -U agrifarms --auth=trust
        
        # Start database temporarily to create agrifarms database
        Write-Host "Starting database temporarily to create schema..." -ForegroundColor Cyan
        & "$pgBin\pg_ctl.exe" -D $dataDir -o "-p 5435" start
        Start-Sleep -Seconds 3
        & "$pgBin\createdb.exe" -p 5435 -U agrifarms agrifarms
        & "$pgBin\pg_ctl.exe" -D $dataDir stop
        Start-Sleep -Seconds 2
    }
    
    # Start the private PostgreSQL instance
    & "$pgBin\pg_ctl.exe" -D $dataDir -o "-p 5435" start
    Start-Sleep -Seconds 2
}

# 2. Run the Spring Boot application
Write-Host "Launching AgriFarms Spring Boot Application..." -ForegroundColor Green
mvn spring-boot:run
