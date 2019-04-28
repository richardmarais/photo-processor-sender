Run com.assement.waldo.photoprocessorjava.PhotoProcessorJavaApplicationTests JUnit test
or start the com.assement.waldo.PhotoProcessorSenderApplication call the RESTful endpoint '/photos/process'
e.g. POST http://localhost:8080/photos/process
You can also view the existing photos by calling the RESTful endpoint '/photos/pending'
e.g. GET http://localhost:8080/photos/pending
or see Waldo.postman_collection.json

This will launch a Spring Boot runtime which will send the messages to the RabbitMQ queue.