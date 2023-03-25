pipeline {
    agent {
      docker { 
            image 'ci/pipeline'
            args '--privileged  -v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    environment {
        COMMITER_NAME = sh (
              script: 'git show -s --pretty=\'%an\'',
              returnStdout: true
        ).trim()
        BACKEND_ARTIFACT_PATH = getArtifactPath('backend')
        BACKEND_VERSION = parseVersion(BACKEND_ARTIFACT_PATH)
        BACKEND_ARTIFACT_EXISTS = exists(BACKEND_ARTIFACT_PATH)
        CLI_ARTIFACT_PATH = getArtifactPath('cli')
        CLI_VERSION = parseVersion(CLI_ARTIFACT_PATH)
        CLI_ARTIFACT_EXISTS = exists(CLI_ARTIFACT_PATH)
        BANK_ARTIFACT_PATH = getNodeArtifactPath('bank')
        BANK_ARTIFACT_EXISTS = exists(BANK_ARTIFACT_PATH)
        BANK_VERSION = parseVersion(BANK_ARTIFACT_PATH)
        ARTIFACTORY_ACCESS_TOKEN = credentials('artifactory-access-token')
        TAG_VERSION = getTag()
    }

    stages {
        stage('Prepare') {
            options {
              timeout(time: 5, unit: 'MINUTES')   // timeout on this stage
            }
            steps {
                echo 'Pulling...' + env.BRANCH_NAME
                checkout scm
                downloadIfExists('backend')
                downloadIfExists('cli')
                downloadIfExistsNode('bank')
            }
        }

        stage('End2End Tests'){
            steps {
                timeout(time: 15, unit: 'MINUTES') {
                    echo "Building Backend Image ..."
                    sh "docker build --build-arg JAR_FILE=${BACKEND_VERSION}.jar -t teamgisadevops2023/backend:${TAG_VERSION} ./backend"
                    echo "Building CLI Image ..."
                    sh "cd ./cli && docker build --build-arg JAR_FILE=${CLI_VERSION}.jar -t teamgisadevops2023/cli${TAG_VERSION} -f Dockerfile ."
                    echo "Building Bank Image ..."
                    sh "cd ./bank && docker build -t teamgisadevops2023/bank${TAG_VERSION} -f Dockerfile ."
                    echo "Start System"
                    sh "./End2End.sh"
                }
            }
        }

        stage('Publish DockerHub'){
            when { 
                expression { env.BRANCH_NAME =~ /^[0-9]+\.[0-9]+\.[0-9]+/ }
            }
            steps {
                withDockerRegistry([ credentialsId: "DockerHub", url: ""]) {
                    sh "docker push teamgisadevops2023/backend:${TAG_VERSION}"
                    sh "docker push teamgisadevops2023/cli:${TAG_VERSION}"
                    sh "docker push teamgisadevops2023/bank:${TAG_VERSION}"

                    sh "docker push teamgisadevops2023/backend:latest"
                    sh "docker push teamgisadevops2023/cli:latest"
                    sh "docker push teamgisadevops2023/bank:latest"
                }
            }
        }
        
        stage('Deploy'){
            when { 
                expression { env.BRANCH_NAME =~ /^[0-9]+\.[0-9]+\.[0-9]+/ }
            }
            steps {
                sh "docker-compose -f docker-compose.prod.yml -p mfc-prod down"
                sh "docker-compose -f docker-compose.prod.yml -p mfc-prod pull"
                sh "docker-compose -f docker-compose.prod.yml -p mfc-prod up -d"
            }
        }
    }

    post {
        always {
            notify(currentBuild.currentResult)
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

def getArtifactPath(module) {
    def inputString =  sh (
          script: "cd ${module} && mvn -q -Dexec.executable=echo -Dexec.args='\${project.groupId}/\${project.artifactId}/\${project.version}' --non-recursive exec:exec 2>/dev/null",
          returnStdout: true
    ).trim()
    def version = inputString.substring(inputString.lastIndexOf('/') + 1)
    def remainingString = inputString.substring(0, inputString.lastIndexOf('/') + 1)
    return remainingString.replaceAll(/\./, '/') + version
}


def getNodeArtifactPath(module) {
    def version =  sh (
          script: "cd ./${module} && npm pkg get version",
          returnStdout: true
    ).trim().replaceAll('"', '')
    return 'fr/univ-cotedazur/bank/' + version
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

def getTag() {
     if(env.BRANCH_NAME =~ /^.[0-9]+\.[0-9]+\.[0-9]+/) {
         return env.BRANCH_NAME
     }
     return ""
}

def downloadIfExists(module){
    String path = ""
    String artifactPath = ""
    String version = ""
    switch(module){
        case 'backend':
            artifactPath = BACKEND_ARTIFACT_PATH
            version = BACKEND_VERSION
            path = "backend/target/${BACKEND_VERSION}.jar"
            break
        case 'cli':
            artifactPath = CLI_ARTIFACT_PATH
            version = CLI_VERSION
            path = "./cli/target/${CLI_VERSION}.jar"
            break 
    }
    sh("jf rt dl --url http://vmpx07.polytech.unice.fr:8002/artifactory/ --access-token ${ARTIFACTORY_ACCESS_TOKEN} --limit=1 libs-snapshot-local/${artifactPath}/ ./${module}/target/")
    try {
        sh("cd ./${module}/target/${artifactPath} && pwd && ls")
        sh("mv ./${module}/target/${artifactPath}/*.jar ./${module}/target/ && cd ./${module}/target && ls *.jar |grep '^${version}.jar\$' || mv `ls *.jar | head -1` ${version}.jar")
        sh("cd ./${module}/target && pwd && ls")
    } catch (e) {
        echo "No artifact found in Artifactory"
    }
}

def moveArtifact(module){
    String version = ""
    switch(module){
        case 'backend':
            version = BACKEND_VERSION
            break
        case 'cli':
            version = CLI_VERSION
            break 
    }
    sh("cd ./${module}/target && ls *.jar |grep '^${version}.jar\$' || mv `ls *.jar | head -1` ${version}.jar")
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

def downloadIfExistsNode(module){
    String path = ""
    String artifactPath = ""
    String version = ""
    switch(module){
        case 'bank':
            artifactPath = BANK_ARTIFACT_PATH
            version = BANK_VERSION
            path = "bank/${BACKEND_VERSION}.zip"
            break
    }
    sh("jf rt dl --url http://vmpx07.polytech.unice.fr:8002/artifactory/ --access-token ${ARTIFACTORY_ACCESS_TOKEN} --limit=1 libs-snapshot-local/${artifactPath}/ ./${module}/")
    try {
        sh("cd ./${module}/ && unzip ${version}.zip && ls")
    } catch (e) {
        echo "No artifact found in Artifactory"
    }
}
