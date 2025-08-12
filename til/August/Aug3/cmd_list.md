* prepare vault-csr.conf file
   - CN and O must be aligned on the lines of what is present in the file. this is a need enforced by signer ,i.e. kubelet signing
   - same thing goes for key usage
   - SAN can be tailor-made for the specific deployment
* create a CSR file based on the conf file prepared above and sign it with the private key
   - openssl req -new -key vault.key -out vault.csr -config vault-csr.conf
* prepare certificate YAML based on the generated csr file . the cert will be issued by the indicated signer

tee csr.yaml<<EOF \napiVersion: certificates.k8s.io/v1\nkind: CertificateSigningRequest\nmetadata:\n  name: vault.svc\nspec:\n  signerName: kubernetes.io/kubelet-serving\n  expirationSeconds: 8640000\n  request: $(cat vault.csr|base64|tr -d '\n')\n  usages:\n  - digital signature\n  - key encipherment\n  - server auth\nEOF

* example csr.yaml file to be referred

* kubectl apply -f csr.yaml

* kubectl certificate approve <cert name>

* kubectl get certificates < check status is approved>

* Retrieve cert file
  - kubectl get csr vault.svc -o jsonpath='{.status.certificate}' | openssl base64 -d -A -out vault.crt

* Retrieve CA file
  - kubectl config view\\n   --raw \\n   --minify \\n   --flatten \\n   -o jsonpath='{.clusters[].cluster.certificate-authority-data}' \\n   | base64 -d > vault.ca

* create secret using ca, certificate and private key. this secret is referenced in overrides.yaml file

  - kubectl create secret generic vault-server-tls\\n   -n $VAULT_K8S_NAMESPACE \\n   --from-file=vault.key=vault.key \\n   --from-file=vault.crt=vault.crt \\n   --from-file=vault.ca=vault.ca

* deploy vault with overrides.yaml file as values placeholder
