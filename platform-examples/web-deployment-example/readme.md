# Dolphin Platform Spring Boot Sample

![Dolphin Platform Logo](http://www.guigarage.com/wordpress/wp-content/uploads/2015/10/logo.png)

This automatically created project can be used as a basic skeleton for a Spring Boot based Dolphin Platform application.
The Maven projects contains 3 modules: 

* The __common__ module that contains the model that is shared between client and server
* The __server__ module that contains the Spring Boot Application and a first Dolphin Platform controller 
* The __client__ module a JavaFX application that creates a view that is bound to the Dolphin Platform controller in the server module


## How to use it

Install bower dependencies(if asked for polymer version,
choose option no 3, it should be __>=1.0.4__ __<=1.4.0__)
```
cd platform-examples/polymer-client
bower install
```

Steps for using/generating war file:
* Open index.html

* Edit the client context URL, Make sure you have war file Name in the (http://localhost:8080/WebDeployment/dolphin)

* Generate war file by below command (go to web-deployment-example
folder & then do mvn clean install)
```
cd platform-examples/web-deployment-example
mvn clean install
```

* Copy the generated war file in the tomcat webapps directory

* Run the Polymer client using following url
```
http://localhost:8080/WebDeployment/polymer/index.html
``` 