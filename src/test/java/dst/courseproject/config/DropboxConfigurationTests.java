package dst.courseproject.config;

import com.dropbox.core.v2.DbxClientV2;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DropboxConfigurationTests {
    private DropboxConfiguration dropboxConfiguration = new DropboxConfiguration();

    @Test
    public void dropboxConfiguration_dropBoxClient_returnClient() {
        DbxClientV2 dbxClientV2 = this.dropboxConfiguration.dropBoxClient();
        Assert.assertEquals(DbxClientV2.class, dbxClientV2.getClass());
    }
}
