pipeline{
    agent any
    tools{
        maven 'maven3'
        jdk 'jdk17'
    }
    stages{
        stage('call common pipeline'){
            steps{
                script {
                   checkout([
                       $class: 'GitSCM',
                       branches: [[name: '*/main']],
                       userRemoteConfigs: [[url: 'https://github.com/oneshoot-rh/common-jenkins-pipeline.git']]
                   ])
                   def centralizedPipeline = load 'Jenkinsfile'
                   centralizedPipeline(SERVICE_NAME: 'OneShoot')
               }
            }
        }
    }

}