echo "TRC Scouting Data Grabber"
echo "(C) Titan Robotics Club 2019"
while true; do 
	echo
	echo "      Choice Menu"
	echo "[1] - Pull match data from phone"
	echo "[2] - Pull pit data from phone"
	echo "[9] - Exit "
	read input
	if [ "$input" = "1" ];
	then
		echo "Pulling match data from phone..."
		mkdir matchdata
 		adb shell ls sdcard/TrcScoutingApp/*.csv | tr '\r' ' ' | xargs -n1 adb pull
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
 		adb shell ls sdcard/TrcPitScouting/*.csv | tr '\r' ' ' | xargs -n1 adb pull
		mv *.csv pitdata
		echo "Done"
	else
		echo "Please enter a valid choice."
	fi
done
