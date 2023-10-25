package org.Bondflix.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Model {

    public abstract void createFromSQL(ResultSet rs) throws SQLException;
}
