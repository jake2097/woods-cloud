package woods.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import woods.FilesListReader;
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
        model.addAttribute("files", files);
        return "filesListForm";
    }
}
