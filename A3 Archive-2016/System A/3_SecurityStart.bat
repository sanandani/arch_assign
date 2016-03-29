%ECHO OFF
%ECHO Starting ECS System
PAUSE
%ECHO Starting Alarm Controller Console
START "Alarm CONTROLLER CONSOLE" /MIN /NORMAL java AlarmController %1
%ECHO Starting Doorbreak Sensor Console
START "Doorbreak SENSOR CONSOLE" /MIN /NORMAL java DoorBreakSensor %1
%ECHO Starting WindowBreak Sensor Console
START "WindowBreak SENSOR CONSOLE" /MIN /NORMAL java WindowBreakSensor %1
%ECHO Starting Motion Sensor Console
START "Motion SENSOR CONSOLE" /MIN /NORMAL java MotionSensor %1
%ECHO Starting Security Monitor Console
START "Security Monitor CONSOLE" /MIN /NORMAL java SecurityMonitor %1
%ECHO Security  Console
START "Security SYSTEM CONSOLE" /NORMAL java SecurityConsole %1