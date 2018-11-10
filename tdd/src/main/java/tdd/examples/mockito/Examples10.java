package tdd.examples.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import java.util.List;

/*
이때까지의 예제에서는 stub 할 때 모두 특정값을 넣었다.
만약 mock의 상태나 메소드 인자값에 따라 다른 값을 돌려주게 하게 만들고 싶다면 어떻게 해야할까.
Answer<?> 클래스를 사용하면 가능하다.
 */
@RunWith(MockitoJUnitRunner.class)
public class Examples10 {
    @Mock
    private List mockedList;

    @Test
    public void test() {
        // int get(int)
        when(mockedList.get(anyInt())).thenAnswer(new Answer<Integer>() {
            public Integer answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments(); // arguments
                List mock = (List)invocation.getMock(); // mock itself
                int result = (Integer)args[0] + 1;
                return result;
            }
        });
        // int size();    0<=x<=10

        // 람다표현식: Answer는 메소드가 1개다. 메소드가 한개인 인터페이스를 함수형인터페이스라고한다.
        // 함수형 인터페이스를 사용하는 이름없는 객체는 람다표현식으로 바꿀 수 있다.
        when(mockedList.size()).thenAnswer(
            (invocation) -> {
                int result = (int)(Math.random()*10) + 1;
                return result;
            }
        );

        System.out.println(mockedList.get(1)); // called with argument: 2\
        System.out.println(mockedList.size());
    }
}
