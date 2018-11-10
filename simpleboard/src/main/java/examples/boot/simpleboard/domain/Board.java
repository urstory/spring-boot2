package examples.boot.simpleboard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Setter
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Board implements Serializable {
    public Board(){
        readCount = 0;
        regdate = LocalDateTime.now();
    }
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;
    private String title;

    @Column(columnDefinition = "TEXT") // jpa 2.0 이상 지원
    private String content;
    private int readCount;
    private LocalDateTime regdate;

    @OneToMany(mappedBy = "board", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    List<UploadFile> uploadFiles = new ArrayList<>();

    public void addUploadFile(UploadFile uploadFile){
        this.uploadFiles.add(uploadFile);
        if(uploadFile.getBoard() != this){
            uploadFile.setBoard(this);
        }
    }
}
