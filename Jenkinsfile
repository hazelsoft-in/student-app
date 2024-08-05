pipeline {
   agent any

stages {

    stage("Git Clone"){

        git credentialsId: 'GIT_HUB_CREDENTIALS', url: 'https://github.com/hazelsoft-in/student-app.git'
    }
    
    stage('Gradle Build') {

       sh './gradlew clean build'

    }
    
    stage("Docker build"){
        sh 'docker version'
        sh 'docker build -t student-app .'
        sh 'docker image list'
        sh 'docker tag student-app sandeeplv/student-app:v30'
    }
    
    stage("Docker Login"){
        withCredentials([string(credentialsId: 'DOCKER_HUB_PASSWORD', variable: 'PASSWORD')]) {
            sh 'docker login -u sandeeplv -p $PASSWORD'
        }
    }
    
    stage("Push Image to Docker Hub"){
        sh 'docker push sandeeplv/student-app:v30'
    }
    
    stage("K8 Deploy") {
        
       sh 'kubectl apply -f deploy.yaml'
        
    }
 }
    
    
}