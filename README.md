./helm uninstall prometheus

https://koudingspawn.de/spring-boot-cloud-ready-part-ii/
https://blog.devops.dev/monitoring-a-spring-boot-application-in-kubernetes-with-prometheus-a2d4ec7f9922

./helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
./helm repo update
./helm install -f prometheus-values.yaml prometheus prometheus-community/kube-prometheus-stack --namespace prometheus --create-namespace

kubectl get pod
kubectl describe pod prometheus-grafana-d5679d5d7-k4n9f
kubectl get secret  prometheus-grafana -oyaml         "prometheus-grafana é o nome da secret admin/prom-operator"


----------------------------------------------------------------------
docker image build -t scheiner-prometheus .
docker tag scheiner-prometheus lscheiner/scheiner-prometheus:v1
docker push lscheiner/scheiner-prometheus:v1

kubectl create namespace java-prometheus
kubectl apply -f deploy-java.yaml
kubectl apply -f keda-java.yaml
kubectl apply -f service-java.yaml

kubectl port-forward service/java-prometheus-service 9292:8080 -n java-prometheus
kubectl port-forward service/prometheus-operated 9393:9090 -n prometheus
kubectl port-forward svc/prometheus-grafana 9999:80 -n prometheus


http://localhost:9393/

query exemplo:

rate(http_server_requests_seconds_count{application="java-prometheus-service"}[2m])



RequestResponseBodyMethodProcessor
HandlerMethodArgumentResolverComposite -- linha 117
InvocableHandlerMethod  -  linha 207
RequestResponseBodyMethodProcessor -- linha 159
readWithMessageConverters -- linha 186

curl -X 'POST' \
  'http://localhost:8080/produtos' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json'
