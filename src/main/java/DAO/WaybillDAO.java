package DAO;
import models.Waybill;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

public final class WaybillDAO {
    public WaybillDAO(@NotNull Connection connection) throws SQLException {
        this.statement= connection.createStatement();
    }

    @NotNull
    private Statement statement;
    public void createWaybill(int id, @NotNull String date, @NotNull String organization) throws SQLException {
        statement.executeUpdate( "INSERT  INTO waybill VALUES ("+id+",'"+date+"','"+organization+"');");
    }
    @Nullable
    public Waybill readWaybill( int id) throws SQLException {
        ResultSet resultSet= statement.executeQuery( "SELECT * FROM waybill WHERE id="+id+"");
        if ( resultSet.next()){
        return new Waybill( resultSet.getInt( "id" ),resultSet.getString( "date" ),resultSet.getString( "organization" ) );}
        return null;
    }
    public void updateWaybill(int id, @NotNull String newDate, @NotNull String newOrganization) throws SQLException {
        statement.executeUpdate( "UPDATE waybill SET date='"+newDate+"', organization= '"+newOrganization+"' WHERE id="+id+"" );

    }
    public void deleteWaybill ( int id) throws SQLException {
        statement.executeUpdate( "DELETE FROM waybill WHERE id="+id+"" );
    }
}
