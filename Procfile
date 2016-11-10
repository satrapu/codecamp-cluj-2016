# run Heroku application in the cloud
# "PORT" environment variable is managed by Heroku
# "-Dswarm.http.port=$PORT" sets the HTTP port to be used by Undertow web server
web: java -jar '-Dswarm.http.port=$PORT' target/heroku-jee-ci-cd-swarm.jar
