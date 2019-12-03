package binzh.wan.comechat.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class img {
    private Integer id;
    private Integer userId;
    private String imgUrl;
    private String username;
}
