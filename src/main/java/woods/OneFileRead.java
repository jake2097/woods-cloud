package woods;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import woods.datamodel.Area;
import woods.datamodel.Months;
import woods.datamodel.Worker;
import woods.datamodel.WorkerStatistics;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

public class OneFileRead extends TwoFilesRead implements Callable<Area> {

    public OneFileRead(){}

    public OneFileRead(String fileName){
        this.fileName = fileName;
    }

    public Area call(){
        try (FileInputStream inputStream = new FileInputStream(new File(fileName))) {

            workBook = new HSSFWorkbook(inputStream);

            if (this.fileName.equals("заготовка.xls") || this.fileName.equals("заготовка(12-51).xls")) {
                this.zagProcessor(fileName);
            } else if (this.fileName.equals("трельовка.xls") || this.fileName.equals("трельовка(12-51).xls")) {
                this.trelProcessor(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        area.setList(list);
        return area;
    }

    @Override
    public void zagProcessor(String fileName) {
        super.zagProcessor(fileName);
    }

    @Override
    public void trelProcessor(String fileName) {
        // reading the value of PMM2 from the Трельовка and assigning it to the the PMM2 field of area object
        area.setPMM2(workBook.getSheet("Сторона1").getRow(11).getCell(13).getNumericCellValue());

        //setting the necessary workerNames to read information about
        String[] workerNames = new String[2];
        workerNames[0] = "Ковальчук Олександр Олександрович";
        workerNames[1] = "Шпак Олександр Іванович";

        // declaring and reading the values of days, salaries and workerNames from the file
        String[] allWorkers = new String[3];
        double[] days = new double[3];
        double[] salaries = new double[3];

        for (int i = 0; i < allWorkers.length; i++) {
            allWorkers[i] = workBook.getSheet("Сторона2 (2)").getRow(4 + i).getCell(1).getStringCellValue();
            days[i] = workBook.getSheet("Сторона2 (2)").getRow(4 + i).getCell(18).getNumericCellValue();
            salaries[i] = workBook.getSheet("Сторона2 (2)").getRow(4 + i).getCell(20).getNumericCellValue();
        }

        WorkerStatistics worker5 = new WorkerStatistics();
        WorkerStatistics worker6 = new WorkerStatistics();

        list.add(worker5);
        list.add(worker6);

        String month;
        // reading the month value in Трельовка
        month = workBook.getSheet("Сторона1").getRow(5).getCell(4).getStringCellValue();

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

        area.setAreaName(workBook.getSheet("Сторона1").getRow(7).getCell(2).getStringCellValue());

        for (int i = 0; i < workerNames.length; i++) {
            for (int j = 0; j < allWorkers.length; j++) {
                if (allWorkers[j].equals(workerNames[i])) {
                    list.get(i).setWorker(new Worker());
                    list.get(i).getWorker().setSurname(workerNames[i]);
                    list.get(i).setRate(workBook.getSheet("Сторона1").getRow(11).getCell(6).getNumericCellValue());
                    list.get(i).setVolume(salaries[j] / list.get(i).getRate());
                    list.get(i).setSalary(salaries[j]);
                }
            }
        }
    }
}
