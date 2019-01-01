#Created by p4pupro on 01/01/2019.

FROM java:8
ADD target/tenejob-1.0.0.jar  tenejob-1.0.0.jar
ENTRYPOINT ["java","-jar","tenejob-1.0.0.jar"]