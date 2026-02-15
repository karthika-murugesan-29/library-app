pipeline {
 agent any
  environment { MVN_OPTS = '-B' }
  stages {
    stage('Checkout') {
      steps {
        // explicitly clone the repo because this job is not using "Pipeline script from SCM"
        git branch: 'master', url: 'https://github.com/karthika-murugesan-29/library-app.git'
      }
    }

    stage('Build & Test') {
      steps {
        sh "mvn ${MVN_OPTS} clean verify"
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
        script {
          if (env.SONAR_TOKEN) {
            sh "mvn ${MVN_OPTS} sonar:sonar -Dsonar.login=${env.SONAR_TOKEN} -Dsonar.host.url=${env.SONAR_HOST_URL ?: 'http://localhost:9000'}"
          } else {
            echo 'SONAR_TOKEN not found; skipping Sonar analysis. Add a Jenkins credential with id SONAR_TOKEN or set SONAR_TOKEN env var.'
          }
        }
      }
    }

    stage('Optional: Publish HTML (if plugin available)') {
      steps {
        script {
          try {

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
