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

            // Since there is no Sonar report generated inside the current Jenkins workspace,
            // we need to generate an HTML document containing the link to the report hosted on sonarcloud.io site.
            sh 'mkdir -p ./target/sonarcloud-report'
            sh """
                cat << EOF > ./target/sonarcloud-report/index.html
                <html>
                    <body>
                        <h1>
                            Right click <a href="https://sonarcloud.io/dashboard?id=ro.satrapu%3Acodecamp.cj.2016" target="_parent">here</a> for the external Sonar report.
                        </h1>
                    </body>
                </html
                EOF
            """

            // You need to install HTML Publisher Jenkins plugin to be able to use "publishHTML" step.
            publishHTML(
                target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: './target/sonarcloud-report',
                    reportFiles: 'index.html',
                    reportName: 'Sonar'
                ]
            )
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