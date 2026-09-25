pipeline {
    agent any

    environment {
        DOCKERHUB_USER = 'manediarra'
        IMAGE_NAME     = 'backend-devops'
        IMAGE_TAG      = "v${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo '📥 Récupération du code depuis GitHub'
                git branch: 'main',
                    url: 'https://github.com/gninediarra/DevOps-AppGestionDesProjets.git',
                    credentialsId: 'github-token'
            }
        }

        stage('Build JAR') {
            steps {
                echo '🔨 Compilation du backend Spring Boot'
                dir('backend') {
                    sh 'chmod +x mvnw && ./mvnw clean package -DskipTests'
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo '🐳 Construction de l\'image Docker'
                sh "docker build -t ${DOCKERHUB_USER}/${IMAGE_NAME}:${IMAGE_TAG} ./backend"
                sh "docker tag ${DOCKERHUB_USER}/${IMAGE_NAME}:${IMAGE_TAG} ${DOCKERHUB_USER}/${IMAGE_NAME}:latest"
            }
        }

                stage('Docker Push') {
            steps {
                echo '🚀 Push de l\'image sur DockerHub'
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                        echo "Debug: user=$DOCKER_USER, pass length=${#DOCKER_PASS}"
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push $DOCKERHUB_USER/backend-devops:$IMAGE_TAG
                        docker push $DOCKERHUB_USER/backend-devops:latest
                    '''
                }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline terminé avec succès'
        }
        failure {
            echo '❌ Pipeline échoué'
        }
    }
}
