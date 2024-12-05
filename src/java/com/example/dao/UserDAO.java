package com.example.dao;

import Consts.ID;
import Consts.Queries;
import com.example.dto.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO
{
    private final Connection conn;

    public UserDAO(Connection conn)
    {
        this.conn = conn;
    }

    //1. hàm check xem có người dúng đó hay không
    public boolean authenticateUser(String userID, String password) throws SQLException
    {
        try (PreparedStatement ptm = conn.prepareStatement(Queries.LOGIN))
        {
            ptm.setString(1, userID);
            ptm.setString(2, password);

            try (ResultSet rs = ptm.executeQuery())
            {
                return rs.next();
            }
        }
    }

    public boolean checkAdmin(String userID, String password) throws SQLException
    {
        try (PreparedStatement ptm = conn.prepareStatement(Queries.CHECK_ADMIN))
        {
            ptm.setString(1, userID);
            ptm.setString(2, password);

            try (ResultSet rs = ptm.executeQuery())
            {
                if (rs.next())
                {
                    return rs.getString(1).equals(ID.ADMIN);
                }
            }
        }
        return false;
    }

    //2. hàm tạo ra người dùng mới 
    public boolean createUser(User user) throws SQLException
    {
        try (PreparedStatement ptm = conn.prepareStatement(Queries.CREATE_USER))
        {
            ptm.setString(1, user.getUserID());
            ptm.setString(2, user.getFullName());
            ptm.setString(3, user.getEmail());
            ptm.setString(4, user.getPhoneNumber());
            ptm.setString(5, user.getRoleID());
            ptm.setString(6, user.getPassword());
            return ptm.executeUpdate() > 0;
        }
    }

    // 3. hàm kiểm tra người dùng đã tồn tại chưa
    public boolean isUserExist(String userID) throws SQLException
    {
        try (PreparedStatement pstmt = conn.prepareStatement(Queries.CHECK_USER_EXIST))
        {
            pstmt.setString(1, userID);
            try (ResultSet rs = pstmt.executeQuery())
            {
                return rs.next();
            }
        }
    }
}
