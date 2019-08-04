
import DAO.NomenclatureDAO;
import DAO.OrganizationDAO;
import DAO.WaybillDAO;
import DAO.WaybillPositionDAO;
import models.Waybill;
import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public final class Application {

    public static void main(@NotNull String[] args) {
       Flyway flyway=Flyway.configure().dataSource("jdbc:postgresql://localhost:3600/test","postgres","rainbow7" ).load();
       flyway.clean();
       flyway.migrate();
    try( Connection connection= DriverManager.getConnection( "jdbc:postgresql://localhost:3600/test","postgres","rainbow7" ) )
    {
        taskPreparations( connection );
        Statement statement= connection.createStatement();
       taskThreePointOne( statement );
      taskThreePointTwo( statement );
        taskThreePointThree( statement );
        taskThreePointFour( statement );
        taskThreePointFive( statement );

    }
    catch(SQLException e){
        e.printStackTrace();
        }
    }

    private static void taskThreePointFive(Statement statement) throws SQLException {
        Scanner scanner = new Scanner( System.in );
        System.out.println("Enter the first date of the interval(yyyy/mm/dd)" );
        String firstDate=scanner.next();
        System.out.println("Enter the second date of the interval (yyyy/mm/dd)" );
        String secondDate = scanner.next();
        ResultSet resultSet=statement.executeQuery( "SELECT a.organization,waybillpositions.nomenclature FROM (SELECT id,organization FROM waybill where date between '"+firstDate+"' and '"+secondDate+"' group by id)AS A,waybillpositions WHERE A.id=waybillpositions.id" );
        while ( resultSet.next() ){
            System.out.println(resultSet.getString( 1 )+"  " +resultSet.getString( 2 ) );
        }
        ResultSet notStatedResultSet=statement.executeQuery( "SELECT organization FROM waybill where date not between '"+firstDate+"' and '"+secondDate+"' group by organization ");
        while ( notStatedResultSet.next() ){
            System.out.println(notStatedResultSet.getString( 1 ) +" no shipment");
        }
    }

    private static void taskThreePointFour(Statement statement) throws SQLException {
        Scanner scanner = new Scanner( System.in );
        System.out.println("Enter the first date of the interval(yyyy/mm/dd)" );
        String firstDate=scanner.next();
        System.out.println("Enter the second date of the interval (yyyy/mm/dd)" );
        String secondDate = scanner.next();
        ResultSet resultSet=statement.executeQuery( "SELECT SUM(priSuM)/SUM (quaSUm)FROM(SELECT SUM(quantity) AS quaSum,SUM(price)as priSum,a.date FROM " +
                "(SELECT id,date FROM waybill where date between '"+firstDate+"' and '"+secondDate+"' group by id) AS A,waybillpositions group by  A.date order by a.date)AS C" );
        while ( resultSet.next() ){
            System.out.println("Average price of one element in this period is:"+ resultSet.getBigDecimal( 1 ));
        }
    }

    private static void taskThreePointThree(Statement statement) throws SQLException {
        Scanner scanner = new Scanner( System.in );
        System.out.println("Enter the first date of the interval(yyyy/mm/dd)" );
        String firstDate=scanner.next();
        System.out.println("Enter the second date of the interval (yyyy/mm/dd)" );
        String secondDate = scanner.next();
        ResultSet resultSet=statement.executeQuery( "SELECT SUM(quantity),SUM(price),a.date FROM (SELECT id,date FROM waybill where date between '"+firstDate+"' and '"+secondDate+"' group by id) AS A,waybillpositions group by  A.date order by a.date" );
        while ( resultSet.next() ){
            System.out.println(
                    "quantity: " + resultSet.getBigDecimal( 1 )+
                     "  revenue: "+resultSet.getBigDecimal( 2 )+
                     "  date: "+resultSet.getString( 3 ) );

        }
    }

    private static void taskThreePointOne(@NotNull Statement statement) throws SQLException {
        ResultSet resultSet =statement.executeQuery(  "SELECT organization  FROM (SELECT SUMDESC.SUMA,id FROM(SELECT  id,SUM(quantity) AS SUMA FROM waybillpositions GROUP BY waybillpositions.id )AS SUMDESC ORDER BY  SUMA DESC)AS B,waybill WHERE waybill.id=B.id limit 10");
        while (resultSet.next()){
        System.out.println(resultSet.getString( 1) );
        }
    }
    private static void taskThreePointTwo(@NotNull Statement statement) throws SQLException {
        Scanner scanner = new Scanner( System.in );
        System.out.println("Enter minimum sum that will be selected" );
        int expectedValue = scanner.nextInt();
        ResultSet resultSet =statement.executeQuery("SELECT organization  FROM (SELECT SUMDESC.SUMA,id FROM(SELECT  id,SUM(quantity) AS SUMA FROM waybillpositions GROUP BY waybillpositions.id )AS SUMDESC ORDER BY  SUMA DESC)AS B,waybill WHERE b.SUMA>"+expectedValue+"AND waybill.id=B.id");
        while ( resultSet.next()){
            System.out.println(resultSet.getString( 1 ) );
    }}

     private static void taskPreparations(@NotNull Connection connection) throws SQLException {
        NomenclatureDAO nomenclatureDAO = new NomenclatureDAO( connection );
        OrganizationDAO organizationDAO = new OrganizationDAO( connection );
        WaybillDAO waybillDAO = new WaybillDAO( connection );
        WaybillPositionDAO waybillPositionDAO=new WaybillPositionDAO( connection );
        @NotNull
        Waybill tempWaybill;
        Random random = new Random(  );
        for (int i=1;i<21;i++){
        nomenclatureDAO.createNomenclature( "nomen"+i,random.nextInt(Integer.MAX_VALUE) );
        organizationDAO.createOrganization( "org"+i,random.nextInt(Integer.MAX_VALUE),random.nextInt(Integer.MAX_VALUE));
        waybillDAO.createWaybill(i,i+"-02-2013" ,"org"+i );
        tempWaybill= Objects.requireNonNull( waybillDAO.readWaybill( i ) );
        for (int j=1;j<6;j++){
            waybillPositionDAO.createWaybillPosition(tempWaybill,random.nextInt(Integer.MAX_VALUE),"nomen"+i,random.nextInt(Integer.MAX_VALUE));
        }
        }
    }
}
