package dst.courseproject.cloud;

import com.dropbox.core.DbxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface DropboxService {
    void uploadFile(File localFile, String dropBoxPath) throws IOException, DbxException;

    String getFileLink(String fileName) throws DbxException, FileNotFoundException;
}
