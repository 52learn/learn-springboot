package com.example.learn.springboo.data.repository.handler;

import org.apache.ibatis.executor.parameter.ParameterHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Unused
 */
public class MyParameterHandler implements ParameterHandler {
    @Override
    public Object getParameterObject() {
        return null;
    }

    @Override
    public void setParameters(PreparedStatement ps) throws SQLException {

    }
}
