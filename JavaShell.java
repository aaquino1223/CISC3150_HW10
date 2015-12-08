import java.io.*;
import java.util.*;

//Alex Aquino Homework 10
//JavaShell class imitates the command line interface
//of linux and runs programs entered in
//Output to files is available
public class JavaShell {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Vector<String> vec;
		StringTokenizer words;
		String line;
		
		ProcessBuilder p;
		Vector<ProcessThread> hiddenP= new Vector<ProcessThread>();
		Process current;
		BufferedReader output;
		
		System.out.println("----------JAVA SHELL----------");
		while(true) {
			System.out.print(">");
			line = input.nextLine();
			
			//For hw10_Output.txt so that you
			//actually see what i typed in
			System.out.println(line);
			
			if(line.equals("exit")) {
				break;
			}
			
			words = new StringTokenizer(line);
			vec = new Vector<String>();
			while(words.hasMoreTokens()) {
				vec.add(words.nextToken());
			}
			
			p = new ProcessBuilder(vec);
				
			for(int i = 0; i < vec.size(); i++) {
				if(vec.elementAt(i).equals(">") && i + 1 != vec.size()) {
					p.redirectOutput(new File(vec.elementAt(i + 1)));
					break;
				}
			}
			
			if(line.contains("&")) {	
				if(vec.elementAt(0).endsWith("&")) {
					vec.add(0, vec.elementAt(0).substring(0, vec.elementAt(0).length() - 1));
					hiddenP.add(new ProcessThread(p));
				}
				else if(vec.size() >= 2) { //To prevent out of bounds exception
					if(vec.elementAt(0).equals("java") && vec.elementAt(1).endsWith("&")) {
						vec.add(1, vec.elementAt(1).substring(0, vec.elementAt(1).length() - 1));
						hiddenP.add(new ProcessThread(p));
					}
				}
			}
			else if(vec.size() != 0) {
				try {
					current = p.start();
					if((p.redirectOutput().file() == null)) {
						output = new BufferedReader(new InputStreamReader(current.getInputStream()));
						line = output.readLine();
						System.out.println(line != null ? line : "");
					}
				} catch(IOException e) {
					System.out.println("Cannot perform the command specified");
				} 
			}
		}
		
		System.out.println("Bye!");
	}
}