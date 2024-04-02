### Version Used
* Java 1.8
* Maven 3.6.3
* Spring boot 2.7.5
* jacoco 0.8.7 - For Coverage report
* Open API 1.6.9 for API Specification

### Run Tests with Maven:
"mvn clean install" command. This will run the project and also execute the test as well.

### Run Tests with Maven:
"mvn test" command. This will execute your tests and collect coverage information.

### Swagger Implement for API specification
http://localhost:8080/swagger-ui/index.html#/
![img_1.png](img_1.png)
### Basic Auth implement only
User is "admin"

### Generate Coverage Reports:
After running the tests, JaCoCo will generate coverage reports automatically. You can find the reports in the target/site/jacoco directory of Maven project.
![img_2.png](img_2.png)
### Viewing Reports:
Open the generated HTML report (index.html) in a web browser to view the code coverage summary and detailed coverage information for each class.


### Class Diagram
![img.png](img.png)

#### Having limitation on my office laptop due to which not able to install sonarQube locally for report. 