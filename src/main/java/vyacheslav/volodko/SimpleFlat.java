package vyacheslav.volodko;

import javax.persistence.*;

@Entity
@Table(name="SimpleFlat")
public class SimpleFlat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="district", nullable = false)
    private String district;
    private String street;
    private int room;
    private int prise;

    public SimpleFlat() {}

    public SimpleFlat(String district, String street, int room, int prise) {
        this.district = district;
        this.street = street;
        this.room = room;
        this.prise = prise;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getPrise() {
        return prise;
    }

    public void setPrise(int prise) {
        this.prise = prise;
    }

    @Override
    public String toString() {
        return "SimpleFlat{" +
                "id=" + id +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", room=" + room +
                ", prise=" + prise +
                '}';
    }
}
