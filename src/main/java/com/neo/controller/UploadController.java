package com.neo.controller;

import com.neo.tools.Upload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class UploadController {
    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "D:\\test\\";

    @GetMapping("/")
    public String index() {
        return "Upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @ResponseBody
    @PostMapping("/ajax/upload") // //new annotation since 4.3
    public String uploadByAjax(@RequestParam("file") MultipartFile file) {
        return Upload.uploadFile(file);
    }

    @ResponseBody
    @PostMapping("/ajax/uploads") // //new annotation since 4.3
    public String uploadsByAjax(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
        System.out.println("uploads");

        return Upload.uploadFiles(files);
    }


    @ResponseBody
    @PostMapping("/ajax/uploadss") // //new annotation since 4.3
    public String uploadssByAjax(@RequestParam("file") MultipartFile []files) {
        System.out.println("uploads");

        return Upload.uploadFiless(files);
    }


    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }



}

