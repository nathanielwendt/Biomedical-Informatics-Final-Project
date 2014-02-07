package ADRreport;
import JDBCWrapper.*;
import java.util.Vector;
import java.math.*;
import java.text.*;
/*
 * @author: Team Go-Go-Gadget-Girls
 */
public class ADR_IO {

    private double drugcount;
    private double PTcount;
    private double DvRcount;
    private double totalCount;
    private double IC = 0;
    private String URL;
    private String username;
    private String password;
    private Database db;
    private Vector ISR = new Vector(100);

    //establishes the database connection so all methods of this class
    //can use the connection without having to establish their own connection
    public ADR_IO()
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

    //returns the drug count from Drug08q1
    public double getDrugCount(String drug)
    {
        Vector NAMES = new Vector();
        String tblName = "adr";
        Table tbl = new Table(this.db, tblName);
        String criteria = ("select count(distinct isr) as count " +
                            "from adr " +
                            "where DRUGNAME like '" + drug + "';");
        RowSet rs = null;
        NAMES.add("ISR");
        try {
             rs = tbl.executeSQL(criteria);
        } catch (Exception e) {
             System.err.println("problems getting rows: "+ e.toString());
        }

        drugcount = Double.parseDouble(rs.get(0).get("count"));
        return drugcount;
    }

    //returns the number of reactions given an input string
    public double getReactionCount(String PT)
    {
        Vector NAMES = new Vector();
        String tblName = "adr";
        Table tbl = new Table(this.db, tblName);
        String criteria = ("select count(distinct isr) as count1 " +
                            "from adr " +
                            "where PT like '" + PT + "';");
        RowSet rs = null;

        NAMES.add("ISR");
        try {
             rs = tbl.executeSQL(criteria);
        } catch (Exception e) {
             System.err.println("problems getting rows: "+ e.toString());
        }

        PTcount = Double.parseDouble(rs.get(0).get("count1"));
        return PTcount;
    }

    //returns the count of occurence of both an input drugname
    //and an input pt (reaction)
    public double getDvRCount(String drug, String PT)
    {
        Vector NAMES = new Vector();
        //Drug Table
        String tblName = "adr";
        Table tbl = new Table(this.db, tblName);
        String criteria = ("select count(distinct isr) as count2 " +
                            "from adr " +
                            "where PT like '" + PT +
                            "' and DRUGNAME like '" + drug +"';");
        RowSet rs = null;
        NAMES.add("ISR");
        try {
             rs = tbl.executeSQL(criteria);
        } catch (Exception e) {
             System.err.println("problems getting rows: "+ e.toString());
        }
        DvRcount =Double.parseDouble(rs.get(0).get("count2"));
        return DvRcount;
    }

    //returns the total count of entries in our cached adr table
    public double getTotalCount()
    {
        Vector NAMES = new Vector();
        //Drug Table
        String tblName = "adr";
        Table tbl = new Table(this.db, tblName);
        String criteria = ("select count(PT) as count3 " +
                            "from reac08q1;");
        RowSet rs = null;
        NAMES.add("ISR");
        try {
             rs = tbl.executeSQL(criteria);
        } catch (Exception e) {
             System.err.println("problems getting rows: "+ e.toString());
        }

        totalCount = Double.parseDouble(rs.get(0).get("count3"));
        return totalCount;
    }

    //returns the IC value
    //getDVRcount, getReactionCount, getDrugCount must
    //run before getIC in order to populate variables
    public double getIC()
    {
        DecimalFormat df = new DecimalFormat("#.##");
        double PA, PD, X;
        double PT, DRUG, DVR, TOTAL, OUTPUT, FINAL;

        PT = this.PTcount;
        DRUG = this.drugcount;
        DVR = this.DvRcount;
        TOTAL = this.totalCount;

        PA = PT/TOTAL;
        PD = DRUG/TOTAL;
        X = (DVR)/(PA*PD);
        IC = (Math.log(X)/Math.log(2));

        return (Math.pow(IC,3))/1000;
    }

    //returns the IC value
    //overloaded getIC method for use with getTop10
    public double getIC(double PT, double DRUG, double DVR)
    {
        double PA, PD, X;
        double TOTAL;

        TOTAL = getTotalCount();

        PA = PT/TOTAL;
        PD = DRUG/TOTAL;
        X = (DVR)/(PA*PD);
        IC = (Math.log(X)/Math.log(2));

        return (Math.pow(IC,3))/1000;
    }

    //returns a vector containing the top ten reactions to a given drug
    //each entry in the vector will have 3 data items: PT,Count,Association IC
    public Vector getTopTen(String drug)
    {
        Vector top10 = new Vector();
        Vector ordered = new Vector();
        double drugCount;
        Double DVRCount;
        double DVRCountd;
        double PTCount;
        double IC;
        String PT;
        String tblName = "adr";
        Table tbl = new Table(this.db, tblName);
        String criteria = ("select count(distinct ISR) as count, PT " +
                           "from adr " + "where DRUGNAME like '" + drug +
                           "'group by PT " + "order by count desc limit 0,10;");
        RowSet rs = null;

        try {
             rs = tbl.executeSQL(criteria);
        } catch (Exception e) {
             System.err.println("problems getting rows: "+ e.toString());
        }

        //loop through rowset
        drugCount = getDrugCount(drug);
        for(int rowNum = 0; rowNum < rs.length(); rowNum++)
        {
            String sCount = rs.get(rowNum).get("count");
            DVRCount = Double.valueOf(sCount);
            DVRCountd = DVRCount.doubleValue();
            PT = rs.get(rowNum).get("PT");
            PTCount = getReactionCount(PT);
            IC = getIC(PTCount, drugCount, DVRCount);
            top10.addElement(PT);
            top10.addElement(DVRCountd);
            BigDecimal bd = new BigDecimal(IC);
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            top10.addElement(bd);
        }

        return top10;
    }
}