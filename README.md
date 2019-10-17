## Instructions: ##

### Initial setup: ###

1. [Download Eclipse](https://www.eclipse.org/downloads/)
 1.1 Install [Java](https://www.java.com/en/download/windows-64bit.jsp) if it's not installed on your system.

2. Download the project from [GitHub](https://github.com/Demonikki/self-testing) (click the Green "Clone or download" button on the right, and download as a ZIP)

3. Download [Postman](https://www.getpostman.com/)

4. Import the 2 folders (currency-conversion-service and currency-exchange-service) into Eclipse.
_File -> Import -> Maven -> Existing Maven Project -> Select one of the folders, and repeat for the other one._

5. Once imported, right click currency-conversion-service and in the menu:
_Properties -> Java Build Path -> Libraries tab -> Add External JARs -> Choose the json-simple-1.1.1.jar file_


### To run the application: ###

#### Current version: v0 ####

Once the 2 folders are imported as Maven projects into Eclipse

1 Go into the currency-exchange-service folder - src/main/java - expand all folders inside
 1.1 Look for the CurrencyExchangeServiceApplication.java file. 
 1.2 Right click that -> Run As -> Java Application

2. Go into the currency-conversion-service folder - src/main/java - expand all folders inside 
 2.1 Look for the CurrencyConversionServiceApplication.java file. 
 2.2 Right click that -> Run As -> Java Application.

3. Accept any Windows Firewall prompts you get. Now the webservice should be up and running. 

4. Open Postman. You'll need an account, but you can use your Google account to sign up.

5. Now you can issue GET and POST requests. The database of currency exchange values is already populated. Below are some sample requests with some input that you can use. To perform a currency conversion, Postman should already be set to GET requests (a GET button next to the request box)
  a. http://localhost:8100/currency-converter/from/USD/to/INR/quantity/900
  b. http://localhost:8100/currency-converter/from/EUR/to/INR/quantity/45
  c. http://localhost:8100/currency-converter/from/AUD/to/EUR/quantity/70

 These 3 examples should be enough for a demo


6. Now, you can make POST requests to save the user's input so far, as well as the output they received. This will get logged under "log_vX.json" file in the currency-conversion-service folder. To do this, Make sure Postman is set to POST (change the GET button to POST) and enter the following request:
   http://localhost:8100/currency-converter/log-requests 
   Now all the requests made so far, along with their output, are stored into the JSON file.

#### Updating to version v1 ####

There are 2 changes to make to move to version 1. We want to introduce a small logical bug in CurrencyConversionController.java, and update the version number in CurrencyConversionServiceApplication.java

1. In CurrencyConversionController.java, comment out line 59 (under v0 code) and uncomment line 61 (under v1 code). This is the bug.

2. In CurrencyConversionServiceApplication.java, change line 16 to controller.currentVersion = 1; 

Save both files. The program should automatically reload and run the previous input/output against the current version, and report on any discrepancies.




