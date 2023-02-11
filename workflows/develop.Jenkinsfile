pipeline {
    agent {
      docker { image 'maven:3.8.7-eclipse-temurin-17' }
    }
    environment {
        COMMITER_NAME = sh (
              script: 'git show -s --pretty=\'%an\'',
              returnStdout: true
        ).trim()
    }
    stages {
        stage('Prepare') {
            steps {
                echo 'Pulling...' + env.BRANCH_NAME
                checkout scm
            }
        }
        stage('Backend') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "cd ./backend && mvn clean package sonar:sonar \
                          -Dsonar.projectKey=maven-jenkins-pipeline \
                          -Dsonar.host.url=http://vmpx07.polytech.unice.fr:8001 \
                          -Dsonar.login=${env.SONAR_AUTH_TOKEN}"
                }
            }
        }
        stage("Sonar backend check") {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('CLI') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "cd ./cli && mvn clean package sonar:sonar \
                          -Dsonar.projectKey=maven-jenkins-pipeline \
                          -Dsonar.host.url=http://vmpx07.polytech.unice.fr:8001 \
                          -Dsonar.login=${env.SONAR_AUTH_TOKEN}"
                }
            }
        }
        stage("Sonar cli check") {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        
        stage("Notify") {
            steps {
                notify(currentBuild.currentResult)
            }
        }
    }
}

def notify(result) {
    if(BRANCH_NAME != 'develop' && BRANCH_NAME != 'main') {
        return
    }
    discordSend description: "Pipeline ${parseResult(result)}", footer: "By ${COMMITER_NAME}", link: BUILD_URL, result: result, title: JOB_NAME.replaceAll('%2F', '/'), webhookURL: "https://discord.com/api/webhooks/1074071003132600413/voTcEpidfmcOtBOJsJEIepZl1xf0jODYOWHg9I_RQMEmHAJtoBDx-R1AE3ylVR9cCvug"
}

def parseResult(result) {
    switch(result){
        case 'SUCCESS':
            return 'successful'
        case 'FAILURE':
            return 'failed'
        case 'UNSTABLE':
            return 'unstable'
        case 'ABORTED':
            return 'aborted'
        default:
            return 'unknown'
    }
}
