* side-car dns resolver with a local cache
* forwards un-resolved queries and cache misses to upstream dns resolver/server of choice - core-dns in our case
* has a bunch of security features - to investigate and study more 
* from K8s standpoint , dnspolicy of none allows to stitch things and use the side-car resolver
* standard k8s offering for this is the nodelocal dns cache - another til will be done for this

