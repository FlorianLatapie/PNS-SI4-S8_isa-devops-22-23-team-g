pipeline {
    agent {
      docker { image 'ci/pipeline' }
    }

    environment {
        PARKING_ARTIFACT_PATH = getNodeArtifactPath('parking')
        PARKING_ARTIFACT_EXISTS = exists(PARKING_ARTIFACT_PATH)
        PARKING_VERSION = parseVersion(PARKING_ARTIFACT_PATH)
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
                sh "cd ./parking && npm ci"
            }
        }

        stage('Linting'){
            steps {
                sh "cd ./parking && npm run lint"
            }
        }

        stage('Tests'){
            steps {
                sh 'cd ./parking && npm run test'
            }
        }

        stage('Verify version number') {
            when{
                allOf {
                    expression { env.CHANGE_ID != null } 
                    expression { PARKING_ARTIFACT_EXISTS == 'true' }
                }
            }
            steps {
                error(
                    "PARKING artifact with version ${PARKING_VERSION} already exists."
                )
            }
        }
    }
}

def getArtifactPath(module) {
    def version =  sh (
          script: "cd ${module} -p \"require('./package.json').version\"",
          returnStdout: true
    ).trim()
    return 'fr/univ-cotedazur/parking/' + version
}

def parseVersion(artifactPath) {
    return artifactPath.substring(artifactPath.lastIndexOf('/') + 1)
}

def exists(artifactPath) {
    return sh (
        script: "jf rt s --url http://vmpx07.polytech.unice.fr:8002/artifactory/ --access-token ${ARTIFACTORY_ACCESS_TOKEN} generic-releases-local/${artifactPath}/ --count",
        returnStdout: true
    ).trim().toInteger() != 0
}

def getNodeArtifactPath(module) {
    def version =  sh (
          script: "cd ./${module} && npm pkg get version",
          returnStdout: true
    ).trim().replaceAll('"', '')
    return 'fr/univ-cotedazur/parking/' + version
}