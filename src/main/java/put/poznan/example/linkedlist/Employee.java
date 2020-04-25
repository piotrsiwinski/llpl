package put.poznan.example.linkedlist;

// Data class
class Employee {
    private final long id;
    private final String name;
    private final String surname;
    private final String email;

    public Employee(long id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
}
