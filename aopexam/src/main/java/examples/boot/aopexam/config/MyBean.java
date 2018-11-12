package examples.boot.aopexam.config;

public class MyBean {
    private String name;

    public MyBean(){
        System.out.println("!!!! MyBean()");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
