#!/usr/bin/env bash

# build script to be used by any CI server running on a *nix OS, like Linux
# get MAVEN_VERSION or default value
DEFAULT_MAVEN_VERSION=3.3.9
MAVEN_VERSION=${MAVEN_VERSION-${DEFAULT_MAVEN_VERSION}}
echo "MAVEN_VERSION=${MAVEN_VERSION}"

# get MAVEN_PROFILE or default value
DEFAULT_MAVEN_PROFILE=heroku
MAVEN_PROFILE=${MAVEN_PROFILE-${DEFAULT_MAVEN_PROFILE}}
echo "MAVEN_PROFILE=${MAVEN_PROFILE}"

# set folder where to download Maven
MAVEN_DOWNLOAD_HOME=$(pwd)/maven
echo "MAVEN_DOWNLOAD_HOME=${MAVEN_DOWNLOAD_HOME}"

# create Maven home folder
## "mkdir" command options:
### -p: will create all folders present in the specified path, if they do not exist already
echo "Creating Maven home folder ..."
sudo mkdir -p ${MAVEN_DOWNLOAD_HOME}
echo "Maven home folder has been created"

# build the URL from where to download Maven
MAVEN_DOWNLOAD_URL=http://www-eu.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz

# download Maven archive from public domain
## "wget" command options:
### -nv: will generate a smaller amount of output
echo "Downloading Maven ..."
wget ${MAVEN_DOWNLOAD_URL} -nv
echo "Maven has been downloaded"

# extract Maven from the downloaded archive to a local folder
## "tar" command options:
### x: extracts files and folders from the archive
### f: specifies the current archive file
### -C <FOLDER>: will extract the archive to the folder identified by "<FOLDER>" name
echo "Extracting Maven archive ..."
sudo tar xf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C ${MAVEN_DOWNLOAD_HOME}
echo "Maven archive has been extracted"

# declare and initialize a variable with the name of the folder where Maven has been downloaded to
MAVEN_HOME=${MAVEN_DOWNLOAD_HOME}/$(ls ${MAVEN_DOWNLOAD_HOME})
echo "Maven has been installed into folder: ${MAVEN_HOME}"

# run Maven build using the configured profile; ensure the downloaded version of Maven is used, not the default one!
## "maven" command options:
### -q: show only error messages
### -P<MAVEN_PROFILE_NAME>: use Maven profile identified by <MAVEN_PROFILE_NAME>
echo "Building application using Maven profile: ${MAVEN_PROFILE} ..."
${MAVEN_HOME}/bin/mvn -q compile test -P${MAVEN_PROFILE}

# check whether Maven was able to build the application or not
MAVEN_OUTCOME=$?

if [ ${MAVEN_OUTCOME} -eq 0 ]; then
  echo "Build successful"
else
  echo "Build failed"
fi

exit ${MAVEN_OUTCOME}