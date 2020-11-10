package woods.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woods.FilesListReader;
import woods.OneFileRead;
import woods.TwoFilesRead;
import woods.data.AreaRepository;
import woods.datamodel.Area;
import woods.datamodel.FilesDirectory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ExcelReadService {

    @Autowired
    private AreaRepository areaRepository;

    public static List<Area> getAreasList(FilesDirectory directory){
        directory.setFiles(new FilesListReader().filesListFormer(directory.getFilePath()));
        List<String> files = directory.getFiles();

        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<Area> f;
        List<Area> areasList = new ArrayList<>();

        for(int i = 0; i < files.size() - 1; i++){
            boolean statement1 = (files.get(i).endsWith("заготовка.xls") && (files.get(i + 1).endsWith("трельовка.xls")));
            boolean statement2 = (files.get(i).endsWith("заготовка(12-51).xls") && (files.get(i + 1).endsWith("трельовка(12-51).xls")));
            if(statement1 || statement2){
                f = es.submit(new TwoFilesRead(files.get(i), files.get(i + 1)));
            } else{
                f = es.submit(new OneFileRead(files.get(i)));
            }
            try{
                areasList.add(f.get());
            }catch(InterruptedException e){
                e.printStackTrace();
            } catch(ExecutionException e){
                e.printStackTrace();
            }
        }
        ExcelReadService excel = new ExcelReadService();
        excel.areaRepository.saveAll(areasList);
        return areasList;
    }

}
