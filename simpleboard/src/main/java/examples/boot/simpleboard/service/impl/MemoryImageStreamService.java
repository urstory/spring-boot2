package examples.boot.simpleboard.service.impl;

import examples.boot.simpleboard.service.ImageStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Profile("heroku")
public class MemoryImageStreamService implements ImageStreamService {
    private static int writeIndex = 0;
    private static int readIndex = 0;
    public static final int MAX_SIZE = 5;
    private static List<byte[]> list = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(String saveFileName, InputStream in) {
        ByteArrayOutputStream baos = null;
        try{
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int readCount = 0;
            while((readCount = in.read(buffer)) != -1){
                baos.write(buffer, 0, readCount);
            }
        }catch(IOException ioe){
            throw new RuntimeException(ioe.getMessage());
        }finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        list.add(writeIndex, baos.toByteArray());
        readIndex = writeIndex;
        writeIndex++;
        if(writeIndex >= MAX_SIZE){
            writeIndex = 0;
        }
    }

    @Override
    public void readAndWrite(String saveFileName, OutputStream out) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream fis = null;
        int readCount = 0;
        byte[] buffer = new byte[1024];
        try{
            fis = new ByteArrayInputStream(list.get(readIndex));
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
