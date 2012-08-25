package com.yama;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Deloitte
 *
 * User: samurudkar
 * Date: 8/7/12
 * Time: 11:59 AM
 *
 * Base page class to load Page Yaml file and get selenium fields of page
 *
 */
public class Page {
    public Map<String, Object> m_pagefile;
    WebDriver m_driver;

    protected String get_field(String s) throws InvalidPageFileFormatException{
        List<String> sl = new ArrayList<String>( Arrays.asList(s.split("\\.")));

        Map<String, Object> temp = m_pagefile;
        Object tmp_val = null;
        for (String field: sl){
            tmp_val = null;
            if (temp instanceof Map )
                tmp_val = temp.get(field);

            if( tmp_val == null)
                throw new InvalidPageFileFormatException("Invalid page definition file format. " + s + " field not found.");
            if ( tmp_val instanceof Map )
                temp = (Map<String, Object>) tmp_val;
        }
        String ret_val= null;
        ret_val = (String)tmp_val;
        if (ret_val== null )
            throw new InvalidPageFileFormatException("Invalid page definition file format. " + s + " field not found.");
        return ret_val;
    }

    protected String get_component(String s) throws InvalidPageFileFormatException {
        return get_field("Components." + s);
    }

    protected String get_page(String s) throws InvalidPageFileFormatException{
        return get_field("Page." + s);
    }

    public WebElement get(String s){
        try{
            String xpath = get_component(s);
            return m_driver.findElement(By.xpath(xpath));
        }
        catch (InvalidPageFileFormatException e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean validate(){
        String title = m_driver.getTitle();

        String expected_title;
        try{
            expected_title = get_page("title");
            if (expected_title == null)
                return false;
        }
        catch (InvalidPageFileFormatException e){
            e.printStackTrace();
            return false;
        }
        if (expected_title.equals(title))
            return true;
        return false;
    }

    public static Page create (WebDriver driver, String ymlfile){
        Yaml yaml = new Yaml();

        Page page = new Page();
        page.m_driver = driver;
        try{
            InputStream in = Page.class.getResourceAsStream(ymlfile);
            page.m_pagefile = (Map<String,Object>)yaml.load(in);
        }
        catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return page;
    }

    public void load() throws Exception {
        String uri = get_page("uri");
        System.out.println("uri: " + uri);
        m_driver.get(uri);
    }
}
