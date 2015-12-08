import java.util.*;
import java.io.*;

//ProcessThread is a class for those processes
//that are to be immediately returned
public class ProcessThread implements Runnable {
	Thread processThread;
	ProcessBuilder processBuild;
	
	public ProcessThread(ProcessBuilder processBuild) {
		this.processBuild = processBuild;
		processThread = new Thread(this);
		processThread.start();
	}
	
	public void run() {
		try {
			Process current = processBuild.start();
			if((processBuild.redirectOutput().file() == null)) {
				BufferedReader output = new BufferedReader(new InputStreamReader(current.getInputStream()));
				String line = output.readLine();
				System.out.println(line != null ? line : "");
			}
		} catch(IOException e) {
			System.out.println("Cannot perform the command specified");
		}
	}
}