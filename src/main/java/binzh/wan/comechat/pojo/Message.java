package binzh.wan.comechat.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Integer id;
    private Integer fromId;
    private String fromUsername;
    private Integer toId;
    private String msg;
}
