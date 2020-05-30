package com.oogie.controller;

import java.sql.Connection;

public class BaseService {
    protected final Connection conn;

    public BaseService(Connection conn) {
        this.conn = conn;
    }
}
