package examples.boot.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodayService {
    private TodayBean todayBean;

    // 생성자 주입.
    public TodayService(TodayBean todayBean){
        this.todayBean = todayBean;
        System.out.println("TodayService(TodayBean)");
    }

    public String getTime(){
        return "오늘의 날짜와 시간 : " + todayBean.today();
    }
}
