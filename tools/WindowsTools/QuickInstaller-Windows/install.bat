@echo off
:start
echo TRC Scouting App Installer
echo (C) Titan Robotics Club 2019
taskkill /im adb.exe /f
:prompt
echo.
echo       Choice Menu
echo [1] - Install app on phone
echo [9] - Exit 
SET /P _inputname=Choice: 
IF "%_inputname%"=="1" GOTO :install
IF "%_inputname%"=="9" GOTO :done
:confused
echo Please enter a valid choice.
goto :prompt
:done
echo Bye!
exit
:install
echo Installing App...
adb install -r app.apk
echo Done
goto :start