pipeline {
    agent {
      docker { image 'ci/pipeline' }
    }

    environment {
        BACKEND_ARTIFACT_PATH = getArtifactPath('backend')
        BACKEND_ARTIFACT_EXISTS = exists(BACKEND_ARTIFACT_PATH)
        BACKEND_VERSION = parseVersion(BACKEND_ARTIFACT_PATH)
        BANK_ARTIFACT_PATH = getNodeArtifactPath('bank')
        BANK_ARTIFACT_EXISTS = exists(BANK_ARTIFACT_PATH)
        BANK_VERSION = parseVersion(BANK_ARTIFACT_PATH)
        PARKING_ARTIFACT_PATH = getNodeArtifactPath('parking')
        PARKING_ARTIFACT_EXISTS = exists(PARKING_ARTIFACT_PATH)
        PARKING_VERSION = parseVersion(PARKING_ARTIFACT_PATH)
        CLI_ARTIFACT_PATH = getArtifactPath('cli')
        CLI_VERSION = parseVersion(CLI_ARTIFACT_PATH)
        CLI_ARTIFACT_EXISTS = exists(CLI_ARTIFACT_PATH)
        ARTIFACTORY_ACCESS_TOKEN = credentials('artifactory-access-token')
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

        
        stage('Backend Unit & Integration Tests'){
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    withSonarQubeEnv('SonarQube') {
                        sh "cd ./backend && mvn clean package sonar:sonar \
                              -Dsonar.projectKey=mfc-backend \
                              -Dsonar.host.url=http://vmpx07.polytech.unice.fr:8001 \
                              -Dsonar.login=${env.SONAR_AUTH_TOKEN}"
                    }
                }
                sh "cd ./backend && mvn verify"
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('CLI Unit & Integration Tests'){
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    withSonarQubeEnv('SonarQube') {
                        sh "cd ./cli && mvn clean package sonar:sonar \
                              -Dsonar.projectKey=mfc-cli \
                              -Dsonar.host.url=http://vmpx07.polytech.unice.fr:8001 \
                              -Dsonar.login=${env.SONAR_AUTH_TOKEN}"
                    }
                }
                sh "cd ./cli && mvn verify"
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Bank Tests & Linting'){
            steps {
                sh "cd ./bank && npm ci"
                sh "cd ./bank && npm run lint"
                sh "cd ./bank && npm run test"
            }
        }

        stage('Parking Tests & Linting'){
            steps {
                sh "cd ./parking && npm ci"
                sh "cd ./parking && npm run lint" 
                sh "cd ./parking && npm run test"
            }
        }

    }
}

def getArtifactPath(module) {
    def inputString =  sh (
          script: "cd ${module} && mvn -q -Dexec.executable=echo -Dexec.args='\${project.groupId}/\${project.artifactId}/\${project.version}' --non-recursive exec:exec 2>/dev/null",
          returnStdout: true
    ).trim()
    def version = inputString.substring(inputString.lastIndexOf('/') + 1)
    def remainingString = inputString.substring(0, inputString.lastIndexOf('/') + 1)
    return remainingString.replaceAll(/\./, '/') + version
}

def parseVersion(artifactPath) {
    return artifactPath.substring(artifactPath.lastIndexOf('/') + 1)
}

def exists(artifactPath) {
    return sh (
        script: "jf rt s --url http://vmpx07.polytech.unice.fr:8002/artifactory/ --access-token ${ARTIFACTORY_ACCESS_TOKEN} libs-snapshot-local/${artifactPath}/ --count",
        returnStdout: true
    ).trim().toInteger() != 0
}

def getNodeArtifactPath(module) {
    def version =  sh (
          script: "cd ./${module} && npm pkg get version",
          returnStdout: true
    ).trim().replaceAll('"', '')
    return "fr/univ-cotedazur/${module}/" + version
}
