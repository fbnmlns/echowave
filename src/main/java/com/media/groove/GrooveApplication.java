package com.media.groove;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GrooveApplication {

	public static void main(String[] args) {
		Application.launch(MediaPlayerApplication.class, args);
	}

}
