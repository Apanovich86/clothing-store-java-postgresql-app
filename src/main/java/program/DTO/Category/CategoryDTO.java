package program.DTO.category;

import lombok.Data;
import program.DTO.product.ProductDTO;

import java.util.List;

@Data
public class CategoryDTO {
    private String name;
    private List<ProductDTO> productDTOList;
}
