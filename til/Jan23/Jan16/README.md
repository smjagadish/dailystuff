opensearch notes

* built on top of apache lucene and is a distributed documented oriented db (of sorts) with advanced search capabilities
* lucene probably provides the free text search capabilities 
* documents are organized as json objects and each document belongs to an index 
* indices are stored in shards with a primary  shard and one/more replica shards 
* shards are distributed amongst opensearch nodes 
* possibility to enforce affinity rules (i.e. zone based distribution) when distributing shards amongst the nodes
* node can be either a master, data or ingestor or co-ordinator 
* data nodes are the ones handling read/write operations on the data . these require high storage and high memory 
* ingestor can run pre-processing pipelines (if any) 
* co-ordinator serve as a facade for the multiple data nodes
* co-ordinator  thus is a single point of contact for app nodes and can distribute/consolidate search queries amongst data nodes and route writes to excat data node
* co-ordinator just needs to be high on memory 
* master node is responsible for overall cluster management , i.e. monitoring which nodes enter/exit the cluster , shard assignment amongst nodes, shard re-assignment if any etc .
* looks like the index creation is also something handled by the master node ( to be confirmed)
* node roles are setup in configuration files and that governs if a node starts up as master/ data / ingestor / co-ordinator

