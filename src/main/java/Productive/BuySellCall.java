package Productive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BuySellCall {
	static String buyCallList="";

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		
//		----------------- NSE 500 COMPANIES -----------------
		
//		File data = new File("D:\\Program Files\\WebScrapping\\NSE500.txt");
		
//		----------------- NSE 100 COMPANIES -----------------
		File data = new File("D:\\Program Files\\WebScrapping\\NSE100.txt");
		
//		----------------- fetching data from file -----------------
		String names = "";
		BufferedReader br = new BufferedReader(new FileReader(data));
		String st;
		while((st = br.readLine()) != null)
			names += st+" ";
		
		

//		----------------- USER INPUT STOCK NAMES -----------------
//		Scanner sc = new Scanner(System.in);
//		String names = sc.nextLine();
		names = names.trim();
		String nameStock[] = names.split(" ");

//		------------ MAIN FUNCTION CALL ------------
		for (int i = 0; i < nameStock.length; i++)
			Details(nameStock[i], 7);
		
//		------------ buy call list ------------
		System.out.println(buyCallList);
		
		
//		------------ CALCULATING TIME OF EXECUTION ------------
		long end = System.currentTimeMillis();
		float sec = (end - start) / 1000F; System.out.println(sec + " seconds");
	}
	
	
	
	
	
	
	
	

//	 -----------------  FUNCTION DEFINATION  -----------------
	public static void Details(String nameStock, int numberOfDays) throws IOException {
		try {
			int count = 0, index = 0;
//			String buyCallList="";

//		------> connecting java to url for respective stock
			String url = "https://in.finance.yahoo.com/quote/" + nameStock.toUpperCase() + ".NS/history?p="
					+ nameStock.toUpperCase() + ".NS&.tsrc=fin-srch";
			Document doc = Jsoup.connect(url).get();
			Elements yahooLinks = doc.select("div#Col1-1-HistoricalDataTable-Proxy");

//		-----------------  fetching data  -----------------
			String values[] = new String[numberOfDays];
			for (Element link : yahooLinks.select("tr")) {
				if (count < numberOfDays) {
					values[index++] = link.getElementsByTag("td").text();
					count++;
				} else
					break;
			}
			
//		-----------------  calculating Central Tendency  -----------------
			double sum = 0, average = 0, grandAvg, high = 0, num1 = 0, num2 = 0,day =1;
			for (int i = 1; i < numberOfDays; i++) {
				String[] temp = values[i].split(" ");
				/*
				 * positions value 
				 * temp[0] -> Date 
				 * temp[1] -> Opening price 
				 * temp[2] -> Highest price 
				 * temp[3] -> lowest price 
				 * temp[4] -> Closing price 
				 * temp[5] -> Absolute closing price 
				 * temp[6] -> volumne
				 */
//				if(day++ == 1)
//					continue;
//				else
//				{
					num1 = checkNumber(temp[1]);
					num2 = checkNumber(temp[4]);
					if (num1 > num2)
						high += num1;
					else
						high += num2;
//				}
				

				for (int k = 0; k < temp.length; k++) {
					if (k == 1 || k == 4)
						sum += checkNumber(temp[k]); // function to get exact number removing "," in value -> "26,99,00"
				}
				average += sum / 2;
				sum = 0;
			}
			grandAvg = average / (numberOfDays - 1);

//		-----------------  FETCHING CORRENT VALUE OF SHARE -----------------
			double corrent = 0;
			String formalNameStock = "";
			Elements yahooNameLinks = doc.select("div#quote-header-info");
			for (Element link : yahooNameLinks) {
				formalNameStock = link.getElementsByTag("h1").text();
				corrent = checkNumber(link.getElementsByTag("span").text().split(" ")[15]);
				break;
			}
//			-----------------  PRINTING DETAILS -----------------
//			if(grandAvg>corrent && corrent <= 300)
			printDetails(formalNameStock,grandAvg,corrent, high);
		}
		
		
		
		catch (Exception e) {
//			if that share is not in NSE here it will search for BSE

			try {

				int count = 0, index = 0;

//			------> connecting java to url for respective stock
				String url = "https://in.finance.yahoo.com/quote/" + nameStock.toUpperCase() + ".BO/history?p="
						+ nameStock.toUpperCase() + ".BO&.tsrc=fin-srch";
				Document doc = Jsoup.connect(url).get();
				Elements yahooLinks = doc.select("div#Col1-1-HistoricalDataTable-Proxy");

//			-----------------  fetching data  -----------------
				String values[] = new String[numberOfDays];
				for (Element link : yahooLinks.select("tr")) {
					if (count < numberOfDays) {
						values[index++] = link.getElementsByTag("td").text();
						count++;
					} else
						break;
				}

//			-----------------  calculating Central Tendency  -----------------
				double sum = 0, average = 0, grandAvg, high = 0, num1 = 0, num2 = 0;
				for (int i = 1; i < numberOfDays; i++) {
					String[] temp = values[i].split(" ");
					/*
					 * positions value 
					 * temp[0] -> Date 
					 * temp[1] -> Opening price 
					 * temp[2] -> Highest price 
					 * temp[3] -> lowest price 
					 * temp[4] -> Closing price 
					 * temp[5] -> Absolute closing price 
					 * temp[6] -> volumne
					 */
					num1 = checkNumber(temp[1]);
					num2 = checkNumber(temp[4]);
					if (num1 > num2)
						high += num1;
					else
						high += num2;

					for (int k = 0; k < temp.length; k++) {
						if (k == 1 || k == 4)
							sum += checkNumber(temp[k]); // function to get exact number removing "," in value ->
															// "26,99,00"
					}
					average += sum / 2;
					sum = 0;
				}
				grandAvg = average / (numberOfDays - 1);

//			-----------------  FETCHING CORRENT VALUE OF SHARE -----------------
				double corrent = 0;
				String formalNameStock = "";
				Elements yahooNameLinks = doc.select("div#quote-header-info");
				for (Element link : yahooNameLinks) {
					formalNameStock = link.getElementsByTag("h1").text();
					corrent = checkNumber(link.getElementsByTag("span").text().split(" ")[15]);
					break;
				}
//				-----------------  PRINTING DETAILS -----------------
//				if(grandAvg>corrent)
				printDetails(formalNameStock,grandAvg,corrent, high);

			} catch (Exception e1) {
				System.out.println("\n\n*********  Some Error Found for "+nameStock.toUpperCase()+" stock *********\n\n");
			}
		}
	}

