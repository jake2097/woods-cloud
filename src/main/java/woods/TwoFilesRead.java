package woods;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import woods.datamodel.*;

@Data
public class TwoFilesRead implements Callable<Area> {

    public String fileName;
    public String fileName2;

    public TwoFilesRead() {
    }

    public TwoFilesRead(String fileName, String fileName2) {
        this.fileName = fileName;
        this.fileName2 = fileName2;
    }

    HSSFWorkbook workBook;
    HSSFWorkbook workBook2;
    Area area = new Area();
    List<Wood> woods = new ArrayList<>();
    List<WorkerStatistics> list = new ArrayList<WorkerStatistics>();

    public Area call(){
        try (FileInputStream inputStream = new FileInputStream(new File(fileName))) {

            workBook = new HSSFWorkbook(inputStream);
            this.zagProcessor(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileInputStream inputStream2 = new FileInputStream(new File(fileName2))) {

            workBook2 = new HSSFWorkbook(inputStream2);
            this.trelProcessor(fileName2);

        } catch (IOException e) {
            e.printStackTrace();
        }
        area.setList(list);
        return area;
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

        double woodsSum = 0;

        woods.add(new Wood());
        woods.get(0).setType(WoodTypes.ДІЛОВА);
        woods.get(0).setVolume(woodsArray[0]);

        woods.add(new Wood());
        woods.get(1).setType(WoodTypes.ДЕРЕВИНА_ДРОВ_ХВ_П);
        woods.get(1).setVolume(woodsArray[1]);

        woods.add(new Wood());
        woods.get(2).setType(WoodTypes.ДРОВА_ХВ_П);
        woods.get(2).setVolume(woodsArray[2]);

        woods.add(new Wood());
        woods.get(3).setType(WoodTypes.ДРОВА_ТВ_П);
        woods.get(3).setVolume(woodsArray[3]);

        woods.add(new Wood());
        woods.get(4).setType(WoodTypes.СУЧКИ);
        woods.get(4).setVolume(woodsArray[4]);

        woods.add(new Wood());
        woods.get(5).setType(WoodTypes.ХВОРОСТ);
        woods.get(5).setVolume(woodsArray[5]);

        woods.add(new Wood());
        woods.get(6).setType(WoodTypes.НЕЛІКВІД);
        woods.get(6).setVolume(woodsArray[6]);

        for (int i = 0; i < woods.size(); i++) {
            // calculating the value of woodsSum field
            woodsSum += woods.get(i).getVolume();
        }

        area.setWoods(woods);
        //assigning the value of woodsSum to respective field of area object
        area.setWoodsSum(woodsSum);

        //assigning the value of PMM1 to respective field of area object
        area.setPMM1(workBook.getSheet("Сторона1").getRow(31).getCell(9).getNumericCellValue());

        //setting the necessary workerNames to read information about
        String[] workerNames = new String[4];

        if(fileName.endsWith("заготовка(12-51).xls")) {
            workerNames[0] = "Візнович Василь Степанович";
        }else if(fileName.endsWith("заготовка.xls")){
            workerNames[0] = "Візнович Василь Степанович ";
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
                    list.get(i).setWorker(new Worker());
                    list.get(i).getWorker().setSurname(workerNames[i]);
                    list.get(i).setVolume(sumVolume * days[j] / sumDays);
                    list.get(i).setSalary(salaries[j]);
                }
            }
        }
    }


    public void trelProcessor(String fileName) {

        // reading the value of PMM2 from the Трельовка and assigning it to the the PMM2 field of area object
        area.setPMM2(workBook2.getSheet("Сторона1").getRow(11).getCell(13).getNumericCellValue());

        //setting the necessary workerNames to read information about
        String[] workerNames = new String[2];
        workerNames[0] = "Ковальчук Олександр Олександрович";
        workerNames[1] = "Шпак Олександр Іванович";

        // declaring and reading the values of days, salaries and workerNames from the file
        String[] allWorkers = new String[3];
        double[] days = new double[3];
        double[] salaries = new double[3];

        for (int i = 0; i < allWorkers.length; i++) {
            allWorkers[i] = workBook2.getSheet("Сторона2 (2)").getRow(4 + i).getCell(1).getStringCellValue();
            days[i] = workBook2.getSheet("Сторона2 (2)").getRow(4 + i).getCell(18).getNumericCellValue();
            salaries[i] = workBook2.getSheet("Сторона2 (2)").getRow(4 + i).getCell(20).getNumericCellValue();
        }

        WorkerStatistics worker5 = new WorkerStatistics();
        WorkerStatistics worker6 = new WorkerStatistics();

        list.add(worker5);
        list.add(worker6);

        int k = 4;
        for (int i = 0; i < workerNames.length; i++) {
            for (int j = 0; j < allWorkers.length; j++) {
                if (allWorkers[j].equals(workerNames[i])) {
                    list.get(i + k).setWorker(new Worker());
                    list.get(i + k).getWorker().setSurname(workerNames[i]);
                    list.get(i + k).setRate(workBook2.getSheet("Сторона1").getRow(11).getCell(6).getNumericCellValue());
                    list.get(i + k).setVolume(salaries[j] / list.get(i + k).getRate());
                    list.get(i + k).setSalary(salaries[j]);
                }
            }
        }
    }
}
