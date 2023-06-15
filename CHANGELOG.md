### versions:
##### 0.0.1-SNAPSHOT - initial working version.
- very simple - uploading url lists to mongo. got more then 700ms for long request. 
- tried uploading with Domain object, containing domain name and list of urls - but running upsert on mongo was very slow - gave up(hours!)
##### 1.0.0 - 
- added redis as an intermediate processor for the data.
- for improved performance and optional complex queries, I created a Domain object containing domain name and list of urls.
- after processing the data I uploaded it to mongo, indexed by domain name.
- improve redis writes with pipelined writes.
- results in very fast api calls - up to 200ms, 500K+ domains and 1M+ urls.