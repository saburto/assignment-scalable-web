# Assignment Scalable Web

### Requeriments

* JDK 8

### How to build and run tests

For building and unit test

```sh
$ ./gradlew build
```
For integration test

```sh
$ ./gradlew integrationTest
```

### How to run it

To run locally on port 8080
```sh
$ ./gradlew build & java -jar build/libs/assignment-scalable-web-1.0.0.jar
```

#### Usage

To run locally on port 8080
```sh
    $ ./gradlew build && java -jar build/libs/assignment-scalable-web-1.0.0.jar
```

To post data left
```sh
$ curl localhost:8080/v1/diff/<<ID>>/left \
-H "Content-Type: application/json" \
-X POST -d '{"data":"<<DATA>>"}'
```
To post data right
```sh
$ curl localhost:8080/v1/diff/<<ID>>/right \
-H "Content-Type: application/json" \
-X POST -d '{"data":"<<DATA>>"}'
```
To get diff information

```sh
$ curl localhost:8080/v1/diff/<<ID>>
```
Parameters:
<<ID>> Unique ID for comparation. See example.
<<DATA>> Base64 string encoded

### Example

Post first data on the left.
Request:
```sh
 curl localhost:8080/v1/diff/444/left \
-H "Content-Type: application/json" \
-X POST -d '{"data":"MTIz"}'
```
Response:
```sh
#empty
```
Post second different data on the right.
Request:
```sh
 curl localhost:8080/v1/diff/445/right \
-H "Content-Type: application/json" \
-X POST -d '{"data":"MTTz"}'
```
Response:
```sh
#empty
```
Get the differences
Request:
```sh
 curl localhost:8080/v1/diff/445
```
Response:
```json
{"equal":false,"equalSize":true,"diffs":[{"index":1,"length":2}]}
```
## Suggestions for improvement

* Add databse repository
* Add automatic generation of api rest documentation
* Add async process for comparation data. Maybe a queue or multiple thread implementation.
* Save the diff response on a database or cache to never do the comparation twice.
