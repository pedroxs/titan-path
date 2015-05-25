# Distributed Shortest Route

This demo project demonstrates how to use [Titan DB](http://thinkaurelius.github.io/titan/) distributed
infrastructure with the help of Cassandra to calculate the
shortest route between to places.

## Dependencies

This project uses [Play Framework](https://www.playframework.com/),
[Scala](http://scala-lang.org/) 2.11.6 and [sbt](http://www.scala-sbt.org/) 0.13.8
To run this project you will also need [Cassandra](http://cassandra.apache.org/) and [ElasticSearch](https://www.elastic.co/)

If you only which to run the Unit tests you may alter the file conf/TitanConf.scala line:16 to
```
val prop = getClass.getResource("/titan-inmemory.properties")
```

If you like [Docker](https://www.docker.com/) the environment can be prepared with:

```
$ docker run -d --name cassandra -e LISTEN_ADDRESS=localhost -p 7000:7000 -p 7001:7001 -p 7199:7199 -p 8012:8012 -p 9042:9042 -p 9160:9160 poklet/cassandra
$ docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 elasticsearch:1.5
```

If are not on linux you will need some more port redirects:
```
$ boot2docker ssh -L 7000:localhost:7000 -L 7001:localhost:7001 -L 7199:localhost:7199 -L 8012:localhost:8012 -L 9042:localhost:9042 -L 9160:localhost:9160 -L 9200:localhost:9200 -L 9300:localhost:9300
```

### How to use

To add a new path send a POST request to /add-path/:map
ex:
```
curl -XPOST localhost:9000/add-path/SP -H "Content-Type: application/json" -d '
{
    "origin": "Sao Paulo",
    "destination": "Maringa",
    "distance": 120
}'
```

To trace a route send a POST to /trace-route/:map
ex:
```
curl -XPOST localhost:9000/trace-route/SP -H "Content-Type: application/json" -d '
{
    "origin": "Sao Paulo",
    "destination": "Maringa",
    "autonomy": 50,
    "price": 2.8
}'
```
