package examples.boot.simpleboard.controller;

import examples.boot.simpleboard.domain.ImageFile;
import examples.boot.simpleboard.service.BoardService;
import examples.boot.simpleboard.service.ImageStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/image-files")
public class ImageFileController {
    @Autowired
    BoardService boardService;

    @Autowired
    ImageStreamService imageStreamService;

    @GetMapping("/{fileId}")
    @ResponseBody
    public void handleFileUpload(
            @PathVariable("fileId") Long fileId,
            HttpServletResponse response) {

        ImageFile imageFile = boardService.getImageFile(fileId);
        FileInputStream fis = null;
        byte[] buffer = new byte[1024];
        int readCount = 0;
        OutputStream out = null;

        response.setContentLengthLong(imageFile.getLength());
        response.setContentType(imageFile.getContentType());

        try {
            imageStreamService.readAndWrite(imageFile.getSaveFileName(), response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        try{
//            fis = new FileInputStream(imageFile.getSaveFileName());
//            out = response.getOutputStream();
//            while((readCount = fis.read(buffer)) != -1){
//                out.write(buffer, 0, readCount);
//            }
//        }catch(Exception ex){
//            throw new RuntimeException(ex.getMessage());
//        }finally {
//            if(fis != null){
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(out != null){
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        } // finally
    }
}
