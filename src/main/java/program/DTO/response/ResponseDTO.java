package program.DTO.response;

import lombok.Data;

@Data
public class ResponseDTO {
    private String comment;
    private Long productId;
    private Long userId;
    private int rating;
}
