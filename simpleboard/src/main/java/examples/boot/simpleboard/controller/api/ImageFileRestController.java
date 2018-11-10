package examples.boot.simpleboard.controller.api;

import examples.boot.simpleboard.domain.ImageFile;
import examples.boot.simpleboard.domain.UploadFile;
import examples.boot.simpleboard.domain.User;
import examples.boot.simpleboard.service.BoardService;
import examples.boot.simpleboard.service.ImageStreamService;
import examples.boot.simpleboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

@RestController
@RequestMapping("/api/image-files")
public class ImageFileRestController {
    @Value("${file.upload.dir}")
    private String fileUploadDir;

    @Autowired
    BoardService boardService;

    @Autowired
    UserService userService;

    @Autowired
    ImageStreamService imageStreamService;

    @PostMapping
    public String handleFileUpload(
            Principal principal,
            @RequestParam("fname") String fname,
            @RequestParam("data") MultipartFile multipartFile) {

        Calendar cal = Calendar.getInstance();
        String dir = fileUploadDir + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DAY_OF_MONTH);
        File uploadDir = new File(dir);
        uploadDir.mkdirs();
        String saveFileName = dir + "/" + UUID.randomUUID().toString();

//
//        try(
//                InputStream in = multipartFile.getInputStream();
//                OutputStream out = new FileOutputStream(saveFileName)
//        ){
//            byte[] buffer = new byte[1024];
//            int readCount = 0;
//            while((readCount = in.read(buffer)) != -1){
//                out.write(buffer, 0, readCount);
//            }
//        }catch(IOException ioe){
//            throw new RuntimeException(ioe.getMessage());
//        }

        try {
            imageStreamService.save(saveFileName, multipartFile.getInputStream());
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }

        User user = userService.getUserByEmail(principal.getName());
        ImageFile imageFile = new ImageFile();
        imageFile.setContentType(multipartFile.getContentType());
        imageFile.setFileName(multipartFile.getOriginalFilename());
        imageFile.setRegdate(LocalDateTime.now());
        imageFile.setSaveFileName(saveFileName);
        imageFile.setLength(multipartFile.getSize());
        imageFile.setUser(user);

        imageFile = boardService.addImageFile(imageFile);

        return "/image-files/" + imageFile.getId();
    }

}
