package controller;

import model.Member;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberCrudController {
    public static ArrayList<String> getItemCodes() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT member_id FROM member");
        ArrayList<String> codeList = new ArrayList<>();
        while (result.next()) {
            codeList.add(result.getString(1));
        }
        return codeList;
    }

    public static Member getItem(String code) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Member WHERE member_id=?", code);
        if (result.next()) {
            return new Member(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getDouble(5),
                    result.getString(6)
            );
        }
        return null;
    }
}
