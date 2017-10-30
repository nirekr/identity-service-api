UPSTREAM_TRIGGERS = [
    "common-client-parent",
    "common-dependencies",
    "common-messaging-parent"
]
properties(getBuildProperties(upstreamRepos: UPSTREAM_TRIGGERS))

pipeline {    
    agent {
        node {
            label 'maven-builder'
            customWorkspace "workspace/${env.JOB_NAME}"
        }
    }
    environment {
        GITHUB_TOKEN = credentials('git-02')
    }
    options { 
        skipDefaultCheckout()
        timestamps()
    }
    tools {
        maven 'linux-maven-3.3.9'
        jdk 'linux-jdk1.8.0_102'
    }
    stages {
        stage('Checkout') {
            steps {
                doCheckout()
            }
        }
        stage('Build') {
            steps {
                script {
                    if (env.BRANCH_NAME ==~ /master|stable\/.*/) {
                        sh "mvn clean deploy -Dmaven.repo.local=.repo"
                    } else {
                        sh "mvn clean install -Dmaven.repo.local=.repo"
                    }
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                doSonarAnalysis()    
            }
        }
        stage('Third Party Audit') {
            steps {
                doThirdPartyAudit()
            }
        }
        stage('Github Release') {
            steps {
                githubRelease()
            }
        }
        stage('NexB Scan') {
            steps {
                doNexbScanning()
            }
        }
        stage('PasswordScan') {
            steps {
                doPwScan()
            }
        }
    }
    post {
        always {
            cleanWorkspace()   
        }
        success {
            successEmail()
        }
        failure {
            failureEmail()
        }
    }
}
