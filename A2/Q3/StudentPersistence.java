import java.io.*;
import java.util.Scanner;

public class StudentPersistence extends Student
{
    public void save()
	{
		try
		{
			PrintWriter writer = new PrintWriter("student.txt", "UTF-8");
			writer.println(bannerID);
			writer.println(firstName);
			writer.println(lastName);
			writer.println(email);
			writer.close();
		}
		catch (Exception e)
		{
			System.out.println("I am a bad programmer that hid an exception.");
		}
	}

	public void load()
	{
		try
		{
			Scanner in = new Scanner(new FileReader("student.txt"));
			bannerID = in.next();
			firstName = in.next();
			lastName = in.next();
			email = in.next();
		}
		catch (Exception e)
		{
			System.out.println("I am a bad programmer that hid an exception.");
		}
	}
}