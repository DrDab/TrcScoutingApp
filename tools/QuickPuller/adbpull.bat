setlocal enabledelayedexpansion
if %1.==. (
    echo Wildcard parameter required.
    goto end
)
for /F "tokens=* USEBACKQ" %%F in (`adb shell ls %1`) do (
    set text=%%F
    set mfile=!text:~0,-1!
    adb pull "!mfile!" ./outputs
)
:end
endlocal