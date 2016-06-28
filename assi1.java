import java.io.File;
//import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;


public class assi1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException
	{
		// TODO Auto-generated method stub
		File ffBinary=new File("/home/nawazakhtar/Downloads/firefox/firefox");
		FirefoxBinary ffBinaryFile=new FirefoxBinary(ffBinary);
		FirefoxProfile ffProfile=new FirefoxProfile();
		WebDriver driver= new FirefoxDriver(ffBinaryFile, ffProfile);
		driver.get("http://10.0.1.86/tatoc/basic/grid/gate");
		driver.findElement(By.className("greenbox")).click();
		Thread.sleep(3000);
		driver.switchTo().frame("main");
		String box1 = driver.findElement(By.id("answer")).getAttribute("class");
		System.out.println(box1);
		
		driver.switchTo().frame("child");
		String box2 = driver.findElement(By.id("answer")).getAttribute("class");
		System.out.println(box2);
		
		while(!box1.equals(box2))
		
		{
		driver.switchTo().defaultContent();
		driver.switchTo().frame("main");
		Thread.sleep(1000);
		driver.findElement(By.linkText("Repaint Box 2")).click();
		driver.switchTo().frame("child");
		box2 = driver.findElement(By.id("answer")).getAttribute("class");
		
		
	}
		driver.switchTo().defaultContent();
		driver.switchTo().frame("main");
		driver.findElement(By.linkText("Proceed")).click();
		
		WebElement element = driver.findElement(By.id("dragbox"));
		WebElement target = driver.findElement(By.id("dropbox"));

		(new Actions(driver)).dragAndDrop(element, target).perform();
		Thread.sleep(1000);
			driver.findElement(By.linkText("Proceed")).click();
		
		driver.findElement(By.linkText("Launch Popup Window")).click();
		String handle = driver.getWindowHandles().toArray()[1].toString();
		driver.switchTo().window(handle);
		driver.findElement(By.id("name")).sendKeys(new String[]{"Nawaz Akhtar"});
		driver.findElement(By.id("submit")).click();
		handle = driver.getWindowHandles().toArray()[0].toString();
		driver.switchTo().window(handle);
		driver.findElement(By.linkText("Proceed")).click();
//clicking on generate token
		driver.findElement(By.linkText("Generate Token")).click();
		
		String token =	driver.findElement(By.id("token")).getText();
		String[] arr=token.split(": ");
		Cookie cookie=new Cookie("Token",arr[1]);
		driver.manage().addCookie(cookie);
		driver.findElement(By.linkText("Proceed")).click();

		
	}
}
