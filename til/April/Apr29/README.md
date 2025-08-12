Vault PKI for Cert.Manager
***************************

Steps involved

1) Vault install and unseal

$ helm repo add hashicorp https://helm.releases.hashicorp.com

$ helm upgrade -i vault hashicorp/vault --set "ui.enabled=true" --set "ui.serviceType=LoadBalancer" \
--set "server.service.type=LoadBalancer"  --set="server.dev.enabled=true"

2) generate a self-signed CA

$ CONFIG="
  [req]
  distinguished_name=dn
  [ dn ]
  [ ext ]
  basicConstraints=CA:TRUE,pathlen:0
  "

$ openssl req -config <(echo "$CONFIG") -new -newkey rsa:2048 -nodes \-subj "/C=IN/O=bettercallpavan/OU=Cloud-DevOps/ST=AP/CN=bettercallpavan.com/emailAddress=pavan@medium.com" -x509 -days 365 -extensions ext -keyout root-key.pem -out root-cert.pem

$ cat root-key.pem root-cert.pem > pem_bundle


3) set pki backend in vault

$ export VAULT_ADDR=http://$(kubectl get svc vault -o jsonpath='{.status.loadBalancer.ingress[0].ip}'):8200

$ export VAULT_ROOT_TOKEN=$(cat init-keys.json | jq -r ".root_token")

$ vault login $VAULT_ROOT_TOKEN

$ vault secrets enable pki

$ vault secrets tune -max-lease-ttl=8760h pki

$ vault write pki/config/ca pem_bundle=@pem_bundle ttl=8760h

4) create vault role , distn url and policy

vault write pki/roles/pkigen allowed_domains=playground.com allow_subdomains=true max_ttl=2000h
vault write pki/config/urls issuing_certificates="10.96.177.154:8200/v1/pki/ca" crl_distribution_points="10.96.177.154:8200/v1/pki/crl"

vault policy write pkigen_policy - <<EOF
> path "pki*"                        { capabilities = ["read", "list"] }
> path "pki/roles/pkigen"   { capabilities = ["create", "update"] }
> path "pki/sign/pkigen"    { capabilities = ["create", "update"] }
> path "pki/issue/pkigen"   { capabilities = ["create", "update", "read", "list"] }
> EOF

5) enable k8s auth for vault policy

vault write auth/kubernetes/config token_reviewer_jwt="$(cat /var/run/secrets/kubernetes.io/serviceaccount/token)" kubernetes_host="https://$KUBERNETES_PORT_
443_TCP_ADDR:443" kubernetes_ca_cert=@/var/run/secrets/kubernetes.io/serviceaccount/ca.crt

vault write auth/kubernetes/role/issuer bound_service_account_names=issuer bound_service_account_namespaces=default policies=pkigen_policy ttl=20m

6) install cert manager

helm repo add jetstack https://charts.jetstack.io
$ helm install \
  cert-manager jetstack/cert-manager \
  --namespace cert-manager \
  --version v1.2.0 \
  --create-namespace \
  --set installCRDs=true

might have to also do kubectl apply -f https://github.com/jetstack/cert-manager/releases/download/v1.4.0/cert-manager.yaml [ to circumvent issues with mutating webhook ]

7) SA creation and issuer definition

kubectl create sa issuer
export ISSUER_SECRET_REF=issuer-token-8xdtz

Ref file : issuer.yml

8) certificate create request

Ref file : certificate.yml

9) app deploy [ make sure to deploy ingress controller b4]

Ref file : app.yml


