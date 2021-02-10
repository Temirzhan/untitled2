import java.sql.*;
import java.util.List;

public class NewSql {
    private Connection connection;
    private Statement statement;
    private ResultSet rs;
    private   String primaryLoad;
    private   String updateStr = "insert into Cat(Name, Health, Damage,Satiety) values ('";

    public NewSql(String primaryLoad) {
        this.primaryLoad = primaryLoad;
    }


    public void newConect() throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:Zoo2.db");
        statement = connection.createStatement();
        statement.execute(primaryLoad);
    }

    public void updateSql(Cat cat) throws SQLException {

        statement.executeUpdate(updateStr+cat.getName()+"', "+cat.getHealth()+", "+cat.getDamage()+", "+cat.isSatiety()+");");
    }

    public void updateSql(Cat[] cat) throws SQLException {
        for (Cat c:cat) {
            statement.executeUpdate(updateStr+c.getName()+"', "+c.getHealth()+", "+c.getDamage()+", "+c.isSatiety()+");");
        }

    }

    public void close()  {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Cat getSql(String name) throws SQLException {

        rs =statement.executeQuery("select from Cat where Name like " + name+";");
        Cat c = new Cat(rs.getString("Name")
                ,rs.getInt("Health")
                ,rs.getInt("Damage")
        );

        return c;
    }

    public void renameSql(String name, String newName) throws SQLException {
        statement.executeQuery("update Cat set Name " + newName + "  where Name like " + name+";");
    }


    public void renameSql(String name, int newHealth, int newDamage) throws SQLException {
        statement.executeQuery("update Cat set Health " + newHealth + "set Demage " + newDamage + "  where Name like " + name+";");
    }


    public void deleteSql(String name) throws SQLException {

        statement.executeQuery("delete from Cat where Name like " + name+";");

    }

    public void printSql() throws SQLException {
        rs =statement.executeQuery("select * from Cat;");
        while (rs.next()) {

            System.out.println("Cat " + rs.getString("Name")
                    + " Health: " + rs.getInt("Health")
                    + " Damage: " + rs.getInt("Damage")
                    + " Satiety: " + rs.getBoolean("Satiety"));


            close();
        }
    }

}