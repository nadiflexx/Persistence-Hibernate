package org.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "expedientes")
public class Expedient {
    @Id
    private int id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellidos")
    private String surnames;
    @Column(name = "dni")
    private String dni;
    @Column(name = "nmascotas")
    private int npets;
    @Column(name = "fechaalta")
    private Date dischargeDate;
    @Column(name = "cp")
    private String postalCode;
    @Column(name = "telefono")
    private String phone;
    @Column(name = "usuarioalta")
    private int userId;

    public Expedient(int id, String name, String surnames, String dni, int npets, Date dischargeDate, String postalCode, String phone, int userId) {
        this.id = id;
        this.name = name;
        this.surnames = surnames;
        this.dni = dni;
        this.npets = npets;
        this.dischargeDate = dischargeDate;
        this.postalCode = postalCode;
        this.phone = phone;
        this.userId = userId;
    }

    public Expedient() {
    }

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

    public int getNpets() {
        return npets;
    }

    public void setNpets(int npets) {
        this.npets = npets;
    }

    public Date getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}