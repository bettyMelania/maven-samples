import java.io.Serializable;

public class Person implements Serializable{
    private String name;
    private String cnp;
    private String mail;

    public Person(String name, String cnp, String mail) {
        this.name = name;
        this.cnp = cnp;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public String getCnp() {
        return cnp;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", cnp='" + cnp + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
