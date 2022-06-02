package controller;

import model.Book;
import model.Issue;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookCrudController {
    public static ArrayList<String> getItemCodes() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT book_id FROM book");
        ArrayList<String> codeList = new ArrayList<>();
        while (result.next()) {
            codeList.add(result.getString(1));
        }
        return codeList;
    }

    public static Book getItem(String code) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Book WHERE book_id=?", code);
        if (result.next()) {
            return new Book(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getInt(5)
            );
        }
        return null;
    }

    public static ArrayList<String> getIssueId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT issue_id FROM issue");
        ArrayList<String> codeList = new ArrayList<>();
        while (result.next()) {
            codeList.add(result.getString(1));
        }
        return codeList;
    }

    public static Issue getId(String code) throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT * FROM issue WHERE issue_id=?", code);
        if (result.next()) {
            return new Issue(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getDouble(7)
            );
        }
        return null;
    }
}
