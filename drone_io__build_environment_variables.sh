#!/usr/bin/env bash

# build environment variables for https://drone.io/github.com/satrapu/codecamp-cluj-2016
# specify the Maven version to use
MAVEN_VERSION=3.3.9
export MAVEN_VERSION

# specify the URL where to download Maven from
MAVEN_DOWNLOAD_URL=http://www-eu.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz
export MAVEN_DOWNLOAD_URL

# specify the Maven profile to use
MAVEN_PROFILE=heroku
export MAVEN_PROFILE