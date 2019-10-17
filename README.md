## Instructions: ##

### Initial setup: ###

1. [Download Eclipse](https://www.eclipse.org/downloads/)


1.1 Install [Java](https://www.java.com/en/download/windows-64bit.jsp) if it's not installed on your system.

2. Download the project from [GitHub](https://github.com/Demonikki/self-testing) (click the Green "Clone or download" button on the right, and download as a ZIP)
 

3. Download [Postman](https://www.getpostman.com/)

4. Import the 2 folders (currency-conversion-service and currency-exchange-service) into Eclipse.
_File -> Import -> Maven -> Existing Maven Project -> Select one of the folders, and repeat for the other one._

Once imported, right click currency-conversion-service and in the menu:
_Properties -> Java Build Path -> Libraries tab -> Import External Jar -> Choose the json-simple-1.1.1.jar file_


### To run the application: ###


Once the 2 folders are imported as Maven projects into Eclipse,

1.  Go into the currency-exchange-service folder - src/main/java - expand all folders inside, and look for the CurrencyExchangeServiceApplication.java file. 

Right click that -> Run As -> Java Application

2. Go into the currency-conversion-service folder - src/main/java - expand all folders inside, and look for the CurrencyConversionServiceApplication.java file. 

Right click that -> Run As -> Java Application.

Accept any Windows Firewall prompts you get. Now the webservice should be up and running. 

3. Open Postman. You'll need an account, but you can use your Google account to sign up.

4. Now you can issue GET and POST requests. Below are some sample requests with some input that you can use:


