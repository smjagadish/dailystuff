Loki - yet another kid on the block for log aggregation/analysis 
* totally different to the ELK stack which is based on full indexing , i.e. all the columnar k/v data is indexed. the index and the raw log data both are storaed and both are quite heavy in terms of space
* Loki indexes metadata or labels as opposed to full indexing . Every log entry being ingested is mapped to a stream based on the hash of the associated labelset
* the component that does this is a so-called digester. digester creates streams based on the labelset hash and forwards it to the ingestor
* ingestor creates chunks for incoming streams . the chunks are then compressed and stored in object storage
* ingestor also writes an index entry of the labelset and the chunk id into the same object storage [ this comes into play during the read/retireval ]
* for the read , the input criteria for retrieval is the labels . this can be followed by additional filtering . label is the first leg which will help in identifying streams and chunks 
* read component first reads from ingestors and then from object storage. guess is that for the object storage retrieval, the index is consulted 
* with loki 1 query is split into multiple sub-queries and then the result is processed ( multiple sub-queries are towards the different ingestors and the chunks)
* key for loki to work effectively and efficiently is the ability to keep indexing and streams/chunks ( as a consequence of the indexing) as coarse as possible. else read will be brutal and kill loki. write is never a problem though or is lesser of the 2 evils
