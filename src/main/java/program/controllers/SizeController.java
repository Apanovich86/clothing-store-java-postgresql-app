package program.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import program.DTO.category.CategoryDTO;
import program.DTO.size.SizeDTO;
import program.entities.ColorEntity;
import program.entities.ESizeEntity;
import program.entities.SizeEntity;
import program.mapper.ApplicationMapper;
import program.repositories.SizeRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/size")
public class SizeController {
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    ApplicationMapper applicationMapper;

    @GetMapping("/get") // відображення всіх розмірів
    public ResponseEntity<List<SizeDTO>> getSizes() {
        try {
            List<SizeDTO> sizeDTOList =  applicationMapper
                    .listSizeToListSizeDTO(sizeRepository.findAll());
            if (sizeDTOList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
            else {
                return new ResponseEntity<>(sizeDTOList, HttpStatus.OK);}
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/searchByName") // пошук розміру за назвою
    public ResponseEntity<SizeEntity> searchBySizeName(@RequestParam String name){
        Optional<SizeEntity> size = sizeRepository.findByName(ESizeEntity.valueOf(name));
        if (size.isPresent()) {
            return new ResponseEntity<>(size.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}") // пошук розміру по id
    public ResponseEntity<SizeEntity> searchByColorId(@PathVariable int id) {
        Optional<SizeEntity> size = sizeRepository.findById(id);
        if (size.isPresent()) {
            return new ResponseEntity<>(size.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
