package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DemoUtilTest {

    /**
     * クラス名
     * (initメソッド内で設定する)
     */
    private String classPath;

    /**
     * 前処理(各テストケースを実行する前に行われる処理)
     */
    @Before
    public void init(){
        // クラスパスをフルパスで設定
        classPath = this.getClass().getName();
    }

    /**
     * リクエストオブジェクトがNULLの場合を確認するためのテスト
     */
    @Test
    public void testGetHashList01(){
        // メソッド名を取得し、開始ログを出力
        String methodName
                = new Object(){}.getClass().getEnclosingMethod().getName();
        printStartLog(methodName);

        // カバレッジを100%にするためテスト対象クラスを生成
        new DemoUtil();

        // テスト対象メソッドを実行
        List<String> hashList = DemoUtil.getHashList("key01");
        System.out.println(
                "DemoUtil.getHashListメソッドで取得したhashList : " + hashList);
        assertNull(hashList);

        // 終了ログを出力
        printEndLog(methodName);
    }

    /**
     * セッションがNULLの場合を確認するためのテスト
     */
    @Test
    public void testGetHashList02(){
        // メソッド名を取得し、開始ログを出力
        String methodName
                = new Object(){}.getClass().getEnclosingMethod().getName();
        printStartLog(methodName);

        // テスト対象メソッドを実行するためのリクエストオブジェクトを生成
        HttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletWebRequest(request));

        // テスト対象メソッドを実行
        List<String> hashList = DemoUtil.getHashList("key01");
        System.out.println(
                "DemoUtil.getHashListメソッドで取得したhashList : " + hashList);
        assertNull(hashList);

        // 終了ログを出力
        printEndLog(methodName);
    }

    /**
     * セッションからHashMapが取得できない場合を確認するためのテスト
     */
    @Test
    public void testGetHashList03(){
        // メソッド名を取得し、開始ログを出力
        String methodName
                = new Object(){}.getClass().getEnclosingMethod().getName();
        printStartLog(methodName);

        // テスト対象メソッドを実行するためのリクエスト・セッションオブジェクトを生成
        HttpServletRequest request = new MockHttpServletRequest();
        ((MockHttpServletRequest)request).setSession(new MockHttpSession());
        RequestContextHolder.setRequestAttributes(new ServletWebRequest(request));

        // テスト対象メソッドを実行
        List<String> hashList = DemoUtil.getHashList("key01");
        System.out.println(
                "DemoUtil.getHashListメソッドで取得したhashList : " + hashList);
        assertNull(hashList);

        //終了ログを出力
        printEndLog(methodName);
    }

    /**
     * セッションからHashMapとその対応するリストが取得できる場合を確認するためのテスト
     */
    /*
      注意事項：
      HashMapにキー項目が無い場合、キー項目にnullや空文字が指定された場合など、
      他にもいろいろなケースは考えられるが、hashMap.get(key)の動作を確認するだけという観点で
      テストを行えばよいので、この1ケースのみでよい
     */
    @Test
    public void testGetHashList04(){
        // メソッド名を取得し、開始ログを出力
        String methodName
                = new Object(){}.getClass().getEnclosingMethod().getName();
        printStartLog(methodName);

        // セッションに設定するハッシュマップを生成
        MockHttpSession session = new MockHttpSession();
        HashMap<String, List<String>> hashMap = new HashMap<>();
        hashMap.put("key1", Arrays.asList("item1", "item2", "item3"));
        hashMap.put("key2", Arrays.asList("item4", "item5"));
        session.setAttribute("sesHashMap", hashMap);

        // テスト対象メソッドを実行するためのリクエスト・セッションオブジェクトを生成
        HttpServletRequest request = new MockHttpServletRequest();
        ((MockHttpServletRequest)request).setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletWebRequest(request));

        // テスト対象メソッドを実行
        List<String> hashList = DemoUtil.getHashList("key1");
        System.out.println(
                "DemoUtil.getHashListメソッドで取得したhashList : " + hashList);
        assertEquals(hashMap.get("key1").toString(), hashList.toString());

        // 終了ログを出力
        printEndLog(methodName);
    }

    /**
     * 開始ログを出力
     * @param methodName メソッド名
     */
    private void printStartLog(String methodName){
        System.out.println("===== "
                + classPath + "クラス " + methodName + "メソッド 開始 =====");
    }

    /**
     * 終了ログを出力
     * @param methodName メソッド名
     */
    private void printEndLog(String methodName){
        System.out.println("===== "
                + classPath + "クラス " + methodName + "メソッド 終了 =====");
        System.out.println();
    }
}
