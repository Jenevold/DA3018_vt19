package courseinfo;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class CourseInfo {

	public static void main(String[] args) {
		BinarySearchTree courses = new BinarySearchTree();
		if (0 < args.length) {
			File inFile = new File(args[0]);

			BufferedReader br = null;

			try {

				br = new BufferedReader(new FileReader(inFile));
				String line;
				while ((line = br.readLine()) != null) {
					String[] info = new String[3];
					info = line.split(",");
					String cc = info[0];
					String cn = info[1];
					Double cp = Double.parseDouble(info[2]);
					courses.insert(cc, cn, cp);

				}

			}

			catch (IOException e) {
				e.printStackTrace();
			}


		if (1 == args.length) {

			//Här skriver vi vad som ska hända om man bara anger en fil

			}
		if (2 == args.length) {

			// Här skriver vi vad som ska hända om vi anger en fil och en kurskod

		}
		

		/*
		courses.insert("DA3018", "Computer Science", 7.5);
		courses.insert("MM2001", "Matematik I", 30.0);
		courses.insert("DA2004", "Programmeringsteknik", 7.5);
		courses.insert("MM5014", "Numerisk analys", 7.5);
		*/

		/*
		courses.insert("10", "Numerisk analys", 7.5);
		courses.insert("22", "Numerisk analys", 7.5);
		courses.insert("14", "Numerisk analys", 7.5);
		courses.insert("30", "Numerisk analys", 7.5);
		courses.insert("24", "Numerisk analys", 7.5);
		courses.insert("35", "Numerisk analys", 7.5);
		courses.insert("32", "Numerisk analys", 7.5);
		courses.insert("33", "Numerisk analys", 7.5);
		courses.insert("37", "Numerisk analys", 7.5);
		courses.insert("36", "Numerisk analys", 7.5);
		courses.insert("34", "Numerisk analys", 7.5);
		courses.insert("25", "Numerisk analys", 7.5);

		for (BinarySearchTree.BSTNode e : courses) {
			System.out.println(e.getCourseCode());
		}
		*/

		/*
		int n = courses.size();
		System.out.printf("There are %d courses in the database.\n", n);
			
		try {
			String test = courses.find("MM5014").getCourseName(); //test för att se output om man söker ågot som inte finns
			System.out.printf("Name: %s\n", test);
			String name = courses.find("MM2001").getCourseName();
			String code = courses.find("MM2001").getCourseCode();
			Double credit = courses.find("MM2001").getCredits();
			System.out.printf("Name: %s\n", name);
			System.out.println("cousecode: "+ code);
			System.out.println("credits: " + credit);
			String name2 = courses.find("DA3018").getCourseName();
			System.out.printf("Name: %s\n", name2);
			courses.remove("30");
			int t = courses.size();
			System.out.println(t);
			courses.remove("14");
			courses.remove("10");

			courses.remove("399");

			BinarySearchTree.BSTNode test = courses.find("22").getLeftChild();
			System.out.println(test);
			int g = courses.size();
			System.out.println(g);

			}
			catch (NullPointerException e)
			{
				System.out.println("Not in tree");
			}

		 */
		
	}

}}
