pipeline{
    agent any
    tools{
        maven 'maven3'
        jdk 'jdk17'
    }
    environment{
        BRANCH = "${env.BRANCH_NAME}"
        BUILD_NUMBER = "${BUILD_NUMBER}"
    }
    stages{
        stage('call common pipeline'){
            steps{
                script {
                    build job: 'common-pipeline-oneshot', parameters: [
                        string(name: 'pname', value: 'oneshootmain'),
                        string(name:'branch', value: "${BRANCH}"),
                        string(name:'BUILD_NUMBER', value: "${BUILD_NUMBER}"),
                        string(name:'url', value: 'https://github.com/oneshoot-rh/one-shoot-main.git')],
                        wait: true
                    echo "logs:"
                    def downstreamBuild = downstreamJob.getRawBuild()
                    echo downstreamBuild.getLog()
                }
            }
        }
    }

}