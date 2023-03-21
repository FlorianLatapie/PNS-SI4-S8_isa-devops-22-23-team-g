pipeline {
    agent {
      docker { image 'ci/maven.artifactory' }
    }

    environment {
        BACKEND_ARTIFACT_PATH = getArtifactPath('backend')
        BACKEND_ARTIFACT_EXISTS = exists(BACKEND_ARTIFACT_PATH)
        BACKEND_VERSION = parseVersion(BACKEND_ARTIFACT_PATH)
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

        stage('Unit Tests'){
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    withSonarQubeEnv('SonarQube') {
                        sh "cd ./backend && mvn clean package sonar:sonar \
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

        stage('Integration Tests'){
            when { expression { env.CHANGE_ID != null } }
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    sh "cd ./backend && mvn verify"
                }
            }
        }

        stage('Verify version number') {
            when{
                allOf {
                    expression { env.CHANGE_ID != null } 
                    expression { BACKEND_ARTIFACT_EXISTS == 'true' }
                }
            }
            steps{
                error(
                    "BACKEND artifact with version ${BACKEND_VERSION} already exists."
                )
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