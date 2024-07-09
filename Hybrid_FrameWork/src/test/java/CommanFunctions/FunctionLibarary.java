package CommanFunctions;

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.DirectoryStream.Filter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.math3.analysis.function.Logistic;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibarary {
	private static final String Locatorvalue = null;
	private static final String TestData = null;
	public static WebDriver driver;
	public static Properties conpro;
	//Method for launch browser
	public static WebDriver startBrowser()throws Throwable
	{
		conpro = new Properties();
		//load properties file
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
		driver = new FirefoxDriver();
		}
		else 
		{
			
			
			Reporter.log("Browser value is not matching",true);
			
		}
		return driver;
		
	}
	//method for launching url
	public static void openurl()
	{
		driver.get(conpro.getProperty("url"));
	}

	 
		//mwthod for to wait for any webelement in a page
	
	public static void waitForElement(String Locatortype,String LocatorValue,String TestData)
	{
		WebDriverWait mywWait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
	if(Locatortype.equalsIgnoreCase("id"))
	{
		mywWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
	}
	if(Locatortype.equalsIgnoreCase("name"))
	{
		mywWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		
	}
	if(Locatortype.equalsIgnoreCase("xpath"))
	{
		mywWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
				
	}
	}
	
	//methods for any textbox
	
	public  static void typeAction(String Locatortype,String LocatorValue,String TestData)
	{
		if(Locatortype.equalsIgnoreCase("id"))
		{
		driver.findElement(By.id(LocatorValue)).clear();
		driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
	}
	if(Locatortype.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locatorvalue)).clear();
		driver.findElement(By.xpath(Locatorvalue)).sendKeys(TestData);
	
	}
	if(Locatortype.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locatorvalue)).clear();
		driver.findElement(By.name(Locatorvalue)).sendKeys(TestData);
	}
}

//methods for any button,checkbox,radiobutton,links and images
public  static void typeAction(String Locatortype,String LocatorValue)
{
	if(Locatortype.equalsIgnoreCase("xpath"))
	{	
		driver.findElement(By.xpath(Locatorvalue)).click();
	}
	if(Locatortype.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locatorvalue)).click();
	}
	if(Locatortype.equalsIgnoreCase("id"))
	{
		driver.findElement(By.xpath(Locatorvalue)).sendKeys(Keys.ENTER);

	}
}

public static void validateTitle(String Expected_Title)
{
	String Actual_title = driver.getTitle();
	try {
		Assert.assertEquals(Actual_title,Expected_Title,"Title is Not Matching ");
	}catch(AssertionError a)
	{
		System.out.println(a.getMessage());
	}
}

	//	method for closing
public static void closerBrowser()
{
	driver.quit();
	
}
//method for generate date formate
public static String generateDate()
{ 
	Date date = new Date(0);
	DateFormat df = new SimpleDateFormat("yyyy_mm_dd hh_mm_ss");
	return df.format(date);
	
}
// methods for listboxes
public static void dropDownAction(String Locatortype,String LocatorValue,String TestData)
{
	if(Locatortype.equalsIgnoreCase("id"))
	{
		//convert testdata string type into integer
		int value = Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.id(LocatorValue)));
		element.selectByIndex(value);
	}
	if(Locatortype.equalsIgnoreCase("name"))
	{
		//convert testdata string type into integer
				int value = Integer.parseInt(TestData);
				Select element = new Select(driver.findElement(By.id(LocatorValue)));
				element.selectByIndex(value);
	}
	if(Locatortype.equalsIgnoreCase("xpath"))
	{
		//convert testdata string type into integer
		int value = Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.id(LocatorValue)));
		element.selectByIndex(value);
	}
}

// methods for capturing stock number into notepad
public static void captuserStock(String Locatortype,String LocatorValue)throws Throwable
{
	String StockNum = "";
	if(Locatortype.equalsIgnoreCase("name"))
	{
		StockNum = driver.findElement(By.name(LocatorValue)).getAttribute("value");	
	}
	if(Locatortype.equalsIgnoreCase("id"))
	{
		StockNum = driver.findElement(By.id(LocatorValue)).getAttribute("value");	
	}
	if(Locatortype.equalsIgnoreCase("xpath"))
	{
		StockNum = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");	
	}

	FileWriter fw = new FileWriter("./CaptureData/stockNumber.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(StockNum);
	bw.flush();
	bw.close();
}
//method validate stock number in table
public static void stockTable()throws Throwable
{
	//read stock number from notepad
	FileReader fr = new FileReader("./CaptureData/stockNumber.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data =br.readLine();
	//if search textbox already displayed dont click search panel button
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		//if search textbox not displayed click search p[anel button
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	//clear text in search textbox
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	//enter sctock number into search textbox
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(3000);
	String Act_Data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
	Reporter.log(Act_Data+"        "+Exp_Data,true);
	try {
	Assert.assertEquals(Act_Data, Exp_Data,"Stock number Should not Match");
	}catch(AssertionError a)
	{
		Reporter.log(a.getMessage(),true);
	}
}

}
	



		
	
	

	
		
	
	