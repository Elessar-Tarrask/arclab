@echo off
SET REMOVE_DIR=".\deploy\app_home"

if exist %REMOVE_DIR% rmdir /S /Q %REMOVE_DIR%