package dst.courseproject.cloud;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.ListSharedLinksResult;
import com.dropbox.core.v2.sharing.SharedFileMetadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Date;

@Component
public class DropboxServiceImpl implements DropboxService {
    private static final String SLASH = "/";
    private static final String MP4 = ".mp4";

    private final DbxClientV2 dbxClientV2;

    @Autowired
    public DropboxServiceImpl(DbxClientV2 dbxClientV2) {
        this.dbxClientV2 = dbxClientV2;
    }

    @Override
    public void uploadFile(File localFile, String fileName) throws IOException, DbxException {
        String dropboxPath = SLASH + fileName + MP4;
        InputStream inputStream = new FileInputStream(localFile);
        FileMetadata metadata = this.dbxClientV2.files().uploadBuilder(dropboxPath).withMode(WriteMode.ADD).withClientModified(new Date(localFile.lastModified())).uploadAndFinish(inputStream);

        inputStream.close();
    }

    @Override
    public String getFileLink(String fileName) throws DbxException {
//        String dropboxPath = SLASH + fileName + MP4;
//        ListSharedLinksResult listSharedLinksResult = dbxClientV2.sharing()
//                .listSharedLinksBuilder()
//                .withPath(dropboxPath).withDirectOnly(true)
//                .start();
//        SharedLinkMetadata result = listSharedLinksResult.getLinks().get(0);
//
//        return result.getUrl().replace("di=0", "raw=1");
        return null;
    }

    private static void printProgress(long uploaded, long size) {
        System.out.printf("Uploaded %12d / %12d bytes (%5.2f%%)\n", uploaded, size, 100 * (uploaded / (double) size));
    }
}
