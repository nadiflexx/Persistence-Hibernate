package org.example.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name="usuarios")
public class User {
    @Id
    private int id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellidos")
    private String surnames;
    @Column(name = "dni")
    private String dni;
    @Column(name = "matricula")
    private String license;
    @Column(name = "pass")
    private String password;
    @Column(name = "tipousuario")
    private int userType;
    @Column(name = "ultimoacceso")
    private Date lastAccess;

    public User(int id, String name, String surnames, String dni, String license, String password, int userType, Date lastAccess) {
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.dni = dni;
        this.license = license;
        this.password = password;
        this.userType = userType;
        this.lastAccess = lastAccess;
    }

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }
}