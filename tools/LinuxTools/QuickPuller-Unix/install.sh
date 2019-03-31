echo "TRC Scouting Data Grabber"
echo "(C) Titan Robotics Club 2019"
while true; do 
	echo
	echo "      Choice Menu"
	echo "[1] - Pull data from phone"
	echo "[9] - Exit "
	read input
	if [ "$input" = "1" ];
	then
		echo "Pulling data from phone..."
		mkdir outputs
 		adb shell ls sdcard/TrcScoutingApp/*.csv | tr '\r' ' ' | xargs -n1 adb pull
		mv *.csv outputs
		echo "Done"
	elif [ "$input" = "9" ];
	then
		echo "Bye!"
		exit
	else
		echo "Please enter a valid choice."
	fi
done