package dst.courseproject.cloud;

import com.google.gson.Gson;
import dst.courseproject.models.service.VideoServiceModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CloudVideoExtractor {
    private static final String VIDEO_FOLDER_ID = "1643892282";
    private static final String QUERY_PATH_SEPARATOR = "?";
    private static final String QUERY_PARAMETER_SEPARATOR = "&";
    private static final String FOLDER_ID_PARAMETER = "folderid=";
    private static final String FILE_ID_PARAMETER = "fileid=";
    private static final String AUTH_PARAMETER = "auth=";
    private static final String CODE_PARAMETER = "code=";
    private static final String LIST_FOLDER_URL = "https://api.pcloud.com/listfolder";
    private static final String LIST_FILE_URL = "https://api.pcloud.com/getfilepublink";
    private static final String DOWNLOAD_FILE_URL = "https://api.pcloud.com/getpublinkdownload";

    private final HttpRequestExecutor httpRequestExecutor;
    private final CloudAuthorizationService cloudAuthorizationService;

    public CloudVideoExtractor(HttpRequestExecutor httpRequestExecutor, CloudAuthorizationService cloudAuthorizationService) {
        this.httpRequestExecutor = httpRequestExecutor;
        this.cloudAuthorizationService = cloudAuthorizationService;
    }

    public List<VideoServiceModel> getAllVideos() throws IOException {
        Gson gson = new Gson();

        String accessToken = this.cloudAuthorizationService.getAccessToken();
        String folderJsonResult = this.httpRequestExecutor.executeGetRequest(
                LIST_FOLDER_URL
                  + QUERY_PATH_SEPARATOR
                  + FOLDER_ID_PARAMETER
                  + VIDEO_FOLDER_ID
                  + QUERY_PARAMETER_SEPARATOR
                  + AUTH_PARAMETER
                  + accessToken
        ).body()
        .string();

        Map<String, Object> folderData = gson.fromJson(folderJsonResult, Map.class);
        List<Map<String, Object>> fileData = (List<Map<String, Object>>) ((Map<String, Object>) folderData.get("metadata")).get("contents");

        List<VideoServiceModel> resultVideos = new ArrayList<>();

        for (Map<String, Object> fileDatum : fileData) {
            String fileId = fileDatum.get("id").toString().substring(1);
            String fileListJsonResult = this.httpRequestExecutor.executeGetRequest(
                    LIST_FILE_URL
                            + QUERY_PATH_SEPARATOR
                            + FILE_ID_PARAMETER
                            + fileId
                            + QUERY_PARAMETER_SEPARATOR
                            + AUTH_PARAMETER
                            + accessToken
            ).body()
            .string();

            String fileCode = gson.fromJson(fileListJsonResult, Map.class).get("code").toString();
            String fileDownloadJsonResult = this.httpRequestExecutor.executeGetRequest(
                    DOWNLOAD_FILE_URL
                            + QUERY_PATH_SEPARATOR
                            + CODE_PARAMETER
                            + fileCode
            ).body()
            .string();

            Map<String, Object> fileDownloadData = gson.fromJson(fileDownloadJsonResult, Map.class);

            String filePath = fileDownloadData.get("path").toString();
            String host = ((List<String>) fileDownloadData.get("hosts")).get(0);
            String fileName = fileDatum.get("name").toString();
            String fileUrl = "https://" + host + filePath;

            VideoServiceModel videoServiceModel = new VideoServiceModel();
            videoServiceModel.setTitle(fileName);
            videoServiceModel.setUrl(fileUrl);

            resultVideos.add(videoServiceModel);
        }

        return resultVideos;
    }
}
