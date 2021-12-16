package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car", schema = "public", catalog = "ShowroomTest")
public class CarEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="car_seq")
    @SequenceGenerator(name="car_seq",
            sequenceName="car_id_seq", allocationSize=1)
    @Column(name = "car_id")
    private int carId;

    @Basic
    @Column(name = "name",length = 40, nullable = false)
    private String name;

    @Basic
    @Column(name = "model",length = 100, nullable = false)
    private String model;

    @Basic
    @Column(name = "year", nullable = false)
    private int year;

    @Basic
    @Column(name = "engine", nullable = false)
    private float engine;

    @Basic
    @Column(name = "price")
    private float price;

    @Basic
    @Column(name = "quantity")
    private Integer quantity;

    @Basic
    @Column(name = "complectation", length = 50, nullable = false)
    private String complectation;

    public int getCarId() {
        return carId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getEngine() {
        return engine;
    }

    public void setEngine(float engine) {
        this.engine = engine;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getComplectation() {
        return complectation;
    }

    public void setComplectation(String complectation) {
        this.complectation = complectation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarEntity carEntity = (CarEntity) o;
        return carId == carEntity.carId && year == carEntity.year && Float.compare(carEntity.engine, engine) == 0 && Objects.equals(name, carEntity.name) && Objects.equals(model, carEntity.model) && Objects.equals(price, carEntity.price) && Objects.equals(quantity, carEntity.quantity) && Objects.equals(complectation, carEntity.complectation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, name, model, year, engine, price, quantity, complectation);
    }
}
