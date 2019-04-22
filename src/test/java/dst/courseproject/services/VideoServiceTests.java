package dst.courseproject.services;

import dst.courseproject.cloud.DropboxService;
import dst.courseproject.entities.Category;
import dst.courseproject.entities.User;
import dst.courseproject.entities.Video;
import dst.courseproject.exceptions.VideoAlreadyLiked;
import dst.courseproject.exceptions.VideoNotLiked;
import dst.courseproject.models.binding.VideoEditBindingModel;
import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.service.VideoServiceModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.repositories.VideoRepository;
import dst.courseproject.services.impl.VideoServiceImpl;
import dst.courseproject.thumbnail.ThumbnailExtractor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VideoServiceTests {
    private VideoRepository videoRepository;
    private CategoryService categoryService;
    private UserService userService;
    private DropboxService dropboxService;
    private ModelMapper modelMapper;
    private VideoService service;

    @Before
    public void init() {
        this.videoRepository = mock(VideoRepository.class);
        this.categoryService = mock(CategoryService.class);
        this.userService = mock(UserService.class);
        this.dropboxService = mock(DropboxService.class);
        this.modelMapper = new ModelMapper();
        this.service = new VideoServiceImpl(this.videoRepository, this.categoryService, this.userService, this.modelMapper, this.dropboxService, new ThumbnailExtractor());
        when(this.userService.getUserServiceModelByEmail("gosho@gosho.com")).thenReturn(new UserServiceModel(){{
            setId("1");
            setEmail("gosho@gosho.com");
            setFirstName("Gosho");
            setLastName("Goshev");
        }});
        when(this.categoryService.getCategoryServiceModelByName("Music")).thenReturn(new CategoryServiceModel(){{
            setName("Music");
        }});
        when(this.videoRepository.getByVideoIdentifierEqualsAndDeletedOnNull("aBcDe")).thenReturn(new Video(){{
            setId("1");
            setTitle("Video");
            setVideoIdentifier("aBcDe");
            setCategory(new Category(){{setName("Sport");}});
            setViews(1000000000L);
        }});
        when(this.videoRepository.getAllByDeletedOnNullOrderByViewsDesc()).thenReturn(new LinkedHashSet<>(){{
            add(new Video(){{
                setTitle("Video1");
            }});
            add(new Video(){{
                setTitle("Video2");
            }});
            add(new Video(){{
                setTitle("Video3");
            }});
            add(new Video(){{
                setTitle("Video4");
            }});
            add(new Video(){{
                setTitle("Video5");
            }});
            add(new Video(){{
                setTitle("Video6");
            }});
            add(new Video(){{
                setTitle("Video7");
            }});
            add(new Video(){{
                setTitle("Video8");
            }});
            add(new Video(){{
                setTitle("Video9");
            }});
            add(new Video(){{
                setTitle("Video10");
            }});
            add(new Video(){{
                setTitle("Video11");
            }});
            add(new Video(){{
                setTitle("Video12");
            }});
            add(new Video(){{
                setTitle("Video13");
            }});
            add(new Video(){{
                setTitle("Video14");
            }});
            add(new Video(){{
                setTitle("Video15");
            }});
            add(new Video(){{
                setTitle("Video16");
            }});
            add(new Video(){{
                setTitle("Video17");
            }});
            add(new Video(){{
                setTitle("Video18");
            }});
            add(new Video(){{
                setTitle("Video19");
            }});
            add(new Video(){{
                setTitle("Video20");
            }});
            add(new Video(){{
                setTitle("Video21");
            }});
        }});
    }

    @Test
    public void videoService_getTotalActiveVideosCount_returnCorrect() {
        when(this.videoRepository.countAllByIdIsNotNullAndDeletedOnNull()).thenReturn(20L);
        Long expected = 20L;
        Long actual = this.service.getTotalActiveVideosCount();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoService_get20LatestVideos_returnCollectionOfViewModels() {
        when(this.videoRepository.getAllByDeletedOnNullOrderByUploadedOnDesc()).thenReturn(new LinkedHashSet<>(){{
            add(new Video(){{
                setTitle("Video1");
            }});
            add(new Video(){{
                setTitle("Video2");
            }});
            add(new Video(){{
                setTitle("Video3");
            }});
            add(new Video(){{
                setTitle("Video4");
            }});
            add(new Video(){{
                setTitle("Video5");
            }});
            add(new Video(){{
                setTitle("Video6");
            }});
            add(new Video(){{
                setTitle("Video7");
            }});
            add(new Video(){{
                setTitle("Video8");
            }});
            add(new Video(){{
                setTitle("Video9");
            }});
            add(new Video(){{
                setTitle("Video10");
            }});
            add(new Video(){{
                setTitle("Video11");
            }});
            add(new Video(){{
                setTitle("Video12");
            }});
            add(new Video(){{
                setTitle("Video13");
            }});
            add(new Video(){{
                setTitle("Video14");
            }});
            add(new Video(){{
                setTitle("Video15");
            }});
            add(new Video(){{
                setTitle("Video16");
            }});
            add(new Video(){{
                setTitle("Video17");
            }});
            add(new Video(){{
                setTitle("Video18");
            }});
            add(new Video(){{
                setTitle("Video19");
            }});
            add(new Video(){{
                setTitle("Video20");
            }});
            add(new Video(){{
                setTitle("Video21");
            }});
        }});
        List<VideoViewModel> expected = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setTitle("Video1");
            }});
            add(new VideoViewModel(){{
                setTitle("Video2");
            }});
            add(new VideoViewModel(){{
                setTitle("Video3");
            }});
            add(new VideoViewModel(){{
                setTitle("Video4");
            }});
            add(new VideoViewModel(){{
                setTitle("Video5");
            }});
            add(new VideoViewModel(){{
                setTitle("Video6");
            }});
            add(new VideoViewModel(){{
                setTitle("Video7");
            }});
            add(new VideoViewModel(){{
                setTitle("Video8");
            }});
            add(new VideoViewModel(){{
                setTitle("Video9");
            }});
            add(new VideoViewModel(){{
                setTitle("Video10");
            }});
            add(new VideoViewModel(){{
                setTitle("Video11");
            }});
            add(new VideoViewModel(){{
                setTitle("Video12");
            }});
            add(new VideoViewModel(){{
                setTitle("Video13");
            }});
            add(new VideoViewModel(){{
                setTitle("Video14");
            }});
            add(new VideoViewModel(){{
                setTitle("Video15");
            }});
            add(new VideoViewModel(){{
                setTitle("Video16");
            }});
            add(new VideoViewModel(){{
                setTitle("Video17");
            }});
            add(new VideoViewModel(){{
                setTitle("Video18");
            }});
            add(new VideoViewModel(){{
                setTitle("Video19");
            }});
            add(new VideoViewModel(){{
                setTitle("Video20");
            }});
            add(new VideoViewModel(){{
                setTitle("Video21");
            }});
        }};
        Set<VideoViewModel> actual = this.service.get20LatestVideos();
        int i = 0;

        Assert.assertEquals(20, actual.size());
        for (VideoViewModel act : actual) {
            Assert.assertEquals(expected.get(i++).getTitle(), act.getTitle());
        }
    }

    @Test
    public void videoService_get20MostPopularVideos_returnCollectionOfViewModels() {
        List<VideoViewModel> expected = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setTitle("Video1");
            }});
            add(new VideoViewModel(){{
                setTitle("Video2");
            }});
            add(new VideoViewModel(){{
                setTitle("Video3");
            }});
            add(new VideoViewModel(){{
                setTitle("Video4");
            }});
            add(new VideoViewModel(){{
                setTitle("Video5");
            }});
            add(new VideoViewModel(){{
                setTitle("Video6");
            }});
            add(new VideoViewModel(){{
                setTitle("Video7");
            }});
            add(new VideoViewModel(){{
                setTitle("Video8");
            }});
            add(new VideoViewModel(){{
                setTitle("Video9");
            }});
            add(new VideoViewModel(){{
                setTitle("Video10");
            }});
            add(new VideoViewModel(){{
                setTitle("Video11");
            }});
            add(new VideoViewModel(){{
                setTitle("Video12");
            }});
            add(new VideoViewModel(){{
                setTitle("Video13");
            }});
            add(new VideoViewModel(){{
                setTitle("Video14");
            }});
            add(new VideoViewModel(){{
                setTitle("Video15");
            }});
            add(new VideoViewModel(){{
                setTitle("Video16");
            }});
            add(new VideoViewModel(){{
                setTitle("Video17");
            }});
            add(new VideoViewModel(){{
                setTitle("Video18");
            }});
            add(new VideoViewModel(){{
                setTitle("Video19");
            }});
            add(new VideoViewModel(){{
                setTitle("Video20");
            }});
            add(new VideoViewModel(){{
                setTitle("Video21");
            }});
        }};
        Set<VideoViewModel> actual = this.service.get20MostPopularVideos();
        int i = 0;

        Assert.assertEquals(20, actual.size());
        for (VideoViewModel act : actual) {
            Assert.assertEquals(expected.get(i++).getTitle(), act.getTitle());
        }
    }

    @Test
    public void videoService_getVideoViewModel_returnViewModel() {
        VideoViewModel actual = this.service.getVideoViewModel("aBcDe");
        Assert.assertEquals("1", actual.getId());
        Assert.assertEquals("Video", actual.getTitle());
        Assert.assertEquals("aBcDe", actual.getVideoIdentifier());
    }

    @Test(expected = VideoAlreadyLiked.class)
    public void videoService_likeVideoWithUserLiked_throwException() throws VideoAlreadyLiked {
        when(this.videoRepository.getByVideoIdentifierEqualsAndDeletedOnNull("aBcDe")).thenReturn(new Video(){{
            setId("1");
            setTitle("Video");
            setVideoIdentifier("aBcDe");
            setUsersLiked(new HashMap<>(){{
                put("1", new User(){{
                    setEmail("gosho@gosho.com");
                }});
            }});
        }});
        this.service.likeVideo("aBcDe", "gosho@gosho.com");
    }

    @Test
    public void videoService_likeVideoWithCorrectData_returnServiceModel() throws VideoAlreadyLiked {
        VideoServiceModel actual = this.service.likeVideo("aBcDe", "gosho@gosho.com");
        Assert.assertEquals("gosho@gosho.com", actual.getUsersLiked().get("1").getEmail());
    }

    @Test(expected = VideoNotLiked.class)
    public void videoService_unlikeVideoWithUserLiked_throwException() throws VideoNotLiked {
        this.service.unlikeVideo("aBcDe", "gosho@gosho.com");
    }

    @Test
    public void videoService_unlikeVideoWithCorrectData_returnServiceModel() throws VideoNotLiked {
        when(this.videoRepository.getByVideoIdentifierEqualsAndDeletedOnNull("aBcDe")).thenReturn(new Video(){{
            setId("1");
            setTitle("Video");
            setVideoIdentifier("aBcDe");
            setUsersLiked(new HashMap<>(){{
                put("1", new User(){{
                    setEmail("gosho@gosho.com");
                }});
            }});
        }});
        VideoServiceModel actual = this.service.unlikeVideo("aBcDe", "gosho@gosho.com");
        Assert.assertEquals(0, actual.getUsersLiked().size());
    }

    @Test
    public void videoService_getVideoLikes_returnLikes() {
        when(this.videoRepository.getByVideoIdentifierEqualsAndDeletedOnNull("aBcDe")).thenReturn(new Video(){{
            setId("1");
            setTitle("Video");
            setVideoIdentifier("aBcDe");
            setUsersLiked(new HashMap<>(){{
                put("1", new User(){{
                    setEmail("gosho@gosho.com");
                }});
            }});
        }});
        long expected = 1;
        long actual = this.service.getVideoLikes("aBcDe");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoService_getVideosByUserAsViewModels_returnSetOfViewModels() {
        when(this.videoRepository.getAllByAuthorAndDeletedOnNullOrderByUploadedOnDesc(new User(){{
            setId("1");
            setEmail("gosho@gosho.com");
        }})).thenReturn(new ArrayList<>(){{
            add(new Video(){{
                setId("1");
                setTitle("Video1");
            }});
            add(new Video(){{
                setId("2");
                setTitle("Video2");
            }});
        }});
        UserServiceModel userServiceModel = new UserServiceModel(){{
            setId("1");
            setEmail("gosho@gosho.com");
        }};
        List<VideoViewModel> expected = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setId("1");
                setTitle("Video1");
            }});
            add(new VideoViewModel(){{
                setId("2");
                setTitle("Video2");
            }});
        }};
        Set<VideoViewModel> actual = this.service.getVideosByUserAsViewModels(userServiceModel);
        int i = 0;

        for (VideoViewModel act : actual) {
            Assert.assertEquals(expected.get(i++).getTitle(), act.getTitle());
        }
    }

    @Test
    public void videoService_getVideosByCategoryAsViewModels_returnSetOfViewModels() {
        when(this.videoRepository.getAllByCategoryAndDeletedOnNullOrderByUploadedOnDesc(new Category(){{
            setName("Music");
        }})).thenReturn(new ArrayList<>(){{
            add(new Video(){{
                setId("1");
                setTitle("Video1");
            }});
            add(new Video(){{
                setId("2");
                setTitle("Video2");
            }});
        }});
        CategoryServiceModel categoryServiceModel = new CategoryServiceModel(){{
            setName("Music");
        }};
        List<VideoViewModel> expected = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setId("1");
                setTitle("Video1");
            }});
            add(new VideoViewModel(){{
                setId("2");
                setTitle("Video2");
            }});
        }};
        Set<VideoViewModel> actual = this.service.getVideosByCategoryAsViewModel(categoryServiceModel);
        int i = 0;

        for (VideoViewModel act : actual) {
            Assert.assertEquals(expected.get(i++).getTitle(), act.getTitle());
        }
    }

    @Test
    public void videoService_getVideoServiceModelByIdentifier_returnServiceModel() {
        VideoServiceModel actual = this.service.getVideoServiceModelByIdentifier("aBcDe");

        Assert.assertEquals("1", actual.getId());
        Assert.assertEquals("Video", actual.getTitle());
        Assert.assertEquals("aBcDe", actual.getVideoIdentifier());
    }

    @Test
    public void videoService_increaseVideoByOne_returnServiceModel() {
        Long expected = 1000000001L;
        VideoServiceModel actual = this.service.increaseVideoViewsByOne("aBcDe");
        Assert.assertEquals(expected, actual.getViews());
    }

    @Test
    public void videoService_editVideoData_returnServiceModel() {
        VideoEditBindingModel editBindingModel = new VideoEditBindingModel(){{
            setTitle("Edited Video");
            setCategory("Music");
        }};
        VideoServiceModel actual = this.service.editVideoData(editBindingModel, "aBcDe");

        Assert.assertEquals("1", actual.getId());
        Assert.assertEquals("Edited Video", actual.getTitle());
        Assert.assertEquals("aBcDe", actual.getVideoIdentifier());
        Assert.assertEquals("Music", actual.getCategory().getName());
        Assert.assertEquals(Long.valueOf(1000000000L), actual.getViews());
    }

    @Test
    public void videoService_deleteVideo_returnServiceModel() {
        VideoServiceModel actual = this.service.deleteVideo("aBcDe");
        Assert.assertNotNull(actual.getDeletedOn());
    }

    @Test
    public void videoService_getViewModelsForSearch_returnSetOfViewModels() {
        List<VideoViewModel> expected = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setTitle("Video1");
            }});
            add(new VideoViewModel(){{
                setTitle("Video10");
            }});
            add(new VideoViewModel(){{
                setTitle("Video11");
            }});
            add(new VideoViewModel(){{
                setTitle("Video12");
            }});
            add(new VideoViewModel(){{
                setTitle("Video13");
            }});
            add(new VideoViewModel(){{
                setTitle("Video14");
            }});
            add(new VideoViewModel(){{
                setTitle("Video15");
            }});
            add(new VideoViewModel(){{
                setTitle("Video16");
            }});
            add(new VideoViewModel(){{
                setTitle("Video17");
            }});
            add(new VideoViewModel(){{
                setTitle("Video18");
            }});
            add(new VideoViewModel(){{
                setTitle("Video19");
            }});
        }};
        Set<VideoViewModel> actual = this.service.getViewModelsForSearch("dEo1");
        int i = 0;

        Assert.assertEquals(expected.size(), actual.size());
        for (VideoViewModel act : actual) {
            Assert.assertEquals(expected.get(i++).getTitle(), act.getTitle());
        }
    }
}
