package XLUtility;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.*;
import java.util.Collection;

@RunWith(Parameterized.class)
public class DataDriven {
 private final double a;
 private final double b;
 private final double aTimesB;

@Parameterized.Parameters
 public static Double[] getData() throws IOException{
       ExcelConfig config=new ExcelConfig();
       config.setExcelFile("src/test/java/testdata/testdata.xls","Sheet1");
       Double[] args=new Double[3];
      for(int i=0;i<= config.getRowCountInSheet();i++)
       {
           args= new Double[]{config.getCellData(i, 0), config.getCellData(i, 1), config.getCellData(i, 2)};
       }

    return args;
}
    public DataDriven(double a, double b,double aTimesB)
    {
        super();
        this.a=a;
        this.b=b;
        this.aTimesB=aTimesB;
    }
    @Test
    public void shouldCalculateATimesB() {
    System.out.println(a+b+aTimesB);
        double calculatedValue = a * b;
        Assert.assertTrue(aTimesB==calculatedValue);
    }
}
