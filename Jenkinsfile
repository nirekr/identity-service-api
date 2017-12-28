UPSTREAM_TRIGGERS = [
    "common-client-parent",
    "common-dependencies",
    "common-messaging-parent"
]
properties(getBuildProperties(upstreamRepos: UPSTREAM_TRIGGERS))

pipeline {
    parameters {
        choice(choices: 'OFF\nON', description: 'Please select appropriate flag (master and stable branches will always be ON)', name: 'Deploy_Stage')
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
        stage('Fortify Scan') { 
         steps { 
              runFortifyScan() 
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
                def call() {
				    def nFlag = "${params.Deploy_Stage}"
				    if (env.BRANCH_NAME ==~ /master|q3stable|stable|feature_ests_.*/) {
				        nFlag = "ON"
				    }
				    
				    echo "Deploying identity-service-api jar"
				    
				    switch (nFlag) {
				        case "ON":       
				            if (env.BRANCH_NAME ==~ /stable.*/) {
				                withCredentials([string(credentialsId: 'GPG-Dell-Key', variable: 'GPG_PASSPHRASE')]) {
				                    if (params.dockerRegistry) {
				                        sh "mvn deploy -Dmaven.repo.local=.repo -DskipTests=true -DskipITs=true -Ppublish-release -Dgpg.passphrase=${GPG_PASSPHRASE} -Dgpg.keyname=73BD7C5F -DskipJavadoc=false -DskipJavasource=false -Dexec.skip=true -Dskip=true -DskipDockerBuild=true -Ddocker.registry=${params.dockerRegistry} -DdockerImage.tag=${params.dockerImageTag}"
				                    } else {
				                        sh "mvn deploy -Dmaven.repo.local=.repo -DskipTests=true -DskipITs=true -Ppublish-release -Dgpg.passphrase=${GPG_PASSPHRASE} -Dgpg.keyname=73BD7C5F -DskipJavadoc=false -DskipJavasource=false -Dexec.skip=true -Dskip=true -DskipDockerBuild=true"
				                    }
				                }
				            } else if (params.dockerRegistry) {
				                sh "mvn deploy -Dmaven.repo.local=.repo -DskipTests=true -DskipITs=true -Dexec.skip=true -Dskip=true -DskipDockerBuild=true -Ddocker.registry=${params.dockerRegistry} -DdockerImage.tag=${params.dockerImageTag}"
				            } else {
				                sh "mvn deploy -Dmaven.repo.local=.repo -DskipTests=true -DskipITs=true -Dexec.skip=true -Dskip=true -DskipDockerBuild=true"
				            }
				            break
				        default:
				            echo "Skipping Deploy Stage: Branch -> ${env.BRANCH_NAME} Selected Flag -> ${nFlag}"
				            break
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
