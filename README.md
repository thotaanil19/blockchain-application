# Blockchain Powered Registry Solution

1.	Install the JDK 1.8.
2.	Install the Node server (version 12.14.1).
3.	Install the Maven (version 3.6.3)
4.	Clone the source code from GitHub 
https://github.com/thotaanil19/blockchain-application.git
5.	Build the frontend code by running following command from command prompt from frontend project root folder
npm build
6.	If waring in command prompt, run the
npm audit fix        
7.	Run the frontend code with following command
ng serve
8.	Build backend code by running following command from backend root folder
mvn clean install package
9.	Run the backend code
mvn spring-boot:run

# How to deploy this project into PCF cloud

1. cf login
thota.anil19@gmail.com/Blockchain@123
2. Deploy frontend code
cf push blockchain-ui
3. Deploy backend code
cf push blockchain-backend ./target/blockchain-kotlin-0.0.1-SNAPSHOT.jar



