package dst.courseproject.controllers;

import com.sun.security.auth.UserPrincipal;
import dst.courseproject.controllers.CommentController;
import dst.courseproject.entities.Role;
import dst.courseproject.models.binding.CommentAddBindingModel;
import dst.courseproject.models.service.CommentServiceModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.CommentViewModel;
import dst.courseproject.services.CommentService;
import dst.courseproject.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommentControllerTests {
    private CommentController controller;
    private CommentService commentService;
    private UserService userService;

    @Before
    public void init() {
        this.commentService = mock(CommentService.class);
        this.userService = mock(UserService.class);
        this.controller = new CommentController(this.commentService, this.userService);
        when(this.commentService.getCommentServiceModelById("1")).thenReturn(new CommentServiceModel(){{
            setId("1");
            setContent("Long content");
            setAuthor(new UserServiceModel(){{
                setFirstName("Pesho");
                setLastName("Peshev");
                setAuthorities(new HashSet<>(){{
                    add(new Role(){{
                        setAuthority("MODERATOR");
                    }});
                    add(new Role(){{
                        setAuthority("USER");
                    }});
                }});
            }});
        }});
    }

    @Test
    public void commentController_getAllComments_returnCollectionOfViewModels() {
        when(this.commentService.getCommentViewModelsByVideo("aBcDe")).thenReturn(new LinkedHashSet<>(){{
            add(new CommentViewModel(){{
                setAuthor("Pesho");
                setContent("Long content1.");
            }});
            add(new CommentViewModel(){{
                setAuthor("Gosho");
                setContent("Long content2.");
            }});
            add(new CommentViewModel(){{
                setAuthor("Ivan");
                setContent("Long content3.");
            }});
        }});

        List<CommentViewModel> expected = new ArrayList<>(){{
            add(new CommentViewModel(){{
                setAuthor("Pesho");
                setContent("Long content1.");
            }});
            add(new CommentViewModel(){{
                setAuthor("Gosho");
                setContent("Long content2.");
            }});
            add(new CommentViewModel(){{
                setAuthor("Ivan");
                setContent("Long content3.");
            }});
        }};
        Set<CommentViewModel> actual = this.controller.getAllComments("aBcDe");
        int i = 0;

        for (CommentViewModel act : actual) {
            Assert.assertEquals(expected.get(i).getContent(), act.getContent());
            Assert.assertEquals(expected.get(i++).getAuthor(), act.getAuthor());
        }
    }

    @Test
    public void commentController_addComment_returnTrue() {
        boolean actual = this.controller.addComment(new CommentAddBindingModel(), "aBcDe", new UserPrincipal("pesho@mail.com"));
        Assert.assertTrue(actual);
    }

    @Test
    public void commentController_deleteCommentWithCorrectParameters_returnTrue() {
        when(this.userService.getUserServiceModelByEmail("pesho@mail.com")).thenReturn(new UserServiceModel(){{
            setEmail("pesho@mail.com");
            setFirstName("Pesho");
            setLastName("Peshev");
            setAuthorities(new HashSet<>(){{
                add(new Role(){{
                    setAuthority("MODERATOR");
                }});
                add(new Role(){{
                    setAuthority("USER");
                }});
            }});
        }});
        boolean actual = this.controller.removeComment("1", new UserPrincipal("pesho@mail.com"));
        Assert.assertTrue(actual);
    }

    @Test
    public void commentController_deleteCommentWithIncorrectParameters_returnFalse() {
        when(this.userService.getUserServiceModelByEmail("pesho@mail.com")).thenReturn(new UserServiceModel(){{
            setEmail("stafan@mail.com");
            setFirstName("Stefan");
            setLastName("Stefanov");
            setAuthorities(new HashSet<>(){{
                add(new Role(){{
                    setAuthority("USER");
                }});
            }});
        }});
        boolean actual = this.controller.removeComment("1", new UserPrincipal("pesho@mail.com"));
        Assert.assertFalse(actual);
    }
}
