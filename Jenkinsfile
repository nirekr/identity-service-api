UPSTREAM_TRIGGERS = [
    "common-client-parent",
    "common-dependencies",
    "common-messaging-parent"
]
properties(getBuildProperties(upstreamRepos: UPSTREAM_TRIGGERS))

pipeline {
    parameters {
        choice(choices: 'ON\nOFF', description: 'Please select appropriate flag', name: 'Deploy_Stage')
    }
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
        stage('.travis.yml Validation') {
            steps {
                doTravisLint()
            }
        }
        stage('Compile') {
            steps {
                sh "mvn clean install -Dmaven.repo.local=.repo -DskipTests=true -DskipITs=true"
            }
        }
        stage('Unit Testing') {
            steps {
                sh "mvn verify -Dmaven.repo.local=.repo"
            }
        }
        stage('Record Test Results') {
            steps {
                junit '**/target/*-reports/*.xml'
            }
        }
        stage('PasswordScan') {
            steps {
                doPwScan()
            }
        }
        stage('Deploy') {
            steps {
                doMvnDeploy()
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
        stage('Badge Check'){
            steps{
                doOpenSourceCheck()
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
