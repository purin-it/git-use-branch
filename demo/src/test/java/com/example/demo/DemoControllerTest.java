package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DemoControllerTest {

    /**
     * MockMvcオブジェクト
     */
    private MockMvc mockMvc;

    /**
     * テスト対象クラス
     */
    @Autowired
    DemoController target;

    /**
     * 前処理(各テストケースを実行する前に行われる処理)
     */
    @Before
    public void setup() {
        // MockMvcオブジェクトにテスト対象メソッドを設定
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    /**
     * DemoControllerクラスのindexメソッドを確認するためのテスト
     */
    @Test
    public void indexTest() throws Exception{
       // テスト対象メソッド(index)を実行
        mockMvc.perform(get("/"))
                    // HTTPステータスがOKであることを確認
                .andExpect(status().isOk())
                    // 次画面の遷移先がindex.htmlであることを確認
                .andExpect(view().name("index"))
                    // セッションに設定されたsesHashMapの値が正しいことを確認
                .andExpect(request().sessionAttribute("sesHashMap", createHashMap()))
                    // Modelオブジェクトにエラーが無いことを確認
                .andExpect(model().hasNoErrors());
    }

    /**
     * DemoControllerクラスのnextメソッドを確認するためのテスト
     */
    @Test
    public void nextTest() throws Exception {
        HashMap<String, List<String>> paramMap = createHashMap();

        // テスト対象メソッド(next)を実行
        MvcResult results = mockMvc.perform(
                    // セッションsesHashMapを設定し、nextメソッドを実行
                post("/next/").sessionAttr("sesHashMap", paramMap))
                    // HTTPステータスがOKであることを確認
                .andExpect(status().isOk())
                    // 次画面の遷移先がnext.htmlであることを確認
                .andExpect(view().name("next"))
                    // ModelオブジェクトのsessionListに設定される値が正しいことを確認
                .andExpect(model().attribute("sessionList", paramMap.get("key1")))
                    // Modelオブジェクトにエラーが無いことを確認
                .andExpect(model().hasNoErrors())
                .andReturn();

        // テスト対象メソッド(next)実施後のリクエストオブジェクトから、セッションを取得し、
        // セッションが取得できないことを確認
        MockHttpServletRequest returnReq = results.getRequest();
        HttpSession afterSession = returnReq.getSession(false);
        assertNull(afterSession);
    }

    /**
     * セッションに設定するハッシュマップを生成する
     * @return 生成したハッシュマップ
     */
    private HashMap<String, List<String>> createHashMap(){
        HashMap<String, List<String>> hashMap = new HashMap<>();

        List<String> hashList1 = new ArrayList<>();
        hashList1.add("item1");
        hashList1.add("item2");
        hashList1.add("item3");
        hashMap.put("key1", hashList1);

        List<String> hashList2 = new ArrayList<>();
        hashList2.add("item4");
        hashList2.add("item5");
        hashMap.put("key2", hashList2);

        return hashMap;
    }
}
