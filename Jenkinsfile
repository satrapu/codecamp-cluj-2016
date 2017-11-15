pipeline {
    agent any

    tools { 
        maven 'maven-3.5.2' 
        jdk 'oracle-jdk-8u152' 
    }

    stages {
        stage('Compile') {
            steps {
                echo 'Compiling...'
            }
        }

        stage('Test') {
            steps {
                echo 'Testing...'
            }
        }
    }
}