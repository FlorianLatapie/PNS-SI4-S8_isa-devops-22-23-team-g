pipeline {
    agent {
      docker { image 'ci/pipeline' }
    }

    environment {
        BANK_ARTIFACT_PATH = getNodeArtifactPath('bank')
        BANK_ARTIFACT_EXISTS = exists(BANK_ARTIFACT_PATH)
        BANK_VERSION = parseVersion(BANK_ARTIFACT_PATH)
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
                sh "cd ./bank && npm ci"
            }
        }

        stage('Linting'){
            steps {
                sh "cd ./bank && npm run lint"
            }
        }

        stage('Tests'){
            steps {
                sh 'cd ./bank && npm run test'
            }
        }

        stage('Verify version number') {
            when{
                allOf {
                    expression { env.CHANGE_ID != null } 
                    expression { BANK_ARTIFACT_EXISTS == 'true' }
                }
            }
            steps {
                error(
                    "BANK artifact with version ${BANK_VERSION} already exists."
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
    return 'fr/univ-cotedazur/bank/' + version
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
    return 'fr/univ-cotedazur/bank/' + version
}