* thanos -> single pane of glass for multiple prometheus instances 
* multiple components packaged as a single binary 
* can be setup in a variety of ways and offload lot of responsibility from prometheus
* enables prometheus to concentrate on its core job of metrics scraping/collection 
* simplest model is thanos sidecar - scrapes from prometheus and exposes a grpc store api for querying . can also store/write prometheus data to object storage
* thanos querier - consumes store api and exposes prometheus remote read api for external tools. also has its own ui and supports de-duplication based on labels
* thanos store gateway - fetches data from object storage and exposes grpc store api for querying
* thanos reciever - remote write client for proemetheus and exposes grpc store api for querying 

