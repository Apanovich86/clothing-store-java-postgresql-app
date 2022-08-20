package program.DTO.response;

import lombok.Data;

@Data
public class AddResponseDTO {
    private String comment;
    private Long productId;
    private Long userId;
    private int rating;
}
