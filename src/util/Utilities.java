package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utilities {

    public static String UID;

    public static boolean end=true;
    public static boolean endone=true;

    public static void navigate(AnchorPane anchorPane, String location) throws IOException, IOException {

        anchorPane.getChildren().clear();
        Parent parent = FXMLLoader.load(Utilities.class.getResource("../View/"+location));
        anchorPane.getChildren().add(parent);

    }

    public static void notifycate(String text,String title){
        Notifications notifications=Notifications.create();

        notifications.darkStyle();
        notifications.text(text);
        notifications.title(title);
        notifications.hideAfter(Duration.seconds(4));
        notifications.showError();
    }
    public static void notifycateconfrm(String text,String title){
        Notifications notifications=Notifications.create();



        notifications.darkStyle();
        notifications.text(text);
        notifications.title(title);
        notifications.hideAfter(Duration.seconds(4));
        notifications.showInformation();
    }


//    public static String iDIncrement(String table,String column) throws SQLException, ClassNotFoundException{
//
//        String incrementID=null;
//
//        ResultSet maxId = CrudUtil.execute("SELECT CONCAT(MAX(0+SUBSTRING("+column+",3))) FROM "+table+"");
//
//        String id=null;
//
//        while (maxId.next()){
//            id=maxId.getString(1);
//        }
//
//        if (id!=null){
//            int nextID = Integer.parseInt(id);
//            nextID++;
//            String cptl =table.substring(1,2);
//            char v=cptl.charAt(0);
//            char second = Character.toUpperCase(v);
//            String first =table.substring(0,1);
//            incrementID = first+second+nextID;
//
//
//        }else {
//            String cptl =table.substring(1,2);
//            char v=cptl.charAt(0);
//            char second = Character.toUpperCase(v);
//            String first =table.substring(0,1);
//            incrementID = first+second+"1";
//
//
//        }
//
//
//
//        return incrementID;
//    }
//


}
