* pipelines are pre-processing engines/stages for all docs in a index
* pipeline to be triggered for the docs in an idex can be specified as part of the index creation/updation
* pipeline creation sample
ACTION : PUT
URL : http://localhost:9200/_ingest/pipeline/pipeline-test
BODY:
{
"description": "Splits the text field into a list. Computes the length of the 'word' field and stores it in a new 'word_count' field. Removes the 'test' field.",
"processors": [


 {
   "remove": {
     "field": "text"
   }
 }
]
}
* in the above info , pipeline-test is the name of the pipeline and it removes the field called text from all docs that are associated with the index
* associating index to pipeline is done as shown below ( example from an index creation reques)

{
  "settings": {
    "index": {
      "number_of_shards": 2,
      "number_of_replicas": 1,
      "default_pipeline": "pipeline-test"
    }
  },
  "mappings": {
    "properties": {
      "word": {
        "type": "text",
        "fields": {
            "raw": {
                "type": "keyword"
            }
        }
      },
      "text": {
          "type": "text"
      },
      "factor": {
          "type": "integer"
      }
    }
  },
  "aliases": {
    "sample-alias1": {}
  }
}

* notice the default_pipeline flag that refers to created pipeline
