package Data;
import JDBCWrapper.*;
import static java.lang.Math.*;
/*
 * @author: Team Go-Go-Gadget-Girls
 */
public class DemoIO {

    private double averageAge; //used for easier calculation of stdDev
    private boolean isaverageCalc;
    private String URL;
    private String username;
    private String password;
    private Database db;

    //constructor for DemoIO Class.  Sets up data members with database
    //object connected to database so subsequent methods can use this connection
    //without having to reconnect
    public DemoIO()
    {
        this.URL = "jdbc:mysql://localhost:3306/MedicalData" +
                     "?zeroDateTimeBehavior=convertToNull";
        this.username = "root";
        this.password = "cpen436";
        // Load database driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Failed to load JDBC/ODBC driver.");
            return;
        }
        this.db = new Database(URL, username, password);
    }

    //processes the number of patients in the demo08q1 table
    //returns an int for the number of patients
    public int processNumPatients()
    {
        int numPatients = -1; //will make numPatients -1 if no rowset is found

        String tblName = new String("demo08q1");
        Table tbl = new Table(this.db, tblName);
        String criteria = new String("select Count(ISR) as 'count' from Demo08q1");
        RowSet rs = null;
        try {
            rs = tbl.executeSQL(criteria);
        } catch (Exception e) {
            System.err.println("DemoIO -- problems executing SQL statement: "+ e.toString());
        }
        Row row = new Row();
        if(rs != null)
            numPatients = Integer.parseInt( rs.get(0).get("count") );
        else
            System.out.println("DemoIO -- Count could not be found.");

        return numPatients;
    }

    //processes the average age of people in the demo08q1 table
    //returns a double for the average age
    public double processAvgAge()
    {
        double sum = 0; String code; double age = 0; int count = 0;

        String tblName = new String("demo08q1");
        Table tbl = new Table(this.db, tblName);
        String criteria = new String("SELECT Age, Age_COD FROM DEMO08Q1 WHERE AGE != 0");
        RowSet rs = null;
        try {
            rs = tbl.executeSQL(criteria);
        } catch (Exception e) {
            System.err.println("DemoIO -- problems executing SQL statement: "+ e.toString());
        }
        try{
            Row row = new Row();
            for(int i = 0; i < rs.length(); i++)
            {
                row = rs.get(i);
                age = Integer.parseInt(row.get("AGE"));
                code = rs.get(i).get("AGE_COD");

                age = convertAgeCode(code,age);
                sum += age;
                count++;
            }
        }catch(Exception e){
            System.err.println("Failed to get field data "+ e);
        }

        this.averageAge = sum / count;
        this.isaverageCalc = true;
        return ( this.averageAge );
    }

    //processes the standard deviation of the age in the demo08q1 table
    //returns a double for the standard deviation of the age
    public double processStdevAge()
    {
        //if user has not calculated average age first, use other method
        if(!this.isaverageCalc)
            this.averageAge = processAvgAge();

        double stdsum = 0f; int count = 0; double age = 0; String code;
        String tblName = new String("demo08q1");
        Table tbl = new Table(this.db, tblName);
        String criteria = new String("SELECT Age, Age_COD FROM DEMO08Q1 WHERE AGE != 0");
        RowSet rs = null;
        try {
            rs = tbl.executeSQL(criteria);
        } catch (Exception e) {
            System.err.println("DemoIO -- problems executing SQL statement: "+ e.toString());
        }
        try{
            Row row = new Row();
            for(int i = 0; i < rs.length(); i++)
            {
                row = rs.get(i);
                age = Integer.parseInt(row.get("AGE"));
                code = rs.get(i).get("AGE_COD");

                age = convertAgeCode(code,age);
                stdsum += pow(this.averageAge - age, 2);
                count++;
            }
        }catch(Exception e){
            System.err.println("Failed to get field data "+ e);
        }
        return sqrt(stdsum/count);

    }

    //processes the percentage of Males in the Demo08q1 database
    //returns a double for the percentage of male
    public double processPercentMale()
    {
        double maleCount = 0;
        double femaleCount = 0;
        String gender;

        String tblName = new String("demo08q1");
        Table tbl = new Table(this.db, tblName);
        String criteria = new String("SELECT GNDR_COD FROM DEMO08Q1 WHERE GNDR_COD != ''");
        RowSet rs = null;
        try {
            rs = tbl.executeSQL(criteria);
        } catch (Exception e) {
            System.err.println("DemoIO -- problems executing SQL statement: "+ e.toString());
        }
        try{
            Row row = new Row();
            for(int i = 0; i < rs.length(); i++)
            {
                row = rs.get(i);
                gender = row.get("GNDR_COD").replaceAll("\\s","");;
                if(gender.equals("M"))
                    maleCount++;
                else if(gender.equals("F"))
                    femaleCount++;
            }
        }catch(Exception e){
            System.err.println("Failed to get field data "+ e);
        }
        return( (maleCount / (maleCount + femaleCount)) * 100 ); //return as percent
    }

    //converts the incoming age to the proper age according to the databases
    //code value that is passed in as a string
    //returns a double for the age code adjusted age
    public double convertAgeCode(String code, double age)
    {
        if(code.equalsIgnoreCase("DEC"))
            age *= 10;
        else if(code.equalsIgnoreCase("MON"))
            age /= 12;
         else if(code.equalsIgnoreCase("WK"))
            age /= 52;
        else if(code.equalsIgnoreCase("DY"))
            age /= 365;
        else if(code.equalsIgnoreCase("HR"))
            age /= (365*24);
        return age;
    }
}
