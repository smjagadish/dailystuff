Notes and commands for rekor transparency ledger

* rekor is  tamper-resistant ledger for signed metadata
* complements cosign for instance and fulfills the sig transparency part of supply chain security 
* ensures a recording is in place for signed artifact - basically the artifact URL(can be name) ,  its signature and the cert/key used to sign the artifact
* rekor exposes a public transparency ledger which can be leveraged for quick POC . also possible to setup a private rekor server 
* REST API is avaiable to query the ledger - both for verification of artifact as well as to validate/verify the ledger itself 
* cosign already has experimental support for native rekor integration 

few sample commands from a quick poc

- key-pair generation

ssh-keygen -f id_rekor -C user@example.com 

- creation of a file which will be signed

echo "Hello, World!" > README.md

- signing the file using key pair

ssh-keygen -Y sign -n file -f id_rekor README.md

- uploading to rekor 

rekor-cli upload --artifact README.md --signature README.md.sig --pki-format=ssh --public-key=id_rekor.pub

- searching that the entry is added in rekor

rekor-cli search --artifact README.md
rekor-cli search --public-key id_rekor.pub --pki-format=ssh
rekor-cli get --uuid 24296fb24b8ad77a4ac515e7a1b5f63673b463f8710eec83e4b86ba5bac1f465fdf70b0ec8efc88e

- verifying signed manifest and ensuring transparency from rekor 
rekor-cli verify --signature README.md.sig --pki-format=ssh --public-key id_rekor.pub --artifact README.md


