package XLUtility;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.*;

@RunWith(Parameterized.class)
public class DataDriven {
 private final double a;
 private final double b;
 private final double aTimesB;

@Parameterized.Parameters
 public static double[][] getData() throws IOException {
    ExcelConfig config = new ExcelConfig();
    config.setExcelFile("src/test/java/testdata/testdata1.xls", "Sheet1");
    int rowCount= config.getRowCountInSheet();
    int cellCount= config.getCellCountInSheet();
    System.out.println("row count "+rowCount);
    System.out.println("cell count "+cellCount);
    double[][] args = new double[rowCount][cellCount];
    System.out.println("cellcount"+config.getCellCountInSheet());
    for (int i = 0; i <=rowCount; i++) {
        for(int j=0;j<=cellCount;j++) {

            args[i][j] =config.getCellData(i,j);
            System.out.print(args[i][j]+",");
        }
    //System.out.print(args[i]+",");
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
