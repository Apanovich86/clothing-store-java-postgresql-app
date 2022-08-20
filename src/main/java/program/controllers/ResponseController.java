package program.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import program.DTO.response.AddResponseDTO;
import program.DTO.response.ResponseDTO;
import program.entities.Product;
import program.entities.UserEntity;
import program.mapper.ApplicationMapper;
import program.repositories.ProductRepository;
import program.repositories.ResponseRepository;
import program.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/response")
public class ResponseController {
    @Autowired
    ResponseRepository responseRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ApplicationMapper applicationMapper;

    @GetMapping("/get") // відображення всіх відгуків по товару
    public ResponseEntity<List<ResponseDTO>> getResponse() {
        try {
            List<ResponseDTO> responseDTOList =  applicationMapper
                    .listResponseToListResponseDTO(responseRepository.findAll());
            if (responseDTOList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
            else {
                return new ResponseEntity<>(responseDTOList, HttpStatus.OK);}
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add") // додавання відгуку про товар
    public ResponseEntity<program.entities.ResponseEntity> createResponse(@RequestBody AddResponseDTO addResponseDTO) {
        try {
            program.entities.ResponseEntity responseEntity = applicationMapper.addResponseEntityDTOToResponseEntity(addResponseDTO);
            responseEntity.setComment(addResponseDTO.getComment());
            Product product =  productRepository.getById(addResponseDTO.getProductId());
            responseEntity.setProduct(product);
            UserEntity userEntity = userRepository.getById(addResponseDTO.getUserId());
            responseEntity.setUser(userEntity);
            responseEntity.setRating(addResponseDTO.getRating());
            responseRepository.save(responseEntity);
            return new ResponseEntity<>(responseEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}") // пошук відгуку про товар по id
    public ResponseEntity<program.entities.ResponseEntity> searchByResponseId(@PathVariable long id) {
        Optional<program.entities.ResponseEntity> response = responseRepository.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}") // редагування відгуку про товар
    public ResponseEntity<program.entities.ResponseEntity> updateResponse(@PathVariable("id") long id, @RequestBody program.entities.ResponseEntity responseEntity) {
        Optional<program.entities.ResponseEntity> responseUpdate = responseRepository.findById(id);
        if (responseUpdate.isPresent()) {
            program.entities.ResponseEntity responseChanged = responseUpdate.get();
            responseChanged.setComment(responseEntity.getComment());
            responseChanged.setRating(responseEntity.getRating());
            return new ResponseEntity<>(responseRepository.save(responseChanged), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteColor(@PathVariable("id") long id) {
        try {
            responseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
