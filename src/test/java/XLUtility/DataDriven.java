package XLUtility;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;

@RunWith(Parameterized.class)
public class DataDriven {

   double a;
   double b;
   double aTimesB;

    @Parameterized.Parameters
    public static Collection spreadsheetData() throws Exception {
        InputStream spreadsheet = new FileInputStream("src/test/java/testdata/TestData.xls");
        return new ExcelConfig(spreadsheet).getData();
    }

    public DataDriven(double a,double b,double aTimesB) {
        super();
        this.a=a;
        this.b=b;
        this.aTimesB=aTimesB;

    }

    @Test
    public void Test() {
    double multiply=a*b;
    Assert.assertTrue(multiply==aTimesB);
    }
}