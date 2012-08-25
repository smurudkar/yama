package com.yama;

import com.yama.Page;
import junit.framework.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: samurudkar
 * Date: 8/9/12
 * Time: 12:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestGoogle {
    private WebDriver m_driver;
    Page m_page;

    @BeforeClass
    public void setupDriver() throws Exception {
        System.out.println("Creating web driver");
        m_driver = new FirefoxDriver();
        System.out.println("Creating web page");
    }

    @Test
    public void searchGoogle() throws Exception {
        m_page = Page.create(m_driver, "/uimap/google.yml");
        Assert.assertNotNull(m_page);
        m_page.load();
        m_page.get("search.search_field").sendKeys("Hello");
        m_page.get("search.search_button").click();
    }

    @AfterClass
    public void deleteDriver(){
        m_driver.quit();
        m_driver = null;
    }
}
