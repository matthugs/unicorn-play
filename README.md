unicorn-play
============

A terrifying mpd client-webapp hybrid

opencoweb boilerplate generated through the magic of maven:
mvn archetype:generate -DarchetypeGroupId=org.opencoweb -DarchetypeArtifactId=coweb-archetype -DarchetypeVersion=0.8.3.1

To run:

1. get MPD up and running in a daemonized process. Config file should be set to make it run on port 6001 with password "password"

2. Get the server running:

cd unicorn-play/collab-webapp
mvn compile
mvn jetty:run-war

3. Point your browser to http://localhost:8080/collab-webapp/
