POC for a simple ACME client that uses lets-encrypt with DNS challenge method

steps
* create a dns record using duckdns
* note the api key and the domain
* create the secret that wraps the duckdns api key ( kubectl create secret generic dnssec --from-literal=token=3c82fa91-0474-4b64-88a6-3bfe33496745 )
* create the pvc which will host the certs that will be created
* deploy the acme client job
* this job will do a dns-challenge towards lets-encrypt ( a TXT record under the duckdns domain)
* if everything succeeds , certs will be created and provided to the pvc mounted file path
* consume the same pvc on any other pod to get access to the certs
* renewal job will run each monday at 7:01 am and do cert rnewal if the expiry is < 30 days
* same pvc will be used to hold updated(renewed) certs if renewal is needed
