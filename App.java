import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

class Operation
{
    Connection c = null;
	Statement stmt = null;
	//System.out.println("hello");
	public void fun1()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the company name to view its interview process:");
		String s=sc.nextLine();
		try {
		    Class.forName("org.postgresql.Driver");
		    c=DriverManager
		       .getConnection("jdbc:postgresql://10.100.71.21:5432/201701194",
		       "201701194", "7990918397");
		    System.out.println("Connected:");
		    c.setAutoCommit(false);
			stmt = c.createStatement();
			stmt.execute("set search_path to finalproject;");
			String sql="select companyid from company where companyname="+s;
			//System.out.println(sql);
	        ResultSet rs = stmt.executeQuery(sql);
	        int cid=0;
	        while (rs.next()) {
	        	cid=rs.getInt("companyid");
	        }
	       
	        sql="select profilename,mincpi,ctc from company natural join offers natural join jobprofile where companyid="+Integer.toString(cid);
	        rs = stmt.executeQuery(sql);
	        double cpi=0.0,ctc=0.0;
	        String ss[]=new String[500];
	        int j=0;
	        while (rs.next()) {
	        	cpi=rs.getDouble("mincpi");
	        	ctc=rs.getDouble("ctc");
	        	ss[j++]=rs.getString("profilename");
	        }
	        System.out.println("Company name: "+s);
	        System.out.println("Profiles:");
	        for(int i=0;i<j;i++)
	        	System.out.println(ss[i]);
	        System.out.println("RequiredCPI: "+cpi);
	        System.out.println("CTC: "+ctc);
	        
	        sql="select profilename,roundid,roundname,roundtype,platformname,noofround from (((company natural join offers natural join jobprofile) as r1 join interviewprocessof on(companyid=cid and profileid=pid)) as r2 join interviewprocess on(rid=roundid)) where companyid="+Integer.toString(cid);
	        //System.out.println(sql);
	        rs = stmt.executeQuery(sql);
	        System.out.println("InterviewProcess: ");
	        while(rs.next())
	        {
	        	System.out.println(rs.getString("profilename")+"  "+rs.getString("roundid")+"  "+rs.getString("roundname")+"  "+rs.getString("roundtype")+"  "+rs.getString("platformname")+"  "+rs.getInt("noofround"));
	        }
	        sql="select requiredskill from offers join skill on(companyid=cid and profileid=pid) where companyid="+Integer.toString(cid);
	        rs=stmt.executeQuery(sql);
	        System.out.println("Required Skill that you needed to clear the interview of this company:");
	        while(rs.next())
	        {
	        	System.out.println(rs.getString("requiredskill"));
	        }
	        System.out.println("To get more insight you can contact following students who have cleared interview of this company in past!");
	        sql="select studentname,emailid,contactno,cpi,progname,yr from student natural join selected where cid="+Integer.toString(cid);
	        rs=stmt.executeQuery(sql);
	        while(rs.next())
	        {
	        	System.out.println(rs.getString("studentname")+"  "+rs.getString("emailid")+" "+rs.getString("contactno")+" "+rs.getDouble("cpi")+" "+rs.getString("progname")+" "+rs.getInt("yr"));
	        }    
	        rs.close();
	        stmt.close();
	        c.close();
	        
	        
		 } catch (Exception e) {
		    e.printStackTrace();
		    System.err.println(e.getClass().getName()+": "+e.getMessage());
		    System.exit(0);
		 }
		
	}
	public void fun2()
	{
		Scanner sc=new Scanner(System.in);
		try {
		    Class.forName("org.postgresql.Driver");
		    c=DriverManager
		       .getConnection("jdbc:postgresql://10.100.71.21:5432/201701194",
		       "201701194", "7990918397");
		    System.out.println("Connected:");
		    c.setAutoCommit(false);
			stmt = c.createStatement();
			stmt.execute("set search_path to finalproject;");
			String sql;
			//System.out.println(sql);
			ResultSet rs=null;
			System.out.println("Which type of Programming Questions do you want to practice?");
			System.out.println("Enter the question tags: press Q to exit:");
			while(true)
			{
				String s=sc.nextLine();
				if(s.equals("Q")==true)
					break;
				sql="select qid,problemname,platformname,url from programmingquestions natural join questions natural join onlineplatform  where tag="+s;
				rs=stmt.executeQuery(sql);
				System.out.println();
				while(rs.next())
				{
					System.out.println(rs.getString("qid")+"   "+rs.getString("problemname")+"   "+rs.getString("platformname")+" "+rs.getString("url"));
				}
				System.out.println();
			}
			System.out.println("Enter the CS subject name to practice its subjective question:");
			String s=sc.nextLine();
			System.out.println();
			sql="select qid,problemname from subjectivequestions natural join questions where tag="+s;
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
			}
			
	        rs.close();
	        stmt.close();
	        c.close(); 
		 } catch (Exception e) {
		    e.printStackTrace();
		    System.err.println(e.getClass().getName()+": "+e.getMessage());
		    System.exit(0);
		 }
		
	}
	public void fun3()
	{
		Scanner sc=new Scanner(System.in);
		try {
		    Class.forName("org.postgresql.Driver");
		    c=DriverManager
		       .getConnection("jdbc:postgresql://10.100.71.21:5432/201701194",
		       "201701194", "7990918397");
		    //System.out.println("Connected:");
		    c.setAutoCommit(false);
			stmt = c.createStatement();
			stmt.execute("set search_path to finalproject;");
			String sql=null;
			//System.out.println(sql);
			
			
			System.out.println("Enter your details and the company's details for sharing your interviewexperiance:");
			System.out.println();
			System.out.println("(id,name,program,your cpi,Email,ContactNo,year,CompanyName,profilename,cpiCriteria,ctc)");
			String tem=sc.nextLine();
			
			sql="insert into selected_students(studentid,studentname,progname,cpi,emailid,contactno,yr,companyname,profilename,mincpi,ctc) values"+tem;
			
           // sql="insert into company (companyid,companyname) values(34,'LTI')";		
			
			//System.out.println(sql);
			stmt=c.prepareCall(sql);
			System.out.println("Share your projects details which is done by you?");
			System.out.println();
			System.out.println("(ProjectId,ProjectName,subjectname,refernce,teamsize) press Q to exit:");
			int temp;
			while(true)
			{
				String stt=sc.nextLine();
				if (stt.equals("Q")==true)
					break;
				sql="insert into project(projectid,projectname,subjectname,reference,teamsize) values"+stt;
				temp=stmt.executeUpdate(sql);
				System.out.println("T: "+temp);
			}
	        stmt.close();
	        c.close(); 
		 } catch (Exception e) {
		    e.printStackTrace();
		    System.err.println(e.getClass().getName()+": "+e.getMessage());
		    System.exit(0);
		 }
	}
}

public class App {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("!---------------------!Hello!----------------------!");
		System.out.println();
		int q=0;
		while(q!=4) {	
		System.out.println("What do you want do?");
		System.out.println("1.Want to know the company's details and interview process? press 1");
		System.out.println("2.Want to know the company's interview questions asked in past? press 2");
		System.out.println("3.Share your interview expirence to us? press 3");
		System.out.println("4.Exit press 4");
		System.out.println("<-------------------------------------------------------------------------->");
		Scanner in=new Scanner(System.in);
		q=in.nextInt();
		Operation o=new Operation();
			if(q==1)
			{
				o.fun1();
				System.out.println("<-------------------------------------------------------------------------->");
			}
			else if(q==2)
			{
				o.fun2(); // Questions and Project Details
				System.out.println("<-------------------------------------------------------------------------->");
			}
			else if(q==3)
			{
				o.fun3();// update
				System.out.println("<-------------------------------------------------------------------------->");
			}
			else
			     System.out.println("!-------------------------!Bye!-------------------------!");
		
		}
		
	}
}
