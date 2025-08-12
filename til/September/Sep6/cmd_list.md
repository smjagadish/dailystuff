1. install vault in dev mode ( unsealed and unlocked)

helm repo add hashicorp https://helm.releases.hashicorp.com

helm repo update

helm install vault hashicorp/vault \
    --set "server.dev.enabled=true" \
    --set "injector.enabled=false" \
    --set "csi.enabled=true"

2. enable k8s auth with SA on vault and create needed role,policy . this step also creates a KV secret store in designated path and drops couple of secrets data

kubectl exec -it vault-0 -- /bin/sh

$ vault login root

$ vault secrets enable -version=1 kv

$ vault auth enable kubernetes

$ vault write auth/kubernetes/config token_reviewer_jwt="$(cat /var/run/secrets/kubernetes.io/serviceaccount/token)" kubernetes_host="https://$KUBERNETES_PORT_443_TCP_ADDR:443" kubernetes_ca_cert=@/var/run/secrets/kubernetes.io/serviceaccount/ca.crt

$ vault policy write kv_policy - <<EOF
path "kv/*" {
  capabilities = ["read"]
}
EOF


$ vault write auth/kubernetes/role/csi-kv \
bound_service_account_names=csi-sa \
bound_service_account_namespaces=default \
policies=kv_policy \
ttl=20m

## Put some Sample data

vault kv put kv/db password=password

vault kv put kv/app user=admin

3. install the secrets store csi driver

helm repo add secrets-store-csi-driver https://kubernetes-sigs.github.io/secrets-store-csi-driver/charts

## Install the chart
helm install csi secrets-store-csi-driver/secrets-store-csi-driver

4. apply the yaml to create a secretsprovider class CRD referring to vault and a deployment that volume mounts the secret in vault thru csi driver

Note : auto reload of secrets in the volume is not present unless an alpha feature is enabled
Note : CRD can be extended to also create an associated k8s secret and have it mounted as env var
