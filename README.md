# Walrus Assignment
Created Service Rouge: which will be accessible on 'http://localhost:8080/rouge' which will respond with responce code 503 ,504 or 200
Created Service Undeterred: which will be accessible on 'http://localhost:8080/hello-world' which calls Rouge service and respond
server can be accessed from index.html page's button

How to start the Hello application
---

1. Run `mvn clean install` to build your application from the Walrus main directory
1. Start application with `java -jar target/hello-0.0.1-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080/hello-world`
2. Run index.html file and call the server from the button

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
