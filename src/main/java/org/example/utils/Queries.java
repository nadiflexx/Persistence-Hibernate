package org.example.utils;

public class Queries {
    //SELECT
    public final String SELECT_TABLE_EXPEDIENT = "FROM Expedient";
    public final String SELECT_TABLE_EXPEDIENT_BY_ID = "FROM Expedient where id=:id";
    public final String SELECT_MAX_ID_FROM_EXPEDIENT = "SELECT max(id) FROM Expedient";
    public final String SELECT_TABLE_USER = "FROM User";
    public final String SELECT_MAX_ID_FROM_USER = "SELECT max(id) FROM User";
    //DELETE
    public final String DELETE_FROM_EXPEDIENT_BY_ID =  "delete from Expedient where id =:id";
    public final String DELETE_FROM_USER_BY_ID =  "delete from User where id =:id";
}
