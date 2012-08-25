package com.dt.test.framework.ui;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;

/**
 * Deloitte
 * <p/>
 * User: samurudkar
 * Date: 8/13/12
 * Time: 10:24 AM
 */
public class BaseUITest {
    Page dtcPage;

    protected void setupDriver() throws Exception {
        m_driver = new FirefoxDriver();
    }

    @BeforeClass
    public void BeforeClassMethod(){
        setupDriver();
    }
}
