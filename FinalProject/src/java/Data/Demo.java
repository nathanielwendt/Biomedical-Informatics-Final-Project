package Data;
/*
 * @author: Team Go-Go-Gadget-Girls
 */
public class Demo {

    private DemoIO dio;
    private int numPatients;
    private double avgAge;
    private double stdevAge;
    private double percentMale;

    public Demo()
    {
        this.dio = new DemoIO();
        this.numPatients = this.dio.processNumPatients();
        this.avgAge = this.dio.processAvgAge();
        this.stdevAge = this.dio.processStdevAge();
        this.percentMale = this.dio.processPercentMale();
    }

    //returns the number of patients
    public int getNumPatients()
    {   
        return this.numPatients;
    }

    //returns the average age
    public double getAvgAge()
    {      
        return this.avgAge;
    }

    //returns the standard deviation of the age
    public double getStdevAge()
    {
        return this.stdevAge;
    }

    //returns the percentage of entries that are male
    public double getPercentMale()
    {
        return this.percentMale;
    }
}
