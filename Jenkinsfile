node {
    timestamps{
        properties([
            buildDiscarder(
                logRotator(
                    artifactDaysToKeepStr: '', 
                    artifactNumToKeepStr: '', 
                    daysToKeepStr: '', 
                    numToKeepStr: '5'
                )
            ), 
            disableConcurrentBuilds(), 
            pipelineTriggers([])
        ])

        def mvnHome = tool 'maven-3.5.2'
        def mvn = "${mvnHome}/bin/mvn"
    
        stage('Checkout'){
            def scmVars = checkout scm
            def gitLongCommitHash = scmVars.GIT_COMMIT
            // Consider just the first 8 characters from the Git commit hash value
            def gitShortCommitHash = gitLongCommitHash[0..7]
            currentBuild.displayName = "${env.BUILD_NUMBER} - ${gitShortCommitHash}"
        }

        stage('Compile'){
            sh "${mvn} clean compile"
        }

        stage('Static Code Analysis'){
            // Perform static code analysis via SonarCloud.
            // See more here: https://about.sonarcloud.io/get-started/.
            withCredentials([
                usernamePassword(
                    credentialsId: 'service.sonar', 
                    passwordVariable: 'SONAR_PASSWORD', 
                    usernameVariable: 'SONAR_USERNAME')]){
                        sh "${mvn} org.jacoco:jacoco-maven-plugin:prepare-agent sonar:sonar \
                                -Dsonar.host.url=https://sonarcloud.io \
                                -Dsonar.organization=$SONAR_USERNAME \
                                -Dsonar.login=$SONAR_PASSWORD"
                    }
        }

        stage('Tests') {
            sh "${mvn} test"

            junit([
                allowEmptyResults: true, 
                testResults: 'target/surefire-reports/**/*.xml' 
            ])
        }

        stage('Cleanup'){
            cleanWs notFailBuild: true
        }
    }
}