//	-------------- chekNumber Function Call  -------------- 
	/*
	 * for getting absolute values e.g = 
	 * 4,500 -> 4500 
	 * 56,90,000 -> 5690000 
	 * 890 -> 890
	 */
	public static double checkNumber(String temp) {
		String output = "";
		if (temp.contains(",")) {
			String new_data[] = temp.split(",");
			for (int z = 0; z < new_data.length; z++)
				output += new_data[z];
			return Double.valueOf(output);
		} else
			return Double.valueOf(temp);
	}
	
	public static void printDetails(String formalNameStock,double grandAvg,double corrent,double high)
	{
//		--------------- Printing Data  --------------
			System.out.println("---------------  " + formalNameStock + "  ---------------\n");

			System.out.print("Central Tendency \t\t\t\t-> ");
			System.out.println(Math.round(grandAvg * 100.0) / 100.0);
			System.out.print("Corrent Price \t\t\t\t-> ");
			System.out.println(corrent);
			


//		-------------- Predicting BUYCALL or SELLCALL  -------------- 
			if (corrent < grandAvg)
			{
//				------------ percentage calculator ---------------
				double percent = ((grandAvg - corrent) / corrent)* 100 ;
				System.out.println("\t\t\t\t\t\t\t\t>>>>>>>> BUY CALL <<<<<<<<< ["+percent+"%]\n");
				int n1 = formalNameStock.indexOf("(");
				int n2 = formalNameStock.indexOf(")");
				buyCallList += formalNameStock.substring(n1+1, n2-3) +" ";
			}
				
			else if (corrent >= high)
			{
//				------------ percentage calculator ---------------
				double percent = ((corrent - grandAvg) / grandAvg)* 100 ;
				System.out.println("\t\t\t\t\t\t\t\t>>>>>>>> SELL CALL <<<<<<<<< ["+percent+"%]\n");
			}
			else
				System.out.println("\t\t\t\t\t\t\t\t>>>>>>>> NEUTRAL <<<<<<<<<\n");
	}

}
