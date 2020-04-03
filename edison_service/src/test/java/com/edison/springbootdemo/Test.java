package com.edison.springbootdemo;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;

public class Test {
    public static void main(String[] args) throws Exception {
        ClassLoader cl=Test.class.getClassLoader();
        Enumeration<URL> urls=cl.getResources("META-INF/services/java.sql.Driver");

        while(urls.hasMoreElements()) {
            URL url=urls.nextElement();
            System.out.println(url.getPath());
            InputStream fi = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(fi));

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
            fi.close();
        }


    }
}
