package woods.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import woods.FilesListReader;
import woods.OneFileRead;
import woods.TwoFilesRead;
import woods.datamodel.FilesDirectory;
import woods.datamodel.WorkerStatistics;

import java.util.Iterator;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/filepath")
    public String filepathForm(Model model) {
        model.addAttribute("directory", new FilesDirectory());
        return "filepath";
    }

    @PostMapping("/filepath")
    public String filepathSubmit(FilesDirectory directory, Model model) {
        directory.setFiles(new FilesListReader().filesListFormer(directory.getFilePath()));
        List<String> files = directory.getFiles();
        OneFileRead one;
        TwoFilesRead two;
        //model.addAttribute("files", files);
        //return "filesListForm";
        for(int i = 0; i < files.size(); i++){
            boolean statement1 = (files.get(i).endsWith("заготовка.xls") && (files.get(i + 1).endsWith("трельовка.xls")));
            boolean statement2 = (files.get(i).endsWith("заготовка(12-51).xls") && (files.get(i + 1).endsWith("трельовка(12-51).xls")));
            if(statement1 || statement2){
                two = new TwoFilesRead(files.get(i), files.get(i + 1));
                two.thread.start();
                model.addAttribute("area", two.getArea());
            } else{
                one = new OneFileRead(files.get(i));
                one.thread.start();
                model.addAttribute("area", one.getArea());
            }
        }
        return "/data";
    }
}
