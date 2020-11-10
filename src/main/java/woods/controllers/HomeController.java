package woods.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import woods.FilesListReader;
import woods.OneFileRead;
import woods.TwoFilesRead;
import woods.data.AreaRepository;
import woods.data.WoodRepository;
import woods.data.WorkerRepository;
import woods.datamodel.Area;
import woods.datamodel.FilesDirectory;
import woods.datamodel.WorkerStatistics;
import woods.services.ExcelReadService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
public class HomeController {

    @Autowired
    private WoodRepository woodRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @GetMapping("/filepath")
    public String filepathForm(Model model) {
        model.addAttribute("directory", new FilesDirectory());
        return "filepath";
    }

    @PostMapping("/filepath")
    public String filepathSubmit(FilesDirectory directory, Model model) {
        model.addAttribute("areas", ExcelReadService.getAreasList(directory));
        return "data";
    }
}
