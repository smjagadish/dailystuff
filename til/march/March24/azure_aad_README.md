Notes on playing with Azure AD and AKS

* Enable Azure AD integration at or after cluster creation
* No possibility to disable once turned-on/enabled
* --enable-aad arg to be used to turn on AD integration
* optionally an admin ad group (pre-created) can also be specified
* The admin group arg will lead to AKS creating a clusteradmin rolebinding for the said group
* AD integration to be followed by re-write of kubeconfig by doing az aks get-credentials
* Authenticate using OIDC (during first kubectl command) which will drop the token in kubeconfig file
* Inject admin credentials into kubeconfig by doing az aks get-credentials --admin
* Doing the above step on same kubeconfig file w/o a previous backup will effectively make the day-0 cluster user/owner to be forgotten <TBD>
* Combine AD group and RBAC to enforce fine-grained authz
* Out of box roles can be assigned to AD group (fairly limited and behind the scenes ends up doing RBAC)

