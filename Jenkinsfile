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
                    build job: 'common-pipeline-oneshot', parameters: [
                        string(name: 'pname', value: 'oneshootmain'),
                        string(name:'branch', value: 'main'),
                        string(name:'url', value: 'https://github.com/oneshoot-rh/one-shoot-main.git')],
                        wait: true
                }
            }
        }
    }

}