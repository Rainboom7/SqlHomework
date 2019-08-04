package DAO;

import models.Nomenclature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class NomenclatureDAO {
    public NomenclatureDAO(@NotNull Connection connection) throws SQLException {
        this.statement= connection.createStatement();
    }

    @NotNull
    private Statement statement;
    public void createNomenclature(@NotNull String name, int code) throws SQLException {
        statement.executeUpdate( "INSERT  INTO nomenclatures VALUES ('"+name+"',"+code+");");
    }
    @Nullable
    public Nomenclature readNomenclature(@NotNull String name) throws SQLException {
        ResultSet resultSet= statement.executeQuery( "SELECT * FROM nomenclatures WHERE name='"+name+"'");
       if(resultSet.next()){
        return new Nomenclature( resultSet.getString( "name" ),resultSet.getInt( "code" ) );}
        return null ;
    }
    public void updateNomenclature(@NotNull String name, int newCode) throws SQLException {
        statement.executeUpdate( "UPDATE nomenclatures SET code="+newCode+" WHERE name='"+name+"'" );

    }
    public void deleteNomenclature (@NotNull String name) throws SQLException {
        statement.executeUpdate( "DELETE FROM nomenclatures WHERE name='"+name+"'" );
    }
}
