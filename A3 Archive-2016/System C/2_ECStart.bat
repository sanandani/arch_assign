%ECHO OFF
%ECHO Starting ECS System
PAUSE
%ECHO Starting System Maintenance Controller Console
START "System Maintenance CONTROLLER CONSOLE" /MIN /NORMAL java ECSSrvcMaintainConsole %1
%ECHO Starting Service Monitor Console
START "TEMPERATURE CONTROLLER CONSOLE" /MIN /NORMAL java ECSrvcMaintainMonitor %1

PAUSE
%ECHO Starting Temperature Controller Console
START "TEMPERATURE CONTROLLER CONSOLE" /MIN /NORMAL java TemperatureController %1
%ECHO Starting Humidity Sensor Console
START "HUMIDITY CONTROLLER CONSOLE" /MIN /NORMAL java HumidityController %1
START "TEMPERATURE SENSOR CONSOLE" /MIN /NORMAL java TemperatureSensor %1
%ECHO Starting Humidity Sensor Console
START "HUMIDITY SENSOR CONSOLE" /MIN /NORMAL java HumiditySensor %1
%ECHO ECS Monitoring Console
START "MUSEUM ENVIRONMENTAL CONTROL SYSTEM CONSOLE" /NORMAL java ECSConsole %1