package com.example.FileUpload.Controller;

import com.example.FileUpload.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;

@Controller
public class FileController {
    @Autowired
    FileService fileService;
    @GetMapping("/")
    public String indexPage(){
        return  "index";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes)throws IOException{
        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("message","Please Upload file");
            return "redirect:/";
        }else {
            fileService.uploadFile(file);
            redirectAttributes.addFlashAttribute("imageName",  file.getOriginalFilename());
            String imagePath = fileService.uploadDir +file.getOriginalFilename();
            System.out.println("File Name "+ imagePath);
            return "redirect:/uploadImage";
        }
    }
    @GetMapping("/uploadImage")
    public String showResultPage() {
        // The flash attribute "message" is automatically made available in the model
        return "images";
    }
    @PostMapping("/uploadFiles")
    public String uploadFiles(@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes) {

        Arrays.asList(files)
                .stream()
                .forEach(file -> fileService.uploadFile(file));
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded all files!");
        return "redirect:/";
    }
}
