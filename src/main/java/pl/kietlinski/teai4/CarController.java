package pl.kietlinski.teai4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CarController {

    private CarService carService;
    private Logger logger;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
        logger = LoggerFactory.getLogger(CarService.class);
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars(@RequestParam(required = false, defaultValue = "-1") long id,
                                             @RequestParam(required = false, defaultValue = "none") String color) {
        List<Car> carList = carService.getCarList();
        if(id != -1){
            List<Car> optionalCar = carList.stream()
                    .filter(car -> car.getId() == id)
                    .collect(Collectors.toList());
            if(optionalCar.isEmpty()){
                logger.info("Could not found car by id");
            } else {
                logger.info("Found car by id was returned");
            }
            return new ResponseEntity<>(optionalCar, HttpStatus.OK);
        }
        if(!color.equals("none")){
            List<Car> optionalCar = carList.stream()
                    .filter(car -> car.getColor().equals(color))
                    .collect(Collectors.toList());
            if(optionalCar.isEmpty()){
                logger.info("Could not found car by color");
            } else {
                logger.info("Found car by color was returned");
            }
            return new ResponseEntity<>(optionalCar, HttpStatus.OK);
        }
        logger.info("Found cars was returned");
        return new ResponseEntity<>(carList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@Validated @RequestBody Car newCar) {
        newCar.setId(carService.getCarList().size());
        carService.getCarList().add(newCar);
        logger.info("Car was added");
        return new ResponseEntity<>(newCar, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Car> modifyCar(@RequestBody Car newCar) {
        List<Car> carList = carService.getCarList();
        Optional<Car> optionalCar = carList.stream().filter(car -> car.getId() == newCar.getId()).findFirst();

        if(optionalCar.isPresent()){
            Car car = optionalCar.get();
            carList.remove(car);
            carList.add(newCar);
            logger.info("Car was modified");
            return new ResponseEntity<>(newCar, HttpStatus.OK);
        }
        logger.info("Car was not modified");
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping
    public ResponseEntity deleteCar(@RequestParam long id) {
        List<Car> carList = carService.getCarList();
        Optional<Car> optionalCar = carList.stream().filter(car -> car.getId() == id).findFirst();
        if (optionalCar.isPresent()) {
            carList.remove(optionalCar.get());
            logger.info("Car was deleted");
            return new ResponseEntity(HttpStatus.OK);
        }
        logger.info("Car was not deleted");
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
