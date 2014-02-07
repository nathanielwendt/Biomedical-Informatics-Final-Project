package ADRreport;
import java.util.Vector;

public class ADR {
    private double drugcount;
    private double PTcount;
    private double DvRcount;
    private double totalCount;
    private double IC;
    private Vector top10;
    private ADR_IO pio;

    //overloaded constructor for when a drug name and reaction are given
    public ADR(String drug, String PT)
    {
        this.pio = new ADR_IO();
        this.drugcount = pio.getDrugCount(drug);
        this.PTcount = pio.getReactionCount(PT);
        this.DvRcount = pio.getDvRCount(drug, PT);
        this.totalCount = pio.getTotalCount();
        //this.top10 = pio.getTopTen(drug);
        this.IC = pio.getIC();
    }

    //overloaded constructor where only a drug name is given
    public ADR(String drug)
    {
        this.pio = new ADR_IO();
        this.top10 = pio.getTopTen(drug);
        this.IC = pio.getIC();
    }

    //returns the drug count
    public double getDrugCount()
    {
        return this.drugcount;
    }

    //returns the reaction count
    public double getReactionCount()
    {
        return this.PTcount;
    }

    //returns the dvr count
    public double getDvRCount()
    {
        return this.DvRcount;
    }

    //gets the total count of entries
    public double getTotalCount()
    {
        return this.totalCount;
    }

    //returns the IC value
    public double getIC()
    {
        return this.IC;
    }

    //returns a vector of the top10 associations with a given drug name
    public Vector getTop10()
    {
        return this.top10;
    }
}