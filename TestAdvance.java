



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import java.net.HttpURLConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriver;


public class  TestAdvance {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws SQLException, InterruptedException, IOException, JSONException
	{
		// TODO Auto-generated method stub
		File ffBinary=new File("/home/nawazakhtar/Downloads/firefox/firefox");
		FirefoxBinary ffBinaryFile=new FirefoxBinary(ffBinary);
		FirefoxProfile ffProfile=new FirefoxProfile();
		WebDriver driver= new FirefoxDriver(ffBinaryFile, ffProfile);
		//open the browser
		driver.get("http://10.0.1.86/tatoc/advanced/hover/menu");
		Actions action = new Actions(driver);
		WebElement mainmenu = driver.findElement(By.className("menutitle"));
		action.moveToElement(mainmenu);

		WebElement subMenu = driver.findElement(By.xpath("html/body/div/div[2]/div[2]/span[5]"));
		action.moveToElement(subMenu);
		action.click().build().perform();
		//using Mysql
		Thread.sleep(2000);
		String symbol = driver.findElement(By.id("symboldisplay")).getText();
		System.out.println(symbol);
		Connection con=null;
		PreparedStatement pstmt= null;
		ResultSet rs=null;
		String id=null;
		String name=null;
		String passkey= null;
		try
		{
		Class.forName("com.mysql.jdbc.Driver");  
		  
		 con=DriverManager.getConnection("jdbc:mysql://10.0.1.86/tatoc","tatocuser","tatoc01");  
		
		

			pstmt= con.prepareStatement("select id from identity where symbol=?;");
			pstmt.setString(1, symbol);
			rs= pstmt.executeQuery();
			if(rs.next()){
				id= rs.getString("id");
			}
			System.out.println(id);
			 int identity= Integer.parseInt(id);
				rs.close();
				pstmt.close();
				pstmt= con.prepareStatement("select name,passkey from credentials where id=?;");
				pstmt.setInt(1, identity);
				rs= pstmt.executeQuery();
				
				if(rs.next()){
					name= rs.getString("name");
					passkey= rs.getString("passkey");
				}
				rs.close();
				pstmt.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			finally {
				if(rs!=null){
					rs.close();
			}
			if(pstmt!=null){
					pstmt.close();
				}
				if(con!=null){
					con.close();
				}
		}
			
			driver.findElement(By.cssSelector("#name")).sendKeys(name);
			driver.findElement(By.cssSelector("#passkey")).sendKeys(passkey);
			driver.findElement(By.cssSelector("#submit")).click();
			
		//Playing a video
			 JavascriptExecutor js = (JavascriptExecutor) driver;
		     //play video
			 js .executeScript("player.play()");
			 Thread.sleep(25000);
			 driver.findElement(By.linkText("Proceed")).click();

         //After playing video
		     driver.get("http://10.0.1.86/tatoc/advanced/rest");
		    String token = driver.findElement(By.id("session_id")).getText();
			String ur = "http://10.0.1.86/tatoc/advanced/rest/service/token/";
			
			ur  += token.substring(12);
			System.out.println(ur);
			
			
			
			URL url =new URL(ur);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
		    conn.setRequestProperty("Accept", "application/json");
		    if (conn.getResponseCode() != 200) {
		    	throw new RuntimeException("HTTP error code" + conn.getResponseCode());
		    	}
		    Scanner scan = new Scanner(url.openStream());
		    String entireResponse = new String();
		    while (scan.hasNext())
		    	entireResponse += scan.nextLine();

		    	System.out.println("Response :" + entireResponse);

		    	scan.close();
		    	JSONObject obj = new JSONObject(entireResponse);
		    	/*JSONParser parser = new JSONParser();
				JSONObject object= (JSONObject) parser.parse(str);
				String token= (String) object.get("token");
				*/
		    	System.out.println("Token: "+token);
		    	String responseCode = obj.getString("token");
		    	System.out.println("status :" + responseCode);
		    	URL u = new URL("http://10.0.1.86/tatoc/advanced/rest/service/register");
		    	 conn = (HttpURLConnection) u.openConnection();
		    	conn.setDoOutput(true);
		    	System.out.println("creating a new connection to enter information using post method");
				conn.setRequestMethod("POST");
			
			    conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			    String words[] = entireResponse.split(",");
			    String words1[] = words[0].split(":");
				System.out.println(token.substring(12));
				System.out.println(words1[1]);
				token=token.substring(12);
				String token1 = words1[1].substring(1,words1[1].length()-1).trim();
				String urlParameters = "id="+token+"&signature="+token1+"&allow_access=1";
				System.out.println(urlParameters);
				System.out.println("Token is: ");
				System.out.println(token1);
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
				
				int responseCode1 = conn.getResponseCode();
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + urlParameters);
				System.out.println("Response Code : " + responseCode1);
			//	conn.
				 conn.disconnect();
					driver.findElement(By.linkText("Proceed")).click();
					//download file task
					 driver.findElement(By.linkText("Download File")).click();
				        Thread.sleep(5000);
				        BufferedReader br = null;
				        String strng=null, sCurrentLine;
				        try 
				        {
				            int i=0;
				            br = new BufferedReader(new FileReader("file_handle_test.dat"));
				            while ((sCurrentLine = br.readLine()) != null) 
				            {
				                if(i==2)
				                    strng = sCurrentLine;
				                i++;
				            }
				        }
				        catch (IOException e) 
				        {
				            e.printStackTrace();
				        } 
				        strng = strng.substring(11,strng.length());
				        driver.findElement(By.id("signature")).sendKeys(strng);
				        driver.findElement(By.className("submit")).click();







			}
			
			
		}
	
	





