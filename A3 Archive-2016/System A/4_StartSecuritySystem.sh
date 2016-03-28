echo "Starting Alarm Controller" | java AlarmController | echo "Starting Sensors" | java DoorBreakSensor | java WindowBreakSensor | java MotionSensor| java SecurityMonitor
