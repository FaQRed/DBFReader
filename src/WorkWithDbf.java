import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WorkWithDbf {
    private int num = 1;
    private int numHeader = 1;
    private boolean isFields = true;
    private final StringBuilder notWrittenFields = new StringBuilder();
    private final StringBuilder notWrittenFieldsInIncome = new StringBuilder();

    private DBFReader add(String path) throws FileNotFoundException, DBFException {
        DBFReader reader = new DBFReader(new FileInputStream(path));
        reader.setCharactersetName("windows-1251");
        return reader;
    }

    public boolean isFields() {
        return isFields;
    }

    private ArrayList<Map<String, Object>> createReaderMapList(DBFReader reader) throws DBFException {
        ArrayList<Map<String, Object>> readerMapList = new ArrayList<>();
        Object[] rowObjects;
        while ((rowObjects = reader.nextRecord()) != null) {
            HashMap<String, Object> map = new HashMap<>();
            for (int i = 0; i < reader.getFieldCount(); i++) {
                map.put(reader.getField(i).getName(), rowObjects[i]);
            }
            readerMapList.add(map);
        }
        return readerMapList;
    }


    private Income createIncomeFromMap(Map<String, Object> map) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMyy");

        checkNotWrittenFields(map);
        Income income = new Income();
        if (isFields) {

            try {

                income.setCount((Double) map.get("KOL"));
            } catch (ClassCastException e) {
                notWrittenFieldsInIncome.append(" колличество по накладной, ");
            }
            try {
                Date date = simpleDateFormat.parse(((String) map.get("DI")).trim());
                DateFormat dateFormat = DateFormat.getDateInstance();
                dateFormat.format(date);
                income.setExpirationDate(dateFormat.getCalendar());
            } catch (ClassCastException | ParseException e) {
                notWrittenFieldsInIncome.append(" срок годности, ");
            }
            try {
                income.setPrice((Double) map.get("CTRO"));
            } catch (ClassCastException e) {
                notWrittenFieldsInIncome.append(" цена отпускная, ");
            }
            try {
                income.setProducerPrice((Double) map.get("CTP"));
            } catch (ClassCastException e) {
                notWrittenFieldsInIncome.append(" цена производителя, ");
            }
            try {
                income.setSeries(((String) map.get("SPAR")).trim());
            } catch (ClassCastException e) {
                notWrittenFieldsInIncome.append(" серия, ");
            }
            try {
                income.setTotal((Double) map.get("CTRO") * (Double) map.get("KOL"));
            } catch (ClassCastException e) {
                notWrittenFieldsInIncome.append(" общая сумма, ");
            } catch (NullPointerException ignore) {
            }
            try {
                income.setTradeName(((String) map.get("IMN")).trim());
            } catch (ClassCastException e) {
                notWrittenFieldsInIncome.append(" торговое наименование, ");
            }
            try {
                income.setMedicament((Double) map.get("KODN"));
            } catch (ClassCastException e) {
                notWrittenFieldsInIncome.append(" сылка,");
            }
            try {
                income.setManufacturer(((String) map.get("IMF")).trim());
            } catch (ClassCastException e) {
                notWrittenFieldsInIncome.append(" производитель, ");
            }
            try {
                income.setInformation(((String) map.get("FGRLEVEL4")).trim());
            } catch (ClassCastException e) {
                notWrittenFieldsInIncome.append(" доп. информация, ");
            }
            try {
                income.setBarCode(((String) map.get("BARCODE2")).trim());
            } catch (ClassCastException e) {
                notWrittenFieldsInIncome.append(" баркод, ");
            }
            try {
                income.setUnit(((String) map.get("IMEU")).trim());
            } catch (ClassCastException e) {
                notWrittenFieldsInIncome.append(" единица измерения, ");
            }

            if (notWrittenFieldsInIncome.isEmpty()) {
                System.out.println("Все значения записались успешно!");
                System.out.println("----------------------------------");
            } else {
                notWrittenFieldsInIncome.deleteCharAt(notWrittenFieldsInIncome.lastIndexOf(", "));
                System.out.println(num + ")" + " Не записались значения:" + notWrittenFieldsInIncome.toString());
                notWrittenFieldsInIncome.delete(0, notWrittenFieldsInIncome.length());
                System.out.println("----------------------------------");
                num++;
            }

        } else {
            notWrittenFields.deleteCharAt(notWrittenFields.lastIndexOf(", "));
            System.out.println(numHeader + ")" + " Отсутствуют поля (header):" + notWrittenFields.toString());
            numHeader++;
            notWrittenFields.delete(0, notWrittenFields.length());
        }

        return income;
    }


    private void checkNotWrittenFields(Map<String, Object> map) {
        if (!map.containsKey("KOL")) {
            notWrittenFields.append(" колличество по накладной, ");
        } else if (!map.containsKey("DI")) {
            notWrittenFields.append(" срок годности, ");
        } else if (!map.containsKey("CTRO")) {
            notWrittenFields.append(" цена отпускная, ");
        } else if (!map.containsKey("CTP")) {
            notWrittenFields.append(" цена производителя, ");
        } else if (!map.containsKey("SPAR")) {
            notWrittenFields.append(" серия, ");
        } else if (!map.containsKey("CTRO") || !map.containsKey("KOL")) {
            notWrittenFields.append(" общая сумма, ");
        } else if (!map.containsKey("IMN")) {
            notWrittenFields.append(" торговое наименование, ");
        } else if (!map.containsKey("KODN")) {
            notWrittenFields.append(" сылка,");
        } else if (!map.containsKey("IMF")) {
            notWrittenFields.append(" производитель, ");
        } else if (!map.containsKey("FGRLEVEL4")) {
            notWrittenFields.append(" доп. информация, ");
        } else if (!map.containsKey("BARCODE2")) {
            notWrittenFields.append(" баркод, ");
        } else if (!map.containsKey("IMEU")) {
            notWrittenFields.append(" единица измерения, ");
        }
        if (notWrittenFields.isEmpty()) {
            System.out.println("Все поля присутствуют! (header)");
        } else {
            isFields = false;
        }
    }


    public ArrayList<Income> createIncomeArrayList(String path) {
        DBFReader reader;
        ArrayList<Map<String, Object>> mapList = new ArrayList<>();
        try {
            reader = add(path);
            mapList = createReaderMapList(reader);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (DBFException e) {
            System.out.println("Файл не является DBF");
        }


        ArrayList<Income> incomeArrayList = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : mapList) {
            incomeArrayList.add(createIncomeFromMap(stringObjectMap));
        }
        return incomeArrayList;
    }
}