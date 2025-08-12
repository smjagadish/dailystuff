Lazy notes from exploring IAM in aks and eks
eks
-> a purpose built cluster iam role and node iam role is needed with relevant policies before cluster creation and node group creation
-> these are auto created when using eksctl. if using cloud formation or the aws portal, one would have to reference existing roles of these type or create them and then reference .
-> service-linked roles for the cluster and node group is also needed . these are auto created and need not be manually created ever ( regardless of the method of cluster creation)
-> these service-linked roles come with a predefined policy which cannot be edited/modified . they can be deleted if the cluster itself has been deleted already
-> once the clutser is created , from K8s RBAC standpoint the system:masters group is assigned to the iam user/role which was used to create the cluster
-> it is not possible to associate the system:masters to any other iam user/role
-> recommendation is to create the cluster with an iam role such that this role can be assumed by any individual when needed
-> the role can be later deleted and recreated on demand
-> for access to cluster as such , define purpose built roles (such as k8s read, k8s rw etc) and assign these to users
-> define k8s role bindings that refer to business groups
-> update aws-auth config map or use aws-iam-authentictaor to map business groups wrapping k8s bindings to iam roles

aks
-> leverages concept of managed identities which are similar to eks's cluster iam role and node iam role
-> managed identity is auto created when creating an aks cluster. for both cli based and console based
-> it is also possible to manually create managed identity before hand and use these during cluster creation
-> unlike eks , in aks there doesn't (!) seem to be the concept of iam roles. just iam users
-> the user who created the cluster gets to be the cluster admin user automatically ( i.e. with the system:masters group assigned)
-> until this moment in the flow, there is no AD involved . the auth is also through x509 certs
-> AD can be integrated either for new clusters or existing clusters
-> once done, it cant be disabled though . with ad involved, auth is slightly similar to eks land where we have tokens that are created based on AD auth result
-> even with AD, a bypass/backdoor exists by leveraging the --admin flag
-> this AD integration can be used for authorization too by mapping k8s rolebindings to ad groups
-> ref other poc done for more info on this ( incl. the usage of azure rbac for authz)
