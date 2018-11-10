package examples.boot.simpleboard;

import lombok.*;

import java.net.URLEncoder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PagerModel {
    private int thisPage;
    private String searchType;
    private String searchStr;
    private int buttonCount;
    private int totalPage;
    private long totalCount;
    public static final int PAGE_LIMIT = 10;

    public int getStartPage() {
        if(thisPage / buttonCount * buttonCount == thisPage){
            return thisPage - buttonCount + 1;
        }
       int value = thisPage - (thisPage / PAGE_LIMIT * PAGE_LIMIT);
       if(value > buttonCount){ // 버튼카운트가 5일 경우 page의 시작은 6이라는 것을 나타낸다.
           return thisPage / buttonCount * buttonCount + 1;
       }else{
           return thisPage / buttonCount * buttonCount + 1;
       }
    }

    public int getEndPage() {
        if(thisPage / buttonCount * buttonCount == thisPage){
            return thisPage;
        }
        int value = thisPage - (thisPage / PAGE_LIMIT * PAGE_LIMIT);
        if(value > buttonCount){
            return thisPage / buttonCount * buttonCount + buttonCount;
        }else{
            return thisPage / buttonCount * buttonCount + buttonCount;
        }
    }

    public int getPrevPage() {
        int prevPage =  getStartPage() - 1;
        if(prevPage <= 0){
            prevPage = 0;
        }
        return prevPage;
    }

    public int getNextPage(){
        int nextPage = getEndPage() + 1;
        if(nextPage > totalPage){
            nextPage = 0;
        }
        return nextPage;
    }
}