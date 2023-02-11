pipeline {
  agent {
    docker { image 'maven:3.8.7-eclipse-temurin-17' }
  }
  stages {
    stage('TestMaven') {
      steps {
        sh 'mvn --version'
      }
    }
  }
}