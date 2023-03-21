pipeline {
    agent {
      docker { image 'ci/maven.artifactory' }
    }

    environment {
        COMMITER_NAME = sh (
              script: 'git show -s --pretty=\'%an\'',
              returnStdout: true
        ).trim()
        WAS_TRIGERED = "false"
    }

    stages {
        stage('Prepare') {
            when { 
                anyOf{
                    changeset "backend/*" 
                    expression { env.BRANCH_NAME =~ /^v.\d+\.\d+\.\d+/ }
                }
            }
            environment {
                WAS_TRIGERED = "true"
            }
            options {
              timeout(time: 5, unit: 'MINUTES')   // timeout on this stage
            }
            steps {
                echo 'Pulling...' + env.BRANCH_NAME
                checkout scm
            }
        }

        stage('Deploy'){
            when { 
                expression { env.BRANCH_NAME =~ /^v.\d+\.\d+\.\d+/ }
            }
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    sh "cd ./backend && mvn -s .m2/settings.xml deploy \
                          -Drepo.id=snapshots"
                }
            }
        }
    }
}
