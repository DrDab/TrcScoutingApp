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
echo [3] - Pull most recent match data
echo [4] - Pull most recent pit data
echo [8] - Access phone shell
echo [9] - Exit 
SET /P _inputname=Choice: 
IF "%_inputname%"=="1" GOTO :pull
IF "%_inputname%"=="9" GOTO :done
IF "%_inputname%"=="2" GOTO :pullpit
IF "%_inputname%"=="3" GOTO :pullmatchrecent
IF "%_inputname%"=="4" GOTO :pullpitrecent
IF "%_inputname%"=="8" GOTO :shell

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
goto :prompt

:pullpit
echo Pulling all pit data from phone...
mkdir pitdata
adb shell ls sdcard/TrcPitScouting/*.csv | tr '\r' ' ' | xargs -n1 ./adb pull -a
move *.csv pitdata
echo Done
goto :prompt

:pullmatchrecent
echo Pulling recent match data from phone...
mkdir matchdata
adb shell ls sdcard/TrcScoutingApp/*.csv | tr '\r' ' ' | xargs -n1 ./adb pull -a
ls *.csv -t | tail -n +2 | xargs ./rm
move *.csv matchdata
echo Done
goto :prompt

:pullpitrecent
echo Pulling recent pit data from phone...
mkdir pitdata
adb shell ls sdcard/TrcPitScouting/*.csv | tr '\r' ' ' | xargs -n1 ./adb pull -a
ls *.csv -t | tail -n +2 | xargs ./rm
move *.csv pitdata
echo Done
goto :prompt

:shell
adb shell
goto :prompt

