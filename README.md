# Stock-Market-Prediction
Using this program you will be able to have rough prediction about the stock market where did market go next day

I am using Jsoup for extracting data from website.
For that purpose i am using Maven Project and add dependency for JSOUP.

In the mentioned code we are doing as followed :
  1. Reading Stockes from different inputs
        (i) From a file having NSE 500 companies
        (ii) From a file having NSE 100 companies
        (iii) From a respective user own input.
      
  2. Checking searched stock is available in NSE/BSE or not
  3. Extract data From website
  4. Analysing data
  5. Making numbers Absolute  (eg 45,00,666 to 4500666 )
  6. Applying Formula for PREDICTION
  7. Printing all the searched values (BUY / SELL / NEUTRAL) for each respective share.
