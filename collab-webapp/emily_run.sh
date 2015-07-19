#! /bin/bash
cd ~/git/unicorn-play/collab-webapp
touch src/main/resources/logging.log
> src/main/resources/logging.log
mvn clean compile jetty:run-war > src/main/resources/logging.log