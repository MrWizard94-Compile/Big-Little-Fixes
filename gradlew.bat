@echo off
setlocal enabledelayedexpansion

:: Self-bootstrap Gradle runner. Downloads a Gradle distribution on first run.
set "GRADLE_VERSION=8.6"
set "DIST_URL=https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip"
set "WRAPPER_DIR=%~dp0\.gradle\gradle-%GRADLE_VERSION%"
set "ZIP_FILE=%~dp0\gradle-%GRADLE_VERSION%-bin.zip"

if not exist "%WRAPPER_DIR%\bin\gradle.bat" (
    echo Gradle %GRADLE_VERSION% not found. Downloading distribution...
    powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri '%DIST_URL%' -OutFile '%ZIP_FILE%'"
    if errorlevel 1 (
        echo Failed to download Gradle distribution.
        exit /b 1
    )
    echo Extracting...
    powershell -Command "Expand-Archive -Path '%ZIP_FILE%' -DestinationPath '%~dp0\.gradle' -Force"
    if errorlevel 1 (
        echo Failed to extract Gradle distribution.
        exit /b 1
    )
    del "%ZIP_FILE%" >nul 2>&1
)

:: Execute the downloaded Gradle with passed arguments
"%WRAPPER_DIR%\bin\gradle.bat" %*
endlocal
