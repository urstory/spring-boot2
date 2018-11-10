package examples.boot.simpleboard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "upload_file")
@Getter
@Setter
public class UploadFile {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @Column(name = "content_type")
    private String contentType;

    private Long length;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "save_file_name")
    private String saveFileName;

    private LocalDateTime regdate;

    public void setBoard(Board board){
        this.board = board;
        if(!board.getUploadFiles().contains(this)){
            board.getUploadFiles().add(this);
        }
    }
}
