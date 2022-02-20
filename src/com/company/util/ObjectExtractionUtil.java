package com.company.util;

import com.company.model.Cat;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectExtractionUtil {
    public static Cat resultToCat(ResultSet result) throws SQLException {
        return new Cat(
                result.getLong("idcats"),
                result.getString("name"),
                result.getInt("age")
        );
    }
}
