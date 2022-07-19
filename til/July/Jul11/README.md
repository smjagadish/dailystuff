Autoscaling based on kafka metrics (external metrics) and prometheus adapter based external metrics server

* deploy kafka docker in /c/users/esmjaga/kafka_docker_ha repo ( pay attention to advertised listeners )
* deploy kafka metrics exporter with custom values file ( specify the right kafka server ports and enable service monitor for kafka with right set of labels
* deploy prometheus stack with service monitor label selector consistent with the svc monitor for kafka exporter

edit the crd prometheuses.monitoring.coreos.com to target the svc monitor label selector 

* deploy metrics server (i.e. k8s metrics server that will serve the external metrics req from hpa controller) 

kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

edit the deployment/ss to add --kubelet-insecure-tls startup arg

* deploy rbac for hpa and the hpa defintion (be sure to target the deployment to scale of your choice) 

* optionally run grafana docker to visualize the metrics 

docker run -d -p 3000:3000 --name grafana grafana/grafana-enterprise:8.2.0
