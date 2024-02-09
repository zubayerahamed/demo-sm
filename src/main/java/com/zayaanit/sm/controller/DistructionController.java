package com.zayaanit.sm.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zayaanit.sm.config.AppConfig;
import com.zayaanit.sm.enums.ServiceStatus;

@RestController
@RequestMapping("/tomcat")
public class DistructionController {

	@Autowired
	private AppConfig appConfig;

	@GetMapping("/status")
	public String status() {
		String message = "";
		Process process = null;
		try {
			process = new ProcessBuilder("C:\\Windows\\System32\\sc.exe", "query", appConfig.getTomcatServiceName()).start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			String line;
			String scOutput = "";
			while ((line = br.readLine()) != null) {
				scOutput += line + "\n";
			}

			if (scOutput.contains("STATE")) {
				if (scOutput.contains(ServiceStatus.RUNNING.name())) {
					message = ServiceStatus.RUNNING.name();
				} else if (scOutput.contains(ServiceStatus.STOPPED.name())) {
					message = ServiceStatus.STOPPED.name();
				} else if (scOutput.contains(ServiceStatus.START_PENDING.name())) {
					message = ServiceStatus.START_PENDING.name();
				} else if (scOutput.contains(ServiceStatus.STOP_PENDING.name())) {
					message = ServiceStatus.STOP_PENDING.name();
				}
			} else {
				message = ServiceStatus.UNKNOWN.name();
			}

			process.waitFor();

			br.close();
			isr.close();
			is.close();
			process.destroy();
			process = null;

		} catch (IOException | InterruptedException e) {
			message = e.getMessage();
		} finally {
			if (process != null) {
				process.destroy();
				process = null;
			}
		}

		return message;
	}

	@GetMapping("/stop")
	public String stopTomcat() {
		String[] startScript = { "cmd.exe", "/c", "sc", "stop", appConfig.getTomcatServiceName() };
		try {
			Process process = Runtime.getRuntime().exec(startScript);
			int exitCode = process.waitFor();
			return "" + exitCode;
		} catch (IOException | InterruptedException e) {
			return e.getMessage();
		}
	}

	@GetMapping("/start")
	public String startTomcat() {
		String[] startScript = { "cmd.exe", "/c", "sc", "start", appConfig.getTomcatServiceName() };
		try {
			Process process = Runtime.getRuntime().exec(startScript);
			int exitCode = process.waitFor();
			return "" + exitCode;
		} catch (IOException | InterruptedException e) {
			return e.getMessage();
		}
	}
}
