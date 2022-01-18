package com.pachong;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

/**
 * @ClassName DataScrapingUtil
 * @Author weijian
 * @Date 2021/12/17
 */
public class DataScrapingUtil {

    public static void main(String[] args) {
        DataScrapingUtil dataScrapingUtil = new DataScrapingUtil();
        //String html = dataScrapingUtil.httpClientProcess();
        //System.out.println(html);
        // Document parse = Jsoup.parse(html);
        // System.out.println(parse.title());
        dataScrapingUtil.seleniumProcess();


    }

    private String httpClientProcess() {
        String html = "";
        String uri = "www.baidu.com";
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址
        HttpGet request = new HttpGet(uri);
        try {
            request.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.54 Safari/537.36");
            request.setHeader("accept", "application/json, text/javascript, */*; q=0.01");


            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                html = EntityUtils.toString(httpEntity, "utf-8");
            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return html;
    }

    private void seleniumProcess() {

        String uri = "http://quote.eastmoney.com/sh600556.html";

        // 设置 chromedirver 的存放位置
        System.getProperties().setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\chromedriver.exe");

        // 设置浏览器参数
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");//禁用沙箱
        chromeOptions.addArguments("--disable-dev-shm-usage");//禁用开发者shm
        chromeOptions.addArguments("--headless"); //无头浏览器，这样不会打开浏览器窗口
        WebDriver webDriver = new ChromeDriver(chromeOptions);


        while (true) {
            try {
                webDriver.get(uri);
                WebElement webElements = webDriver.findElement(By.id("price9"));
                String stockPrice = webElements.getText();

                WebElement webElements1 = webDriver.findElement(By.id("km1"));
                String stockPrice1 = webElements1.getText();

                WebElement webElements2 = webDriver.findElement(By.id("km2"));
                String stockPrice2 = webElements2.getText();
                System.out.println("股价：" + stockPrice+"  差价："+stockPrice1+"  跌幅："+stockPrice2);

                Thread.sleep(10000);
            } catch (Exception e) {
                webDriver.close();
                e.printStackTrace();
                seleniumProcess();
            }

        }

    }
}
