pipeline {
    agent {
      docker { image 'ci/pipeline' }
    }

    environment {
        COMMITER_NAME = sh (
              script: 'git show -s --pretty=\'%an\'',
              returnStdout: true
        ).trim()
    }


    stages {
        stage('Prepare') {
            options {
              timeout(time: 5, unit: 'MINUTES')   // timeout on this stage
            }
            steps {
                echo 'Pulling...' + env.BRANCH_NAME
                checkout scm
            }
        }

        stage('Backend') {
            when { changeset "backend/*"}
            stages {
                stage('Verify'){
                    steps {
                        timeout(time: 5, unit: 'MINUTES') {
                            withSonarQubeEnv('SonarQube') {
                                sh "cd ./backend && mvn clean verify sonar:sonar \
                                      -Dsonar.projectKey=maven-jenkins-pipeline \
                                      -Dsonar.host.url=http://vmpx07.polytech.unice.fr:8001 \
                                      -Dsonar.login=${env.SONAR_AUTH_TOKEN}"
                            }
                        }
                    }
                }
                stage('Quality Gate'){
                    steps {
                        timeout(time: 10, unit: 'MINUTES') {
                            waitForQualityGate abortPipeline: true
                        }
                    }
                }
                stage('Build'){
                    steps {
                        timeout(time: 5, unit: 'MINUTES') {
                            sh "cd ./backend && mvn -s .m2/settings.xml deploy \
                                  -Drepo.id=snapshots"
                        }
                    }
                }
            }
        }

        stage('CLI') {
            when { changeset "cli/*"}
            stages {
                stage('Verify'){
                    steps {
                        timeout(time: 5, unit: 'MINUTES') {
                            withSonarQubeEnv('SonarQube') {
                                sh "cd ./cli && mvn clean verify sonar:sonar \
                                      -Dsonar.projectKey=maven-jenkins-pipeline \
                                      -Dsonar.host.url=http://vmpx07.polytech.unice.fr:8001 \
                                      -Dsonar.login=${env.SONAR_AUTH_TOKEN}"
                            }
                        }
                    }
                }
                stage('Quality Gate'){
                    steps {
                        timeout(time: 10, unit: 'MINUTES') {
                            waitForQualityGate abortPipeline: true
                        }
                    }
                }
                stage('Build'){
                    steps {
                        timeout(time: 5, unit: 'MINUTES') {
                            sh "cd ./cli && mvn -s .m2/settings.xml deploy \
                                  -Drepo.id=snapshots"
                        }
                    }
                }
            }
        }

        stage("Notify") {
            options {
              timeout(time: 5, unit: 'MINUTES')   // timeout on this stage
            }
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
