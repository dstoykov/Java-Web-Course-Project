package dst.courseproject.cloud;

import com.dropbox.core.DbxException;

import java.io.File;
import java.io.IOException;

public interface DropboxService {
    void uploadVideo(File localFile, String dropBoxPath) throws IOException, DbxException;

    void uploadImage(File localFile, String fileName) throws IOException, DbxException;

    String getFileLink(String fileName) throws DbxException;
}
