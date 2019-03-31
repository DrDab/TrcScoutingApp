@echo off
:start
echo Scouting App Installer
echo (C) Titan Robotics Club 2019
echo
echo [1] - Install app on phone
echo [9] - Exit 
SET /P _inputname= Choice>
IF "%_inputname%"=="1" GOTO :install
IF "%_inputname%"=="9" GOTO :done
:install
echo Installing App...
adb install app.apk
echo Done
goto :start
:done
echo Bye!
exit
