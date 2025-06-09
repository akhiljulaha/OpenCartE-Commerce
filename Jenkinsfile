pipeline {
    agent any
    tools {
        maven 'maven'
    }

    parameters {
        choice(name: 'SUITE', choices: ['testng_regression.xml', 'testng_chrome.xml'], description: 'Choose the test suite to run')
    }

    stages {

        stage("Build") {
            steps {
                git 'https://github.com/jglick/simple-maven-project-with-tests'
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        stage("Deploy to qa") {
            steps {
                echo("Deploying to QA...")
            }
        }

        stage("Run Selected Automation Suite") {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/akhiljulaha/OpenCartE-Commerce'
                    bat "mvn clean test -DsuiteXmlFile=src/main/resources/testrunners/${params.SUITE}"
                }
            }
        }

        stage('Publish Allure Reports') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }

        stage('Publish Extent Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'TestExecutionReport.html',
                    reportName: 'HTML Test Report',
                    reportTitles: ''
                ])
            }
        }

    }
}
