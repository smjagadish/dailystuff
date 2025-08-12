* index in elastic search can be created up-front or as part of the first doc that gets created under it
* below json is a sample for index (custom-ind) creation as part of doc creation
ACTION : POST
URL: http://localhost:9200/custom-ind/_doc
BODY:
{ "A JSON": "document" }
Note that , there is also a bulk API which can be used for above task. refer ES documentation for usage and differences
* static index creation can be done as captured below . note that this templatized approach also means we can have one template to wrap multiple different indexes
ACTION: PUT
URL : http://localhost:9200/_index_template/logs-template-nginx
BODY:
{
  "index_patterns": "logs-nginx",
  "data_stream": {
    "timestamp_field": {
      "name": "request_time"
    }
  },
  "priority": 200,
  "template": {
    "settings": {
      "number_of_shards": 1,
      "number_of_replicas": 0
    }
  }
}

* for the index creation template request above , it is also possible to define additional auto populated fields , enforce type checks , store 'raw' copy of a text field in addition to the tokenized value ( using the 'raw' property)
* the example shown above also leverages data streams , which are a special type of index (refer further down for info about data streams)
* specific doc retrieval (using doc id) from an index is a straight-forward operation in ES done as below
ACTION: GET
URL :  http://localhost:9200/custom-ind/_doc/<doc id>
* queried doc retrieval from an index can be done as shown below ( maybe a separate readme for the query api will also be created)
ACTION : GET
URL :  http://localhost:9200/custom-ind/_search
BODY: {
  "query": {
    "terms": {
      "kubernetes.labels.app" : [

           "kindnet"
      ]
    }
  }
}
* snippet above searches for all docs in the index custom_ind that has a json key kubernetes.labels.app with value equalling kindnet
* data streams are an improvisation on regular indices are mostly leveraged for append only data w/o any updates like log files
* data streams are backed by 1 write index and multiple read indices by ES. index rollover is automatically handled by ES and force roll-over is also possible
* data streams do require info on the field that acts as the timestamp indicator. creation can be through the template wrapper as shown above
* doc insert into data streams works in same way as for regular index. ES will auto map the referred index name to the current active write index
 sample insert into data stream ( assuming timestamp indicator is request_time)
ACTION : POST
URL: http://localhost:9200/logs-nginx/_doc
Body:
{
  "message": "login attempt failed",
  "request_time": "2013-03-01T00:00:00"
}

* re-indexing is a technique to move docs from 1 index to another . either selectively (through query match) or in entirety . destination index is auto-created if not present already

ACTION : POST
URL : http://localhost:9200/_reindex
BODY:
{
   "source":{
      "index":"fl_index"
   },
   "dest":{
      "index":"rd3"
   }
}

* in above snippet, rd3 is auto created . it is possible for rd3 to be created up-front as well



