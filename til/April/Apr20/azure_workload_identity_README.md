Rationale : To access azure services and/or host access info for external services within azure resources WITHOUT any secrets management at workload level
Realization : using azure workload identities - akin to a managed identity but tight integration with native K8s concepts such as service accounts 

Pre-requisites : 
AKS cluster setup with oidc issuer URL <https://docs.microsoft.com/en-us/azure/aks/cluster-configuration#oidc-issuer-preview>

High-level workflow :
1) create a service principal and do resource cum role assignments for the same . The resource/role assignments is in relation to the azure resource being accessed and the operation against it . for eg: sp 'test' on azure key vault with read of secrets 
2) create a kubernetes service account to be used with the workload that needs azure resource access 
3) setup federated identity between sp in step#1 and SA in step#2
4) mutating admission web-hook to exchange the service account token for an AD token ( communcation between webhook and azure AD)
4) AD validates the incoming SA token against the oidc issuer url 
5) AD passes the token to webhook
6) webhook mounts incoming AD token in pod file system
7) pod (workload) uses the token in pre-determined path and invokes azure resources 
