import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;


public class BingTranslator {
	public static void main(String[] args)throws IOException
	{
		// TODO Auto-generated method stub
		File ffBinary=new File("/home/nawazakhtar/Downloads/firefox/firefox");
		FirefoxBinary ffBinaryFile=new FirefoxBinary(ffBinary);
		FirefoxProfile ffProfile=new FirefoxProfile();
		WebDriver driver= new FirefoxDriver(ffBinaryFile, ffProfile);
		//open the browser
		
		
		driver.get("http://www.bing.com/translator");
		driver.findElement(By.className("LS_HeaderTitle"));
		List<WebElement> list = driver.findElements(By.className("LS_HeaderTitle"));
		String line = "";
		try
		{
		File file = new File("/home/nawazakhtar/Downloads/eclipse/home/desktop/BingAssignement/Translator.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		while((line=br.readLine())!=null)
		{
		String words[] = line.split(",");
		String from = words[0];
		String to = words[1];
		String word = words[2];
		  String s1 = "(//td[text()='"+from+"'])[1]";
	        String s2 = "(//td[text()='"+to+"'])[2]";
	        list.get(0).click();
	        Thread.sleep(2000);
	        driver.findElement(By.xpath(s1)).click();
	       
	        driver.findElement(By.className("srcTextarea")).sendKeys(word);
	       
	        list.get(1).click();
	        
	        driver.findElement(By.xpath(s2)).click();
	        Thread.sleep(2000);
	        driver.findElement(By.className("srcTextarea")).clear();
	        Thread.sleep(2000);
	     //WebElement wb=driver.findElement(By.className("textArea"));
	 //    System.out.println(wb);
	    }
br.close();


		
	}
	catch(Exception e)
	{ 
		e.printStackTrace();
	}
	
	

}
}
