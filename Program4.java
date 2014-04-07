/**
Date: 3/31/2006
Program created by Nader Kishek for program 4 CIS 2353 .
Filename: Program4.java
This application processes the two data files sections.txt and register.txt,
to show which classes students have signed up for,for the current semester.
The program will report the list of available sections, then report the name 
of each student and the sections they registered for.
The program will also report errors when there are duplicate sections, 
any attempts to register for a section that does not exist, or when a student tries 
to register again in  the same section.
*/

import java.io.*;
import java.util.*;

/**
Class Program4 contains the main method.
*/
public class Program4
{
	public static void main(String [] args) 
	{
  		 
		try
		{
			BufferedReader brSections = new BufferedReader(new FileReader("sections.txt"));
         BufferedReader brReg = new BufferedReader(new FileReader("register.txt"));
      	
			/** Creating TreeSet sections to store the sections data.*/
			MyTreeSet<String> sections = new MyTreeSet<String>();
			
			/** Creating TreeMap students, to store student names and the sections registered.*/
      	MyTreeMap<String, TreeSet<String>> students = 
														new MyTreeMap<String, TreeSet<String>>();
  
					
			System.out.println("Loading Sections...."); 
			/** reading and tokenizing the file sections.txt one line at a time.**/
		 	String sline = brSections.readLine();
			while(sline != null)
			{
					StringTokenizer sts = new StringTokenizer(sline);
					
					while(sts.hasMoreTokens())
					{
						String dept = sts.nextToken().trim();
						String course = sts.nextToken().trim();
						String code = sts.nextToken().trim();
						sts.nextToken();
						sts.nextToken();
						sts.nextToken();
						String section = (dept+"-"+course+"-"+code); // one dash-connected string.
					
						if(!sections.contains(section))
						   sections.add(section);// populate the TreeSet sections
						else
						{
							System.out.println("*** IGNORED -- Duplicate section of "+section + "\n");
						}

					}//inner loop
					
   	  				 sline = brSections.readLine();
						 
			}//outer loop
			
			/** print the TreeSet elements */
			System.out.println("Sections:");
			System.out.println(sections +"\n");
				
				
			/** processing register.txt and tokenizing it one line at a time */
			System.out.println("Loading registration data....");
			String rline = brReg.readLine();
			while(rline != null)
			{
				StringTokenizer str = new StringTokenizer(rline);
				while(str.hasMoreTokens())
				{
					String student = str.nextToken().trim();
					String r_dept = str.nextToken().trim();
					String r_course = str.nextToken().trim();
					String r_code = str.nextToken().trim();
					str.nextToken();
					
					/** combining the section data into one dash-connected string */
					String r_section = (r_dept+"-"+r_course+"-"+r_code);
					
					/** creating a new TreeSet "rSections" for each student to store the sections
					in which each student is enrolled
					*/
					TreeSet<String> rSections = new TreeSet<String>();
					
					if(!students.containsKey(student))
					{
						/** verify that the section exists in the "sections" TreeSet,
						and if so add it to the new TreeSet for the student mapped to his 
						name, or if not found , display an error message.*/						
						if(sections.contains(r_section))
						{
							rSections.add(r_section);
							students.put(student, rSections);
						}
						else
						{
							System.out.println("*** IGNORED Section "+ r_section+ " for " + student+ " does not exist");
								
						}
					}
					else
					{
						/** verify that the section has not been registered for by the student
						already, and if already registered  display an error message.*/			
						if(!students.get(student).contains(r_section))
						
							students.get(student).add(r_section);
							
						else
						
							System.out.println("*** ERROR -- student "+ student+ " is already signed up for " + r_section+ "\n" );
						
					}
				}//inner loop
				
     				 rline = brReg.readLine();
					 
  		  	}//outer loop
			
			/** print TreeMap elements and display the student enrollment data  */
			System.out.println("Student enrollment:");	
			System.out.println(students);
			
		}
      catch (IOException e)
      {
         System.out.println("Error: " + e);
		}
	}
}


/**
Class MyTreeSet extending TreeSet to override the
toString method to yield the output we desire instead of the default.
*/

class MyTreeSet<E> extends TreeSet<E>
{
	public String toString()
	{	
		String ret = "";
		
		for (E elem : this)
		{
			ret += elem.toString();
			ret += "\n";
		}
		return ret;
	}
}

/**
Class MyTreeMap extending TreeMap to override the
toString method to yield the output we desire instead of the default.
*/
class MyTreeMap<K,V> extends TreeMap<K,V>
{
	public String toString()
	{	
		String ret = "";
		
		Set<K> keys = this.keySet();
		
		for (K key : keys)
		{
			ret += key;
			ret += " is taking: ";
			ret += this.get(key).toString();
			ret += "\n";
		}
		return ret;
	}		
}