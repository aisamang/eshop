package kz.ais.eshop.controllers;

import kz.ais.eshop.models.Characteristic;
import kz.ais.eshop.services.CharacteristicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class CharacteristicController {

    private CharacteristicService characteristicService;

    public CharacteristicController(CharacteristicService characteristicService){
        this.characteristicService=characteristicService;
    }

    @GetMapping(path = "characteristics/all")
    public ResponseEntity<List<Characteristic>> index(){
        return  new ResponseEntity<List<Characteristic>>(characteristicService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "characteristics/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Characteristic characteristic = characteristicService.getById(id);

        return characteristic!=null ? new ResponseEntity<Characteristic>(characteristic, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "categories/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Characteristic characteristic = characteristicService.getById(id);
        return characteristicService.delete(characteristic)? ResponseEntity.status(HttpStatus.OK).build():
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "characteristics/{id}", method = {RequestMethod.PATCH , RequestMethod.PUT})
    public ResponseEntity update(@RequestBody Characteristic characteristic){
        return characteristicService.update(characteristic) ? new ResponseEntity<Characteristic>(characteristic, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "characteristics/add")
    public ResponseEntity<Characteristic> add(@RequestBody Characteristic characteristic){
        return characteristicService.add(characteristic) ? new ResponseEntity<Characteristic>(characteristic,HttpStatus.ACCEPTED):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @GetMapping(path = "characteristics/name")
    public ResponseEntity getByName(@RequestParam String name){
        Characteristic characteristic = characteristicService.getByName(name);

        return characteristic!=null ? new ResponseEntity<Characteristic>(characteristic, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
