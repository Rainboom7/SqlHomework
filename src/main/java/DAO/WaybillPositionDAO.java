package DAO;

import models.Waybill;
import models.WaybillPosition;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public final class WaybillPositionDAO {
    public WaybillPositionDAO(@NotNull Connection connection) throws SQLException {
        this.statement= connection.createStatement();
    }

    @NotNull
    private Statement statement;
    public void createWaybillPosition(@NotNull Waybill waybill, int price, @NotNull String nomenclature, int quantity) throws SQLException {
        statement.executeUpdate( "INSERT  INTO waybillpositions VALUES ("+waybill.getId()+","+price+",'"+nomenclature+"',"+quantity+");");
    }
    @NotNull
    public List<WaybillPosition> readWaybillPositions(@NotNull Waybill waybill) throws SQLException {
        ResultSet resultSet= statement.executeQuery( "SELECT * FROM waybillpositions WHERE id="+waybill.getId()+"");
        List<WaybillPosition> waybillPositions= new LinkedList<>( );
        while (resultSet.next()){
        waybillPositions.add( new WaybillPosition(waybill,resultSet.getInt( "price" ),
                resultSet.getString( "nomenclature" ),
                resultSet.getInt( "quantity" ) ) );
        }
        return waybillPositions;

    }
    public void updateWaybillPositions(@NotNull Waybill waybill, int oldPrice, int newPrice, @NotNull String newNomenclature, int newQuantity) throws SQLException {
        statement.executeUpdate( "UPDATE waybillpositions SET price="+newPrice+", " +
                "nomenclature='"+newNomenclature+"', " +
                "quantity="+newQuantity+
                " WHERE price="+oldPrice+"" );

    }
    public void deleteWaybillPositions (@NotNull Waybill waybill, int price) throws SQLException {
        statement.executeUpdate( "DELETE FROM waybillpositions WHERE id="+waybill.getId()+"AND price="+price );
    }
}
