package pl.kietlinski.teai4;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class CarService {

    private List<Car> carList;
    private Logger logger;

    public CarService() {
        carList = new ArrayList<>();
        logger = LoggerFactory.getLogger(CarService.class);
        init();
    }

    public void init() {
        Car car1 = new Car(1L, "BMW", "X5", "BLACK", 2010);
        Car car2 = new Car(2L, "Audi", "A5", "WHITE", 2012);
        Car car3 = new Car(3L, "Seat", "Exeo", "BLACK", 2011);
        carList.add(car1);
        carList.add(car2);
        carList.add(car3);
        logger.info("Prepare data was created");
    }

}
