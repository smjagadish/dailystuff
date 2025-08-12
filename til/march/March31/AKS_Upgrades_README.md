AKS upgrades can be control plane only or control plane + workers

cmd:  az aks upgrade --resource-group esmjaga-playground --name vkubelet --kubernetes-version 1.23.3 --control-plane-only
cmd:  az aks upgrade --resource-group esmjaga-playground --name vkubelet --kubernetes-version 1.23.3

get-upgrades can be executed to find candidate upgrade and determine upgrade paths

cmd : az aks get-upgrades --name vkubelet --resource-group esmjaga-playground

upgrades follow surge-drain-join cycle
surge can be controlled through % or count at cluster creation or on existing clusters

upgrade of 'node os' image alone is possible through --node-only parameter for control plane + workers

cmd: az aks nodepool upgrade --resource-group esmjaga-playground --cluster-name vkubelet --name mynodepool --node-image-only
cmd: az aks upgrade --resource-group esmjaga-playground --name vkubelet --control-plane-only --node-image-only
cmd: az aks nodepool upgrade --resource-group esmjaga-playground --cluster-name vkubelet --name agentpool --kubernetes-version 1.23.3

node pools can be mix and match technically , although this is not recommended (eg: np1 in k8s1.22 with ctrl plane wnad np2 in k8s 1.21)

