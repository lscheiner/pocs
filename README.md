docker image build -t scheiner-prometheus .
docker tag scheiner-prometheus lscheiner/scheiner-prometheus:v1
docker push lscheiner/scheiner-prometheus:v1

kubectl create namespace java-prometheus
kubectl apply -f deploy-java.yaml
kubectl apply -f keda-java.yaml
kubectl apply -f service-java.yaml

kubectl port-forward service/java-prometheus-service 9191:9090 -n java-prometheus