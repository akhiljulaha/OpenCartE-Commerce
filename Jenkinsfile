pipeline { 
    
    agent any 
   tools {
    maven 'maven'
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
                echo("deploy to qa")
            }
        }

        stage("Regression automation test") {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/akhiljulaha/OpenCartE-Commerce'
                    bat "mvn clean test -DsuiteXmlFile=src/main/resources/testrunners/testng_regression.xml"
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
                    reportName: 'HTML Regression Extent Report',
                    reportTitles: ''
                ])
            }
        }

        stage("Deploy to stage") {
            steps {
                echo("deploy to stage")
            }
        }

        stage("Sanity automation test") {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/akhiljulaha/OpenCartE-Commerce'
                    bat "mvn clean test -DsuiteXmlFile=src/main/resources/testrunners/testng_chrome.xml"
                }
            }
        }

        stage('Publish sanity Extent Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: 'TestExecutionReport.html',
                    reportName: 'HTML sanity Extent Report',
                    reportTitles: ''
                ])
            }
        }

    } 
}
