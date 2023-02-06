opensearch notes

* built on top of apache lucene and is a distributed documented oriented db (of sorts) with advanced search capabilities
* lucene probably provides the free text search capabilities 
* documents are organized as json objects and each document belongs to an index 
* indices are stored in shards with a primary  shard and one/more replica shards 
* shards are distributed amongst opensearch nodes 
* possibility to enforce affinity rules (i.e. zone based distribution) when distributing shards amongst the nodes
* node can be either a master, data or ingestor 
* data nodes are the ones handling read/write operations on the data . these require high storage and high memory 
* ingestor can run pre-processing pipelines (if any) and also serve as a facade for the multiple data nodes
* ingestor thus is a single point of contact for app nodes and can distribute/consolidate search queries amongst data nodes and route writes to excat data node
* ingestor just needs to be high on memory 
* master node is responsible for overall cluster management , i.e. monitoring which nodes enter/exit the cluster , shard assignment amongst nodes, shard re-assignment if any etc .
* node roles are setup in configuration files and that governs if a node starts up as master/ data / ingestor

