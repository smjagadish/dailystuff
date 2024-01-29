Notes for the poc done on kuik

* think of kuik as a smart distributed cache for image pulls in a kubernetes cluster
* kuik uses a mutating webhook that updates image source in pod manifest on the fly by adding a localhost:7439/ prefix 
* a controller notices the pods and when it finds images not yet loaded into the 'distributed cache' it creates a CR for those images
* another controller fetches the images from actual registry (i..e the portion after the prefix added) and copies them into the cache 
* github link for the proj : https://github.com/enix/kube-image-keeper
* there is an overhead in terms of the disk usage for the storage of the images
* if there are no running containers  using a previously cached image , those images are garbage collected ( by default 30 days but can be configured)
* the utiloty allows pods to be skipped from kuik by using annotations . entire ns can also be bypassed
* private images become less private
* images must be refereced by tags and NOT using a digest
