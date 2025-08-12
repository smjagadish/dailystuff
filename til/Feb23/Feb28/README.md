external-dns operator README

* a handy utility to expose K8s native services (of lb type) and ingresess to the cloud provider DNS
* this service is NOT a dns server/resolver. Rather , it configures or sets-up the cloud provider DNS resolvers
* a very simple poc in azure was done as follows
* public dns zone created in azure with the DN sampledns.esmjaga.com
* azure provides 4 NS's (publically resolvable) for this dns zone
* this DN must be setup with the DNS registrar to point to the 4 NS's that azure gave -> this step alone is skipped in the poc
* as first test, a LB service created with annotation for external dns
* this annotation matches the filtering criteria (matched host/zone) used in the external dns install
* result is an A record in the public DNS zone with name same as annotation value and address the LB address
* note that this approach works for both public lb ip and private lb ip
* the second test was to test publishing of ingress resources
* nginx ingress installed and and ingress created for a sample svc with host name matching the filter criteria
* result is an A record in the public DNS zone with same name as the ingress host and address being LB address for ingress controller svc
