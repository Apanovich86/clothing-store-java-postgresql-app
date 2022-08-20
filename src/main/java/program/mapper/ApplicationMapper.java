package program.mapper;

import org.mapstruct.Mapper;
import program.DTO.category.AddCategoryDTO;
import program.DTO.category.CategoryDTO;
import program.DTO.color.AddColorDTO;
import program.DTO.color.ColorDTO;
import program.DTO.response.AddResponseDTO;
import program.DTO.response.ResponseDTO;
import program.DTO.size.SizeDTO;
import program.entities.Category;
import program.entities.ColorEntity;
import program.entities.ResponseEntity;
import program.entities.SizeEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    CategoryDTO categoryToCategoryDTO(Category category);
    List<CategoryDTO> listCategoryToListCategoryDTO(List<Category> categories);
    Category addCategoryDTOToCategory(AddCategoryDTO addCategoryDTO);

    List<ColorDTO> listColorToListColorDTO(List<ColorEntity> colors);

    ColorEntity addColorEntityDTOToColorEntity(AddColorDTO addColorDTO);

    List<ResponseDTO> listResponseToListResponseDTO(List<ResponseEntity> responses);

    ResponseEntity addResponseEntityDTOToResponseEntity(AddResponseDTO addResponseDTO);

    List<SizeDTO> listSizeToListSizeDTO(List<SizeEntity> sizes);
}

