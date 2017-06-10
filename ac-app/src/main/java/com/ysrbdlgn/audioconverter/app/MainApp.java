package com.ysrbdlgn.audioconverter.app;

import com.ysrbdlgn.audioconverter.frontend.ui.AudioConverterApp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ysrbdlgn on 10-Jun-17.
 */
public class MainApp {

    public static void main( String[] args ) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {"/spring/application-context-app.xml"});

        AudioConverterApp stage = (AudioConverterApp) applicationContext.getBean("audioConverterAppBean");
        stage.run(args);

    }
}
