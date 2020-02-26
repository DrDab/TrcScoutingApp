echo TRC Scouting App Installer
echo "(C) Titan Robotics Club 2019"
while true; do 
	echo
	echo "      Choice Menu"
	echo "[1] - Install app on phone"
	echo "[8] - Access phone shell"
	echo "[9] - Exit "
	read input
	if [ "$input" = "1" ];
	then
		echo "Installing App..."
 		adb install -r app.apk;
		echo "Done"
	elif [ "$input" = "8" ];
	then
		adb shell;
	elif [ "$input" = "9" ];
	then
		echo "Bye!"
		exit
	else
		echo "Please enter a valid choice."
	fi
done
