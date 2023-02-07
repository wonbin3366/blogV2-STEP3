package shop.mtcoding.momo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import shop.mtcoding.momo.model.User;

// @AutoConfigureMockMvc 웹 애플리케이션에서 컨트롤러를 테스트 할 때, 서블릿 컨테이너를 모킹하기 위해
// @SpringBootTest가 하는 역할은 @SpringBootApplication을 찾아서 테스트를 위한 빈들을 다 생성한다. 그리고 @MockBean으로 정의된 빈을 찾아서 교체한다.
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession mockSession;

    @BeforeEach // Test 메서드 실행 직전에 호출됨
    public void setUp() {
        User user = new User();
        user.setId(1);
        user.setUsername("ssar");
        user.setPassword("1234");
        user.setEmail("ssar@nate.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", user);
    }

    @Test
    public void sava_test() throws Exception {
        // given
        String title = "";
        for (int i = 0; i < 99; i++) {
            title += "가";
        }

        String requestBody = "title=" + title + "&content=내용1";

        // when
        ResultActions resultActions = mvc.perform(post("/board")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .session(mockSession));

        // then
        resultActions.andExpect(status().is3xxRedirection());
    }
}