@echo off
:start
echo TRC Scouting App Data Grabber
echo (C) Titan Robotics Club 2019
taskkill /im adb.exe /f
:prompt
echo.
echo       Choice Menu
echo [1] - Pull match data from phone
echo [2] - Pull pit data from phone
echo [9] - Exit 
SET /P _inputname=Choice: 
IF "%_inputname%"=="1" GOTO :pull
IF "%_inputname%"=="9" GOTO :done
IF "%_inputname%"=="2" GOTO :pullpit
:confused
echo Please enter a valid choice.
goto :prompt
:done
echo Bye!
exit
:pull
echo Pulling match data from phone...
mkdir matchdata
adb shell ls sdcard/TrcScoutingApp/*.csv | tr '\r' ' ' | xargs -n1 ./adb pull
move *.csv matchdata
echo Done
goto :start
:pullpit
echo Pulling pit data from phone...
mkdir pitdata
adb shell ls sdcard/TrcPitScouting/*.csv | tr '\r' ' ' | xargs -n1 ./adb pull
move *.csv matchdata
echo Done
goto :start
