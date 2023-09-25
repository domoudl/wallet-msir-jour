pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage("Source") {
            steps {
                git branch: 'main', url: 'https://github.com/domoudl/wallet-msir-jour'
            }
        }
        stage("Build") {
            steps {
                bat 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install'
            }
        }
        stage("SonarQube Analysis") {
            steps {
                bat 'mvn sonar:sonar'
            }
        }
        stage('Docker Build') {
            steps {
                script {
                    def dockerImageName = 'wallet-msir-jour-groupe1'
                    def dockerTag = 'latest'
                    bat "docker build -t ${dockerImageName}:${dockerTag} ."
                }
            }
        }
        stage('Approve Deployment') {
            input {
                message "Do you want to proceed for deployment?"
            }
            steps {
                 bat 'echo "Deploying into Server"'
            }
        }
    }
    post {
        aborted {
            echo "Sending message to agent"
        }
        failure {
            echo "Sending message to agent"
        }
        success {
            echo "Sending message to agent"
        }
    }
}
