package tdd.examples;

import java.lang.reflect.Method;

public class Exam04 { // Exam03
    public static void main(String[] args)
            throws Exception{

        String className = "tdd.examples.MyUtil";
        // MyUtil클래스의 메소드중 @MyTest 애노테이션이 붙은 메소드이름만 출력하시오.
        Class clazz = Class.forName(className);
        Object obj = clazz.newInstance();

        Method[] declaredMethods = clazz.getDeclaredMethods();
        //모든 메소드의 메소드 이름을 출력한다.
        for(Method method: declaredMethods){
            MyTest annotation = method.getAnnotation(MyTest.class);
            if(annotation != null) {
                method.invoke(obj, null);
            }
        }

    }
}
