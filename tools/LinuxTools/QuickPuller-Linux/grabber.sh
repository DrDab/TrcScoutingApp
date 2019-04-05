echo "TRC Scouting Data Grabber"
echo "(C) Titan Robotics Club 2019"
while true; do 
	echo
	echo "      Choice Menu"
	echo "[1] - Pull all match data from phone"
	echo "[2] - Pull all pit data from phone"
	echo "[3] - Pull most recent match data"
	echo "[4] - Pull most recent pit data"
	echo "[8] - Access phone shell"
	echo "[9] - Exit "
	read input
	if [ "$input" = "1" ];
	then
		echo "Pulling match data from phone..."
		mkdir matchdata
 		adb shell ls sdcard/TrcScoutingApp/*.csv | tr '\r' ' ' | xargs -n1 adb pull -a
		mv *.csv matchdata
		echo "Done"
	elif [ "$input" = "9" ];
	then
		echo "Bye!"
		exit
	elif [ "$input" = "2" ];
	then
		echo "Pulling pit data from phone..."
		mkdir pitdata
 		adb shell ls sdcard/TrcPitScouting/*.csv | tr '\r' ' ' | xargs -n1 adb pull -a
		mv *.csv pitdata
		echo "Done"
	elif [ "$input" = "3" ];
	then
		echo "Pulling match data from phone..."
		mkdir matchdata
 		adb shell ls sdcard/TrcScoutingApp/*.csv | tr '\r' ' ' | xargs -n1 adb pull -a
		ls *.csv -t | tail -n +2 | xargs rm
		mv *.csv matchdata
		echo "Done"
	elif [ "$input" = "4" ];
	then
		echo "Pulling pit data from phone..."
		mkdir pitdata
 		adb shell ls sdcard/TrcPitScouting/*.csv | tr '\r' ' ' | xargs -n1 adb pull -a
		ls *.csv -t | tail -n +2 | xargs rm
		mv *.csv pitdata
		echo "Done"
	elif [ "$input" = "8" ];
	then
		adb shell
	else
		echo "Please enter a valid choice."
	fi
done
