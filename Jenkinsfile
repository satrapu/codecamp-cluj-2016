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

        stage('Test') {
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
    }
}