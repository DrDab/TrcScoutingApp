@echo off
:start
echo TRC Scouting App Data Grabber
echo (C) Titan Robotics Club 2019
taskkill /im adb.exe /f
:prompt
echo.
echo       Choice Menu
echo [1] - Pull all match data from phone
echo [2] - Pull all pit data from phone
Rem echo [3] - Pull most recent match data
Rem echo [4] - Pull most recent pit data
echo [9] - Exit 
SET /P _inputname=Choice: 
IF "%_inputname%"=="1" GOTO :pull
IF "%_inputname%"=="9" GOTO :done
IF "%_inputname%"=="2" GOTO :pullpit
Rem IF "%_inputname%"=="3" GOTO :pullmatchrecent
Rem IF "%_inputname%"=="4" GOTO :pullpitrecent

:confused
echo Please enter a valid choice.
goto :prompt

:done
echo Bye!
exit

:pull
echo Pulling all match data from phone...
mkdir matchdata
adb shell ls sdcard/TrcScoutingApp/*.csv | tr '\r' ' ' | xargs -n1 ./adb pull -a
move *.csv matchdata
echo Done
goto :start

:pullpit
echo Pulling all pit data from phone...
mkdir pitdata
adb shell ls sdcard/TrcPitScouting/*.csv | tr '\r' ' ' | xargs -n1 ./adb pull -a
move *.csv pitdata
echo Done
goto :start

Rem :pullmatchrecent
Rem echo Pulling recent match data from phone...
Rem mkdir matchdata
Rem adb shell ls -t sdcard/TrcScoutingApp/*.csv | tr '\r' ' ' | grep -m1 ""| xargs -n1 ./adb pull -a
Rem move *.csv matchdata
Rem echo Done
Rem goto :start

Rem :pullpitrecent
Rem echo Pulling recent pit data from phone...
Rem mkdir pitdata
Rem adb shell ls -t sdcard/TrcScoutingApp/*.csv | tr '\r' ' ' | grep -m1 "" | xargs -n1 ./adb pull -a
Rem move *.csv pitdata
Rem echo Done
Rem goto :start


