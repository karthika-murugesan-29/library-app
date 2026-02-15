pipeline {
  agent any

  environment {
    MVN_OPTS = '-B'
  }

  stages {
    stage('Checkout') {
      steps {
        // explicitly clone the repo because this job is not using "Pipeline script from SCM"
        git branch: 'master', url: 'https://github.com/karthika-murugesan-29/library-app.git'
      }
    }

    stage('Build & Test') {
      steps {
        sh "mvn ${MVN_OPTS} clean test"
      }
    }

    stage('Generate JaCoCo Report') {
      steps {
        // generate HTML coverage report (jaCoCo plugin configured in pom.xml)
        sh "mvn ${MVN_OPTS} jacoco:report || true"
      }
    }

    stage('Publish Test Results') {
      steps {
        junit '**/target/surefire-reports/*.xml'
        // Archive the generated JaCoCo HTML site so it can be inspected from the build artifacts
        archiveArtifacts artifacts: 'target/site/jacoco/**', allowEmptyArchive: true
      }
    }

    stage('Sonar Analysis') {
      steps {
        withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
          sh "mvn ${MVN_OPTS} sonar:sonar -Dsonar.login=${SONAR_TOKEN} -Dsonar.host.url=$SONAR_HOST_URL"
        }
      }
    }

    stage('Optional: Publish HTML (if plugin available)') {
      steps {
        script {
          try {
            publishHTML([
              reportDir: 'target/site/jacoco',
              reportFiles: 'index.html',
              reportName: 'JaCoCo Coverage',
              allowMissing: true,
              alwaysLinkToLastBuild: true,
              keepAll: true
            ])
          } catch (err) {
            echo "publishHTML step failed or plugin missing: ${err}"
          }
        }
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'target/site/jacoco/**', allowEmptyArchive: true
    }
    success {
      echo 'Jenkins pipeline finished: SUCCESS'
    }
    failure {
      echo 'Jenkins pipeline finished: FAILURE'
    }
  }
}
