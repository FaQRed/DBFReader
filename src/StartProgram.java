import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;


public class StartProgram {

    public static void main(String[] args) {
        WorkWithDbf workWithDbf = new WorkWithDbf();
        ArrayList<Income> incomeArrayList;
        incomeArrayList = workWithDbf.createIncomeArrayList("C:\\Users\\sanko\\Desktop\\DBFReader\\src\\IncomeExample.dbf");

        int num = 1;


        if (workWithDbf.isFields()) {
            for (Income income : incomeArrayList) {
                System.out.println();
                System.out.println("------------------" + num + "------------------");
                System.out.println(income);
                num++;
            }
        }


    }
}
