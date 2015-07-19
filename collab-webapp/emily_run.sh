#! /bin/bash
cd ~/git/unicorn-play/collab-webapp
> src/main/resources/logging.log
mvn clean compile jetty:run-war > src/main/resources/logging.log