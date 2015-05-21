# configuration-loader
Configuration loader to load properties
Config.load("path_to_config_file", ["array", "of", "overrides]) loads the properties from the config file for the provided overrides.

Config.get("group.setting") returns the value/array from the loaded properties.

---------------------------------------------------------------------------------------------------------------
A config file will appear as follows: 
 
[common] 
 
basic_size_limit = 26214400 
 
student_size_limit = 52428800 

[ftp] 
 
name = “hello there, ftp uploading” 
 
path = /tmp/ 
 
path<production> = /srv/var/tmp/ 
 
path<staging> = /srv/uploads/ 
 
path<ubuntu> = /etc/var/uploads 
 
enabled = no 
