package DAO;
import models.Organization;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("SqlDialectInspection")
public final class OrganizationDAO {
    public OrganizationDAO(@NotNull Connection connection) throws SQLException {
        this.statement= connection.createStatement();
    }

    @NotNull
    private Statement statement;
    public void createOrganization(@NotNull String name, int inn, int checking) throws SQLException {
        statement.executeUpdate( "INSERT  INTO organizations VALUES ('"+name+"',"+inn+","+checking+");");
    }
    @Nullable
    public Organization readOrganization(@NotNull String name) throws SQLException {
       ResultSet resultSet= statement.executeQuery( "SELECT * FROM organizations WHERE organization='"+name+"'");
       if(resultSet.next()){
       return new Organization( resultSet.getString( "organization" ),resultSet.getInt( "inn" ),resultSet.getInt( "checking" ) );}
       return null;
    }
    public void updateOrganizationByName(@NotNull String name, int newInn, int newChecking) throws SQLException {
        statement.executeUpdate( "UPDATE organizations SET inn="+newInn+", checking=" +newChecking+" WHERE organization='"+name+"'" );

    }
    public void deleteOrganization(@NotNull String name) throws SQLException {

        statement.executeUpdate( "DELETE FROM organizations WHERE organization='"+name+"'" );
    }
}
