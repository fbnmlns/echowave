package com.media.groove;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrooveApplication {

	public static void main(String[] args) {
		Application.launch(MediaPlayerApplication.class, args);
	}

}
