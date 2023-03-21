pipeline {
    agent {
      docker { image 'ci/maven.artifactory' }
    }

    environment {
        BACKEND_VERSION = getVersion('backend')
        CLI_VERSION = getVersion('cli')
        BACKEND_ARTIFACT_EXISTS = exists(BACKEND_VERSION)
        CLI_ARTIFACT_EXISTS = exists(CLI_VERSION)
        ARTIFACTORY_ACCESS_TOKEN = credentials('artifactory-access-token')
    }

    stages {

        stage('Prepare') {
            steps {
                echo 'Pulling...' + env.BRANCH_NAME
                checkout scm
                sh "cd ./backend"
                echo "Backend version: ${BACKEND_VERSION}"
                echo "Cli version: ${CLI_VERSION}"
                echo "Backend exists: ${BACKEND_ARTIFACT_EXISTS}"
                echo "CLI exists: ${CLI_ARTIFACT_EXISTS}"
            }
        }
    }
}

def getVersion(module) {
    def inputString =  sh (
          script: "cd ${module} && mvn -q -Dexec.executable=echo -Dexec.args='\${project.groupId}/\${project.artifactId}/\${project.version}' --non-recursive exec:exec 2>/dev/null",
          returnStdout: true
    ).trim()
    def version = inputString.substring(inputString.lastIndexOf('/') + 1)
    def remainingString = inputString.substring(0, inputString.lastIndexOf('/') + 1)
    return remainingString.replaceAll(/\./, '/') + version
}

def exists(artifactPath) {
    return sh (
        script: "jf rt s --url http://vmpx07.polytech.unice.fr:8002/artifactory/ --access-token ${ARTIFACTORY_ACCESS_TOKEN} libs-snapshot-local/${artifactPath}/ --count",
        returnStdout: true
    ).trim().toInteger() != 0
}


def hasChangesIn(module) {
    currentBuild.description = currentBuild.previousBuild.description
    boolean hasChangesInModule = hasChangesIn(currentBuild, module)
    if(hasChangesInModule && currentBuild.description && !currentBuild.description.match(module)) {
        currentBuild.description += ' ' + module
    }
    return hasChangesInModule
}

def hasChangesIn(activeBuild, module) {
    if(!activeBuild) return false
    if(activeBuild.description && activeBuild.description.match(module)) return true
    def changeSets = activeBuild.changeSets
    for (int i = 0; i < changeSets.size(); i++) {
        def entries = changeSets[i].items
        for (int j = 0; j < entries.length; j++) {
            def entry = entries[j]
            def files = new ArrayList(entry.affectedFiles)
            for (int k = 0; k < files.size(); k++) {
                def file = files[k]
                if (file.path.startsWith(module)) {
                    return true
                }
                echo "  ${file.editType.name} ${file.path}"
            }
        }
    }
    return hasChangesIn(activeBuild.previousBuild, module)
}