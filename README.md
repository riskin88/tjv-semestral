# How to run
## Server
 - Start postgreSQL database named *tjv_semestral* on *localhost:5432* with following user settings:
	 - username: *tjv*
	 - password: *heslo*
- run `/gradlew bootRun` in the */server* directory
- the API should now be listening on *localhost:8080*
## Client

- run `/gradlew bootRun` in the */client* directory
- the app should now be available on *localhost:8081/units*
### Features
- you can find all currently existing units directly on the client homepage
- these can be filtered by their location using the search bar at the top of the page
	- search for an empty string shows all units
- create, update and delete operations can be performed by the corresponding links from the homepage

