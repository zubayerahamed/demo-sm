package com.zayaanit.sm;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DistructionController {

	@GetMapping("/stop")
	public String stopTomcat(){
		String[] startScript = { "cmd.exe", "/c", "sc", "stop", "Tomcat9" };
		try {
			Process process = Runtime.getRuntime().exec(startScript);
			int exitCode = process.waitFor();
			return "stopped with exit code : " + exitCode;
		} catch (IOException | InterruptedException e) {
			return e.getMessage();
		}
	}

	@GetMapping("/start")
	public String startTomcat(){
		String[] startScript = { "cmd.exe", "/c", "sc", "start", "Tomcat9" };
		try {
			Process process = Runtime.getRuntime().exec(startScript);
			int exitCode = process.waitFor();
			return "started with exit code : " + exitCode;
		} catch (IOException | InterruptedException e) {
			return e.getMessage();
		}
	}
}
