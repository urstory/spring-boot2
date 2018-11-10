package examples.boot.simpleboard.service;

import java.io.InputStream;
import java.io.OutputStream;

public interface ImageStreamService {
    public void save(String saveFileName, InputStream in);
    public void readAndWrite(String saveFileName, OutputStream out);
}
