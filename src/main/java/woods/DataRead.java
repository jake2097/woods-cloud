package woods;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import woods.datamodel.Area;
import woods.datamodel.Months;
import woods.datamodel.WoodTypes;
import woods.datamodel.WorkerStatistics;

public class DataRead implements Runnable {

    public String fileName;

    public DataRead() {
    }

    public DataRead(String fileName) {
        this.fileName = fileName;
    }

    DataRead obj;
    HSSFWorkbook workBook;
    Area area = new Area();
    List<WorkerStatistics> list = new ArrayList<WorkerStatistics>();

    @Override
    public void run() {
        try (FileInputStream inputStream = new FileInputStream(new File(fileName))) {

            workBook = new HSSFWorkbook(inputStream);

            if (obj.fileName.equals("заготовка.xls") || obj.fileName.equals("заготовка(12-51).xls")) {
                obj.zagProcessor(fileName);
            } else if (obj.fileName.equals("трельовка.xls") || obj.fileName.equals("трельовка(12-51).xls")) {
                obj.trelProcessor(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void zagProcessor(String fileName) {

        String month;
        // reading the month value in Zagotovka
        month = workBook.getSheet("Сторона1").getRow(5).getCell(2).getStringCellValue();

        // setting the month value in the database
        switch (month) {
            case "січня":
                area.setMonth(Months.СІЧЕНЬ);
                break;
            case "лютого":
                area.setMonth(Months.ЛЮТИЙ);
                break;
            case "березня":
                area.setMonth(Months.БЕРЕЗЕНЬ);
                break;
            case "квітня":
                area.setMonth(Months.КВІТЕНЬ);
                break;
            case "травня":
                area.setMonth(Months.ТРАВЕНЬ);
                break;
            case "червня":
                area.setMonth(Months.ЧЕРВЕНЬ);
                break;
            case "липня":
                area.setMonth(Months.ЛИПЕНЬ);
                break;
            case "серпня":
                area.setMonth(Months.СЕРПЕНЬ);
                break;
            case "вересня":
                area.setMonth(Months.ВЕРЕСЕНЬ);
                break;
            case "жовтня":
                area.setMonth(Months.ЖОВТЕНЬ);
                break;
            case "листопада":
                area.setMonth(Months.ЛИСТОПАД);
                break;
            case "грудня":
                area.setMonth(Months.ГРУДЕНЬ);
                break;
        }
        // setting the value of areaName
        area.setAreaName(workBook.getSheet("Сторона1").getRow(7).getCell(2).getStringCellValue());

        // setting the value of area square
        area.setSquare(workBook.getSheet("Сторона1").getRow(7).getCell(8).getNumericCellValue());

        // reading the volumes of different woodTypes in Zagotovka
        double[] woodsArray = new double[7];
        woodsArray[0] = workBook.getSheet("Сторона1").getRow(14).getCell(2).getNumericCellValue();
        woodsArray[1] = workBook.getSheet("Сторона1").getRow(15).getCell(2).getNumericCellValue();
        woodsArray[2] = workBook.getSheet("Сторона1").getRow(18).getCell(2).getNumericCellValue();
        woodsArray[3] = workBook.getSheet("Сторона1").getRow(23).getCell(2).getNumericCellValue();
        woodsArray[4] = workBook.getSheet("Сторона1").getRow(24).getCell(2).getNumericCellValue();
        woodsArray[5] = workBook.getSheet("Сторона1").getRow(26).getCell(3).getNumericCellValue();
        woodsArray[6] = workBook.getSheet("Сторона1").getRow(27).getCell(2).getNumericCellValue();

        // assigning the respective read volume values to woodTypes in the database
        WoodTypes[] woodTypes = WoodTypes.values();
        double woodsSum = 0;
        for (WoodTypes woodType : woodTypes) {
            int i = 0;
            woodType.setVolume(woodsArray[i]);
            // calculating the value of woodsSum field
            woodsSum += woodType.getVolume();
            i++;
        }

        //assigning the value of woodsSum to respective field of area object
        area.setWoodsSum(woodsSum);

        //assigning the value of PMM1 to respective field of area object
        area.setPMM1(workBook.getSheet("Сторона1").getRow(31).getCell(9).getNumericCellValue());

        //setting the necessary workerNames to read information about
        String[] workerNames = new String[4];
        switch (fileName) {
            case "заготовка(12-51).xls":
                workerNames[0] = "Візнович Василь Степанович";
                break;
            case "заготовка.xls":
                workerNames[0] = "Візнович Василь Степанович ";
                break;
        }
        workerNames[1] = "Мединський Дмитро Володимирович";
        workerNames[2] = "Дорошенко Ігор Юрійович";
        workerNames[3] = "Капустяк Олександр Васильович";

        // declaring and reading the values of days, salaries and workerNames from the file
        String[] allWorkers = new String[15];
        double[] days = new double[15];
        double[] salaries = new double[15];

        for (int i = 0; i < allWorkers.length; i++) {
            allWorkers[i] = workBook.getSheet("Сторона2").getRow(20 + i).getCell(1).getStringCellValue();
            days[i] = workBook.getSheet("Сторона2").getRow(20 + i).getCell(18).getNumericCellValue();
            salaries[i] = workBook.getSheet("Сторона2").getRow(20 + i).getCell(20).getNumericCellValue();
        }

        WorkerStatistics worker1 = new WorkerStatistics();
        WorkerStatistics worker2 = new WorkerStatistics();
        WorkerStatistics worker3 = new WorkerStatistics();
        WorkerStatistics worker4 = new WorkerStatistics();

        list.add(worker1);
        list.add(worker2);
        list.add(worker3);
        list.add(worker4);

        // reading the sumValues of days and volume in this area
        Double sumVolume = workBook.getSheet("Сторона1").getRow(38).getCell(11).getNumericCellValue();
        Double sumDays = workBook.getSheet("Сторона2").getRow(35).getCell(18).getNumericCellValue();

        // defining the fields of WorkerStatistics List
        for (int i = 0; i < workerNames.length; i++) {
            for (int j = 0; j < allWorkers.length; j++) {
                if (allWorkers[j].equals(workerNames[i])) {
                    list.get(i).setSurname(workerNames[i]);
                    list.get(i).setVolume(sumVolume * days[j] / sumDays);
                    list.get(i).setSalary(salaries[j]);
                }
            }
        }
    }


    public void trelProcessor(String fileName) {

        // reading the value of PMM2 from the Трельовка and assigning it to the the PMM2 field of area object
        area.setPMM2(workBook.getSheet("Сторона1").getRow(11).getCell(12).getNumericCellValue());

        //setting the necessary workerNames to read information about
        String[] workerNames = new String[2];
        workerNames[0] = "Ковальчук Олександр Олександрович";
        workerNames[1] = "Шпак Олександр Іванович";

        // declaring and reading the values of days, salaries and workerNames from the file
        String[] allWorkers = new String[3];
        double[] days = new double[3];
        double[] salaries = new double[3];

        for (int i = 0; i < allWorkers.length; i++) {
            allWorkers[i] = workBook.getSheet("Сторона2 (2)").getRow(4 + i).getCell(2).getStringCellValue();
            days[i] = workBook.getSheet("Сторона2 (2)").getRow(4 + i).getCell(18).getNumericCellValue();
            salaries[i] = workBook.getSheet("Сторона2 (2)").getRow(4 + i).getCell(20).getNumericCellValue();
        }

        WorkerStatistics worker5 = new WorkerStatistics();
        WorkerStatistics worker6 = new WorkerStatistics();

        list.add(worker5);
        list.add(worker6);

        int k = 5;
        for (int i = 0; i < workerNames.length; i++) {
            for (int j = 0; j < allWorkers.length; j++) {
                if (allWorkers[j].equals(workerNames[i])) {
                    list.get(i + k).setSurname(workerNames[i]);
                    list.get(i + k).setRate(workBook.getSheet("Сторона1").getRow(11).getCell(6).getNumericCellValue());
                    list.get(i + k).setVolume(salaries[j] / list.get(i + k).getRate());
                    list.get(i + k).setSalary(salaries[j]);
                }
            }
        }
    }
}
