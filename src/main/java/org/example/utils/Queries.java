package org.example.utils;

public class Queries {
    //SELECT
    public final String SELECT_TABLE_EXPEDIENT = "FROM Expedient";
    public final String SELECT_TABLE_EXPEDIENT_BY_ID = "FROM Expedient where id=:id";
    public final String SELECT_MAX_ID_FROM_EXPEDIENT = "SELECT max(id) FROM Expedient";
    public final String SELECT_TABLE_USER = "FROM User";
    public final String SELECT_MAX_ID_FROM_USER = "SELECT max(id) FROM User";
    public final String SELECT_NAME_FROM_USER_BY_ID = "SELECT name FROM User where id=:id";

    //DELETE
    public final String DELETE_FROM_EXPEDIENT_BY_ID = "delete from Expedient where id=:id";
    public final String DELETE_FROM_USER_BY_ID = "delete from User where id=:id";

    //UPDATE
    public final String UPDATE_DATE_USER = "update from User set lastAccess=:date where dni=:dni";
    public final String UPDATE_PASSWORD_USER = "update from User set pass=:pass where id=:id";
    public final String UPDATE_TYPE_USER = "update from User set tipousuario=:type where id=:id";
    public final String UPDATE_PETS_EXPEDIENT = "update from Expedient set nmascotas=:npets where id=:id";
    public final String UPDATE_PHONE_EXPEDIENT = "update from Expedient set telefono=:phone where id=:id";
    public final String UPDATE_POSTAL_EXPEDIENT = "update from Expedient set cp=:postalCode where id=:id";
    public final String UPDATE_ID_EXPEDIENT = "update from Expedient set usuarioalta=:newUserId where usuarioalta=:oldUserId";
}