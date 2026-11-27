# =============================================================
# setup-database.ps1
# Creates the new PostgreSQL database: comp_info_dept_portal
# Run this once before starting the Spring Boot application.
#
# Usage:
#   .\setup-database.ps1 -PgPassword "your_postgres_password"
# =============================================================

param(
    [Parameter(Mandatory=$true)]
    [string]$PgPassword
)

$PgBin    = "C:\Program Files\PostgreSQL\19\bin"
$PgUser   = "postgres"
$DbName   = "comp_info_dept_portal"
$PgHost   = "localhost"
$PgPort   = "5432"

$env:PGPASSWORD = $PgPassword

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  Comp & Info Dept Portal — DB Setup" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# 1. Check connectivity
Write-Host "[1/3] Checking PostgreSQL connectivity..." -ForegroundColor Yellow
& "$PgBin\pg_isready.exe" -h $PgHost -p $PgPort -U $PgUser
if ($LASTEXITCODE -ne 0) {
    Write-Error "Cannot reach PostgreSQL at $PgHost`:$PgPort. Is the service running?"
    exit 1
}
Write-Host "      OK — PostgreSQL is accepting connections." -ForegroundColor Green

# 2. Create the database (idempotent)
Write-Host ""
Write-Host "[2/3] Creating database '$DbName'..." -ForegroundColor Yellow
$check = & "$PgBin\psql.exe" -U $PgUser -h $PgHost -p $PgPort -tAc "SELECT 1 FROM pg_database WHERE datname='$DbName';"
if ($check -eq "1") {
    Write-Host "      Database '$DbName' already exists — skipping." -ForegroundColor DarkYellow
} else {
    & "$PgBin\createdb.exe" -U $PgUser -h $PgHost -p $PgPort -E UTF8 -T template0 $DbName
    if ($LASTEXITCODE -eq 0) {
        Write-Host "      Database '$DbName' created successfully." -ForegroundColor Green
    } else {
        Write-Error "Failed to create database '$DbName'."
        exit 1
    }
}

# 3. Update application.properties password hint
Write-Host ""
Write-Host "[3/3] Reminder: set DB_PASSWORD in application.properties or as an env var." -ForegroundColor Yellow
Write-Host "      DB_URL      = jdbc:postgresql://$PgHost`:$PgPort/$DbName"
Write-Host "      DB_USERNAME = $PgUser"
Write-Host "      DB_PASSWORD = <your password>"
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  Done!  Run the app with:" -ForegroundColor Green
Write-Host "  cd Comp-Info-Department-Portal" -ForegroundColor Green
Write-Host "  .\mvnw.cmd spring-boot:run" -ForegroundColor Green
Write-Host "  Flyway will apply V1__Initial_Schema + V2__Seed_Data automatically." -ForegroundColor Green
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""
