package dst.courseproject.cloud;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface DropboxService {
    void uploadFile(File localFile, String dropBoxPath) throws IOException, DbxException;
}
