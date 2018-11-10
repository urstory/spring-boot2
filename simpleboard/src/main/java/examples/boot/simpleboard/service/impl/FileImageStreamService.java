package examples.boot.simpleboard.service.impl;

import examples.boot.simpleboard.service.ImageStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@Profile("local")
public class FileImageStreamService implements ImageStreamService {

    @Override
    public void save(String saveFileName, InputStream in) {
        try(
                OutputStream out = new FileOutputStream(saveFileName)
        ){
            byte[] buffer = new byte[1024];
            int readCount = 0;
            while((readCount = in.read(buffer)) != -1){
                out.write(buffer, 0, readCount);
            }
        }catch(IOException ioe){
            throw new RuntimeException(ioe.getMessage());
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void readAndWrite(String saveFileName, OutputStream out) {
        FileInputStream fis = null;
        int readCount = 0;
        byte[] buffer = new byte[1024];
        try{
            fis = new FileInputStream(saveFileName);
            while((readCount = fis.read(buffer)) != -1){
                out.write(buffer, 0, readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } // finally
    }
}
