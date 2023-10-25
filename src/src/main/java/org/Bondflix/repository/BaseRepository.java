package org.Bondflix.repository;

import java.sql.Connection;
import java.util.List;

public class BaseRepository<Model> {
    protected Connection dbConnection;
    protected String tableName;

    protected  BaseRepository(Connection dbConnection, String tableName) {
        this.dbConnection = dbConnection;
        this.tableName = tableName;
    }

    public Model create(Model model) throws Exception {
        throw new Exception("Implement this");
    }

    public Model update(Model model) throws Exception {
        throw new Exception("Implement this");
    }

    public Model delete(Model model) throws Exception {
        throw new Exception("Implement this");
    }

    public List<Model> findAll() throws Exception {
        throw new Exception("Implement this");
    }

    public Model findById(Integer modelId) throws Exception {
        throw new Exception("Implement this");
    }
}
