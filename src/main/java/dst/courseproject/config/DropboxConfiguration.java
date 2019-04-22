package dst.courseproject.config;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DropboxConfiguration {
    private static final String DROPBOX_ACCESS_TOKEN = "mWxi8DHSrNAAAAAAAAAADKHN7B6kW5eXPuYCrPx4KP2C117GcKCqhygbuxY8LhQa";
    private static final String CLIENT_IDENTIFIER = "In The Box Project";

    @Bean
    public DbxClientV2 dropBoxClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder(CLIENT_IDENTIFIER).build();
        return new DbxClientV2(config, DROPBOX_ACCESS_TOKEN);
    }
}
