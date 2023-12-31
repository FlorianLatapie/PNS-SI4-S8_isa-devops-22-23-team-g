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
        CLI_ARTIFACT_PATH = getArtifactPath('cli')
        BANK_ARTIFACT_PATH = getNodeArtifactPath('bank')
        BACKEND_VERSION = parseVersion(BACKEND_ARTIFACT_PATH)
        CLI_VERSION = parseVersion(CLI_ARTIFACT_PATH)
        BANK_VERSION = parseVersion(BANK_ARTIFACT_PATH)
        BACKEND_ARTIFACT_EXISTS = exists(BACKEND_ARTIFACT_PATH)
        CLI_ARTIFACT_EXISTS = exists(CLI_ARTIFACT_PATH)
        BANK_ARTIFACT_EXISTS = exists(BANK_ARTIFACT_PATH)
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
                sh "pwd"
                echo "Bank Artifact: ${BANK_ARTIFACT_PATH}"
                echo "Bank version: ${BANK_VERSION}"

            }
        }

        stage('Check Tag Trigger'){
            when { 
                expression { env.BRANCH_NAME =~ /^test[0-9]+\.[0-9]+\.[0-9]+/ }
            }
            steps {
                echo "Triggered by tag ${TAG_VERSION}"
            }
        }

        
        stage('Bank Tests & Linting'){
            steps {
                sh "pwd"
                sh "cd ./bank && npm ci"
                sh "cd ./bank && npm run lint"
                sh "cd ./bank && npm run test"
                sh "cd ./bank && npm run build"
                sh "cd ./bank/dist && ls"
                sh "cd ./bank && zip -r 'dist.zip' dist"
                sh "cd ./bank && jf rt u 'dist.zip' --url http://vmpx07.polytech.unice.fr:8002/artifactory/ --access-token ${ARTIFACTORY_ACCESS_TOKEN} generic-releases-local/fr/univ-cotedazur/bank/${BANK_VERSION}/"
                sh "cd ./bank && rm -rf dist"
                sh "cd ./bank && rm -rf 'dist.zip'"
                sh "cd ./bank && ls"
                downloadIfExistsNode('bank')

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
                moveArtifact('backend')
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
                moveArtifact('cli')
            }
        }

        stage('End2End Tests'){
            environment{
                TAG_VERSION = 'test0.0.1'
            }
            steps {
                timeout(time: 15, unit: 'MINUTES') {
                    echo "Building Backend Image ..."
                    sh "docker build --build-arg JAR_FILE=${BACKEND_VERSION}.jar -t teamgisadevops2023/backend:${TAG_VERSION} ./backend"
                    echo "Building CLI Image ..."
                    sh "cd ./cli && docker build --build-arg JAR_FILE=${CLI_VERSION}.jar -t teamgisadevops2023/cli:${TAG_VERSION} -f Dockerfile ."
                    echo "Building Bank Image ..."
                    sh "cd ./bank && ls && docker build -t teamgisadevops2023/bank:${TAG_VERSION} -f Dockerfile ."
                    echo "Start System"
                    sh "./End2End.sh"
                }
            }
        }

        // stage('Publish Artifactory'){
        //     steps {
        //         script {
        //             if ( CLI_ARTIFACT_EXISTS != 'true' ) {
        //                 sh "cd ./cli && mvn -s .m2/settings.xml deploy \
        //                     -Drepo.id=snapshots"
        //             }  
        //             if (BACKEND_ARTIFACT_EXISTS != 'true' ){
        //                 sh "cd ./backend && mvn -s .m2/settings.xml deploy \
        //                     -Drepo.id=snapshots"
        //             }
        //             if( BANK_ARTIFACT_EXISTS != 'true' ){
        //                 sh "cd ./bank && jf rt u '${BANK_VERSION}.zip' --url http://vmpx07.polytech.unice.fr:8002/artifactory/ --access-token ${ARTIFACTORY_ACCESS_TOKEN} generic-releases-local/fr/univ-cotedazur/bank/"
        //             }
        //         }
        //     }
        // }

        // stage('Publish DockerHub'){
        //     // when { 
        //     //     expression { env.BRANCH_NAME =~ /^[0-9]+\.[0-9]+\.[0-9]+/ }
        //     // }
        //     environment {
        //         TAG_VERSION = "test0.0.1"
        //     }
        //     steps {
        //         withDockerRegistry([ credentialsId: "DockerHub", url: ""]) {
        //             // sh "docker image tag teamgisadevops2023/backend:${TAG_VERSION}  teamgisadevops2023/backend:latest"
        //             // sh "docker image tag teamgisadevops2023/cli:${TAG_VERSION}  teamgisadevops2023/cli:latest"
        //             sh "docker push teamgisadevops2023/backend:${TAG_VERSION}"
        //             sh "docker push teamgisadevops2023/cli:${TAG_VERSION}"
        //             sh "docker push teamgisadevops2023/bank:${TAG_VERSION}"
        //             // sh "docker push teamgisadevops2023/backend:latest"
        //             // sh "docker push teamgisadevops2023/cli:latest"
        //             // sh "docker push teamgisadevops2023/bank:latest"
        //         }
        //         // withCredentials([usernamePassword(credentialsId: 'DockerHub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        //         //     sh "docker login -u ${USERNAME} -p ${PASSWORD} https://index.docker.io/v1/"
        //         //     sh "docker push teamgisadevops2023/backend:${TAG_VERSION}"
        //         //     sh "docker push teamgisadevops2023/cli:${TAG_VERSION}"
        //         //     sh "docker push teamgisadevops2023/bank:${TAG_VERSION}"
        //         // }
        //     }
        // }

        // stage('Deploy'){
        //     // when { 
        //     //     expression { env.BRANCH_NAME =~ /^[0-9]+\.[0-9]+\.[0-9]+/ }
        //     // }
        //     steps {
        //         sh "docker-compose -f docker-compose.prod.yml -p mfc-prod down"
        //         sh "docker-compose -f docker-compose.prod.yml -p mfc-prod up -d"
        //     }
        // }

        stage('Cleanup'){
            steps {
                sh "cd ./bank && jf rt del --url http://vmpx07.polytech.unice.fr:8002/artifactory/ --access-token ${ARTIFACTORY_ACCESS_TOKEN} generic-releases-local/fr/univ-cotedazur/bank/${BANK_VERSION}.zip"
                // delete('cli')
                // delete('backend')
                // withCredentials([usernamePassword(credentialsId: 'DockerHub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                //     script{
                //         def DOCKER_TOKEN = getDockerToken()
                //         sh("curl \"https://hub.docker.com/v2/repositories/teamgisadevops2023/backend/tags/${TAG_VERSION}\" -X DELETE -H \'Authorization: JWT ${DOCKER_TOKEN}\'")
                //         sh("curl \"https://hub.docker.com/v2/repositories/teamgisadevops2023/cli/tags/${TAG_VERSION}\" -X DELETE -H \'Authorization: JWT ${DOCKER_TOKEN}\'")
                //         sh("curl \"https://hub.docker.com/v2/repositories/teamgisadevops2023/bank/tags/${TAG_VERSION}\" -X DELETE -H \'Authorization: JWT ${DOCKER_TOKEN}\'")
                //     }
                // }
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
     if(env.BRANCH_NAME =~ /^test[0-9]+\.[0-9]+\.[0-9]+/) {
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


def delete(module){
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
    println("${artifactPath}")
    sh("jf rt del --url http://vmpx07.polytech.unice.fr:8002/artifactory/ --access-token ${ARTIFACTORY_ACCESS_TOKEN} --limit=1 libs-snapshot-local/${artifactPath}/*")
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
    sh("cd ./${module}/target && ls *.jar")
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

def getNodeArtifactPath(module) {
    def version =  sh (
          script: "cd ./${module} && npm pkg get version",
          returnStdout: true
    ).trim().replaceAll('"', '')
    return 'fr/univ-cotedazur/bank/' + version
}

def getDockerToken() {
    return sh (
        script: "curl -s -H \"Content-Type: application/json\" -X POST -H \"Content-Type: application/json\" -d '{\"username\":\"${USERNAME}\", \"password\":\"${PASSWORD}\"}' \"https://hub.docker.com/v2/users/login/\" | jq -r .token && curl \"https://hub.docker.com/v2/repositories/teamgisadevops2023/backend/tags/${TAG_VERSION}\"",
        returnStdout: true
    ).trim()
}

def downloadIfExistsNode(module){
    String artifactPath = ""
    String version = ""
    switch(module){
        case 'bank':
            artifactPath = BANK_ARTIFACT_PATH
            version = BANK_VERSION
            break
    }
    sh("jf rt dl --url http://vmpx07.polytech.unice.fr:8002/artifactory/ --access-token ${ARTIFACTORY_ACCESS_TOKEN} --limit=1 generic-releases-local/${artifactPath}/ ./${module}/")
    sh("ls")
    try {
        // sh("cd ./${module}/${artifactPath} && pwd && ls")
        sh("cd ./${module}/ && unzip ./${artifactPath}/dist.zip && ls")
        // sh("cd ./${module}/dist && pwd && ls")
    } catch (e) {
        echo "No artifact found in Artifactory"
    }
}
