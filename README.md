#PrimeCheckCICD 

PrimeCheckCICD is a VERY simple AWS Lambda Function implemented in Java. 
It has two classes for representing the request and response objects. 
A third class that implements [RequestHandler](https://github.com/aws/aws-lambda-java-libs/blob/master/aws-lambda-java-core/src/main/java/com/amazonaws/services/lambda/runtime/RequestHandler.java)<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>, determines if a provided number is a prime number or not 
and responds with a message.

Implementing an RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> means that this AWS Lambda 
function is well prepared to be deployed in the AWS API Gateway, accessible from the Web, if you want.
Deserialization and serialization still needs to be handled by this Lambda function, which actually is a good thing,
 allowing the use of Gson for instance, with much more concise code.

The project also has some unit tests implemented and uses Log4J2 locally and also when deployed, logging into AWS CloudWatch.


##Interacting with AWS
For convenience, and with the provided SAM cloud formation template, the PrimeCheckAPI projects includes gradle tasks for 
* buildZip, creating an archive with all required resources and libraries: buildZip
* deleteStack, removing an already deployed AWS Stack
* deploy, deploying the locally build Lambda function package into an S3 bucket and setting up the API Gateway
* uploadZip, copying the locally build Lambda function the function into an S3 bucket

This can be done from a commandline in Terminal or Inside the Gradle Tool-Window inside of IntelliJ IDEA

##Accessing the Service API
Once the deploy task has created, copied, and deployed the locally build Lambda function package, 
opening the [AWS API Gateway WebUI](https://us-west-2.console.aws.amazon.com/apigateway/home) will expose the URL 
in the dashboard view. E.g.: https://gffnscm1mh.execute-api.us-west-2.amazonaws.com/Prod/
Since the API is available as an HTTPS POST, a request like this:

curl -d "{\"number\":17}" https://3cu12lrona.execute-api.us-west-2.amazonaws.com/Prod/

returned this response:

{"tag":"PrimeCheck P4","answer":"Yes, 17 is a prime number, divisible only by itself and 1","n":17,"d":1}
