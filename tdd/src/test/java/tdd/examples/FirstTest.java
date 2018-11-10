package tdd.examples;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FirstTest {
    // 테스트 대상은 필드로 선언
    MyUtil myutil;
    @Before
    public void init(){
        myutil = new MyUtil(); // 초기화
        System.out.println("init");
    }
    @Test
    public void test3() throws Exception{
        System.out.println("test3");
    }
    @Test
    public void test1() throws Exception{
        System.out.println("test1");
    }
    @Test
    public void test2() throws Exception{
        System.out.println("test2");
    }
    @After
    public void destroy(){
        myutil = null;
        System.out.println("destroy");
    }

}
