pipeline {
    agent any

    tools { 
        maven 'maven-3.5.2' 
        jdk 'oracle-jdk-8u152' 
    }

    stages {
        stage ('Compile') {
            steps {
                sh 'mvn clean compile' 
            }
        }

        stage('Static Code Analysis') {
            // Perform static code analysis via SonarCloud.
            // See more here: https://about.sonarcloud.io/get-started/.
            steps {
                withCredentials([usernamePassword(credentialsId: 'service.sonar', passwordVariable: 'SONAR_PASSWORD', usernameVariable: 'SONAR_USERNAME')]) {
                    sh 'mvn org.jacoco:jacoco-maven-plugin:prepare-agent sonar:sonar \
                            -Dsonar.host.url=https://sonarcloud.io \
                            -Dsonar.organization=$SONAR_USERNAME \
                            -Dsonar.login=$SONAR_PASSWORD'
                }
            }
        }

        stage('Tests') {
            steps {
                sh 'mvn test' 
            }
            
            post {
                success {
                    junit ([
                        allowEmptyResults: true, 
                        testResults: 'target/surefire-reports/**/*.xml' 
                    ])
                }
            }
        }

        stage('Cleanup') {
            steps {
                cleanWs notFailBuild: true
            }
        }
    }
}