pipeline{
    agent any
    tools{
        maven 'maven3'
        jdk 'jdk17'
    }
    stages{
        stage('Build'){
            steps{
                bat 'mvn clean package -DskipTests'
            }
        }
        stage('Test'){
            steps{
                bat 'mvn test'
            }
        }
        stage('Deploy'){
            steps{
                echo 'Deploying the artifact..'
            }
        }
    }

}