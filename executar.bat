@echo off
chcp 65001 >nul
cd /d "%~dp0"
echo Iniciando o Sistema de Gestao de Mercado...
echo.
call gradlew.bat run
if errorlevel 1 (
    echo.
    echo Ocorreu um erro. Confira se o MariaDB esta rodando e se o banco gestao_mercado foi criado.
)
pause