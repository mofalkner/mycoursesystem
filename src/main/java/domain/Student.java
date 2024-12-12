package domain;

import java.sql.Date;

public class Student extends BaseEntity {
    private String vn;
    private String nn;
    private Date geburtstag;

    public Student(Long id, String vn, Date geburtstag, String nn) {
        super(id);
        this.vn = vn;
        this.geburtstag = geburtstag;
        this.nn = nn;
    }

    public Student(String vn, Date geburtstag, String nn) {
        super(null);
        this.vn = vn;
        this.geburtstag = geburtstag;
        this.nn = nn;
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
        if (nn != null) {
            this.nn = nn;
        } else {
            throw new InvalidValueException("Der Nachname darf nicht leer sein!");
        }
    }

    public Date getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(Date geburtstag) {
        if (geburtstag != null) {
            this.geburtstag = geburtstag;
        } else {
            throw new InvalidValueException("Der Geburtstag darf nicht leer sein!");
        }
    }

    public String getVn() {
        return vn;
    }

    public void setVn(String vn) {
        if (vn != null) {
            this.vn = vn;
        } else {
            throw new InvalidValueException("Der Vorname darf nicht leer sein!");
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "vn='" + vn + '\'' +
                ", nn='" + nn + '\'' +
                ", geburtstag=" + geburtstag +
                '}';
    }
}