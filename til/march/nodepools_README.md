AKS mandates one system node pool be present at all times
multiple agent and/or system node pools can be created

pool creation/association can be at cluster creation time or at a later stage 

cmd: az aks nodepool add --name mynodepool --resource-group esmjaga-playground --cluster-name vkubelet

pools can be scaled up/down as well as started up/down and even deleted (stoppage/deletion not possible for system pools) - needs preview feature enabling

cmd:  az aks nodepool stop --nodepool-name mynodepool --resource-group esmjaga-playground --cluster-name vkubelet
cmd:  az aks nodepool start --nodepool-name mynodepool --resource-group esmjaga-playground --cluster-name vkubelet
cmd:  az aks nodepool delete --name mynodepool --resource-group esmjaga-playground --cluster-name vkubelet

pools can have custom kubelet config and/or os configuration during creation time - needs preview feature enabling 

cmd: az aks nodepool add --name mynodepool --resource-group esmjaga-playground --cluster-name vkubelet --kubelet-config kubeletconfig.json
