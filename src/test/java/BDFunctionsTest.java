import DAO.NomenclatureDAO;
import DAO.OrganizationDAO;
import DAO.WaybillDAO;
import DAO.WaybillPositionDAO;
import models.Nomenclature;
import models.Organization;
import models.Waybill;
import models.WaybillPosition;
import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;

@SuppressWarnings({"ConstantConditions", "SpellCheckingInspection", "SqlDialectInspection", "unused", "NullableProblems"})
public final class BDFunctionsTest {
    @NotNull
    private static final String NOM_1 = "nom1";
    @NotNull
    private static final String NOM_2="nom2";
    @NotNull
    private static final String NOM_3="nom3";
    private static final int CONST_INT = 123;
    @NotNull
    private static final String ORG_1 = "org1";
    @NotNull
    private static final String ORG_2 = "org2";
    private static final int CONST_INT2 = 321;
    @NotNull
    private
    NomenclatureDAO nomenclatureDAO  ;
    @NotNull
    private
    OrganizationDAO organizationDAO ;
    @NotNull
    private
    WaybillDAO waybillDAO ;
    @NotNull
    private
    WaybillPositionDAO waybillPositionDAO;
    @NotNull
    private
    Connection connection;

            @Before
            public  void initialize(){
    Flyway flyway=Flyway.configure().dataSource("jdbc:postgresql://localhost:3600/test","postgres","rainbow7" ).load();
    flyway.migrate();
    try
                {
                     connection= DriverManager.getConnection( "jdbc:postgresql://localhost:3600/test","postgres","rainbow7" );
                    nomenclatureDAO = new NomenclatureDAO( connection );
                     organizationDAO = new OrganizationDAO( connection );
                     waybillDAO = new WaybillDAO( connection );
                     waybillPositionDAO=new WaybillPositionDAO( connection );
                    Random random = new Random( );
                    }
            catch ( SQLException e){
                e.printStackTrace();}
            }
            @After
            public void cleanDB() throws SQLException {
                Statement statement = connection.createStatement();
                statement.executeUpdate( "truncate table waybillpositions,waybill,organizations,nomenclatures" );

            }
            @Test
        public  void daoCreateAndReadNomenclatureTest() throws SQLException {
                try{
                Nomenclature nomenclature=nomenclatureDAO.readNomenclature( NOM_1 );}
                catch ( Exception e ){Assert.assertTrue( e instanceof NullPointerException );}
               nomenclatureDAO.createNomenclature( NOM_1, CONST_INT );
              Nomenclature nomenclature=nomenclatureDAO.readNomenclature( NOM_1 );
                assert nomenclature != null;
                Assert.assertEquals( NOM_1, nomenclature.getName() );}
                @Test
    public void  daoCreateAndReadOrganizationTest() throws SQLException {
                try{
                    Organization organization=organizationDAO.readOrganization( ORG_1 );}
                catch ( Exception e ){Assert.assertTrue( e instanceof NullPointerException );}
                organizationDAO.createOrganization( ORG_1,CONST_INT,CONST_INT );
                Organization organization= organizationDAO.readOrganization( ORG_1 );
                assert organization != null;
                Assert.assertEquals( ORG_1, organization.getName() );}
                @Test
    public void  daoCreateAndReadWaybillTest() throws SQLException{
                try{
                    Waybill waybill=waybillDAO.readWaybill( 1 );}
                catch ( Exception e ){Assert.assertTrue( e instanceof NullPointerException );}
                organizationDAO.createOrganization( ORG_1,CONST_INT,CONST_INT );
                nomenclatureDAO.createNomenclature( NOM_1,CONST_INT);
                waybillDAO.createWaybill( 1,"27-02-2013",ORG_1 );
                Waybill waybill= waybillDAO.readWaybill( 1 );
                assert waybill != null;
                Assert.assertEquals( waybill.getId(), 1 );
                try{
                    List<WaybillPosition> waybillPosition=waybillPositionDAO.readWaybillPositions( waybill );}
                catch ( Exception e ){Assert.assertTrue( e instanceof NullPointerException );}
                waybillPositionDAO.createWaybillPosition( waybill,CONST_INT,NOM_1,CONST_INT );
                List<WaybillPosition> waybillPositions= waybillPositionDAO.readWaybillPositions( waybill );
                for (WaybillPosition waybillPosition:waybillPositions
                     ) {
                    Assert.assertEquals( waybillPosition.getWaybill(),waybill );

                }

            }
            @Test
        public void daoNomenclatureDeleteTest() throws SQLException {
                try {
                    nomenclatureDAO.readNomenclature( NOM_2 );
                }
                catch ( Exception e ){
                    Assert.assertTrue( e instanceof NullPointerException );
                }
                nomenclatureDAO.createNomenclature( NOM_2,CONST_INT );
                nomenclatureDAO.createNomenclature( NOM_3,CONST_INT2 );
                Nomenclature nomenclature=nomenclatureDAO.readNomenclature( NOM_2 );
                Assert.assertEquals(nomenclature.getCode(),CONST_INT );
                nomenclatureDAO.deleteNomenclature( NOM_2 );
                try{
                    nomenclatureDAO.readNomenclature( NOM_2 );
                }
                catch ( Exception e ){Assert.assertTrue( e instanceof NullPointerException );
                }
               Assert.assertEquals(  nomenclatureDAO.readNomenclature( NOM_3 ).getCode(),CONST_INT2 );

            }
            @Test
            public void daoOrganizationDeleteTest() throws SQLException {
                try {
                    organizationDAO.readOrganization( NOM_2 );
                } catch ( Exception e ) {
                    Assert.assertTrue( e instanceof NullPointerException );
                }
                organizationDAO.createOrganization( ORG_1, CONST_INT, CONST_INT );
                organizationDAO.createOrganization( ORG_2, CONST_INT2, CONST_INT );
                Organization organization = organizationDAO.readOrganization( ORG_1 );
                Assert.assertEquals( organization.getCheking( ), CONST_INT );
                nomenclatureDAO.deleteNomenclature( ORG_1 );
                try {
                    nomenclatureDAO.readNomenclature( ORG_1 );
                } catch ( Exception e ) {
                    Assert.assertTrue( e instanceof NullPointerException );
                }
                Assert.assertEquals( organizationDAO.readOrganization( ORG_2 ).getCheking( ), CONST_INT );


            }
            @Test
    public void daoWaybillDeleteTest() throws SQLException {
        try {
            waybillDAO.readWaybill( 1 );
        } catch ( Exception e ) {
            Assert.assertTrue( e instanceof NullPointerException );
        }
        organizationDAO.createOrganization( ORG_1,CONST_INT,CONST_INT );
        nomenclatureDAO.createNomenclature( NOM_1,CONST_INT );
        waybillDAO.createWaybill(1, "1231-02-27", ORG_1 );
        waybillDAO.createWaybill( 2,"20-02-2001",ORG_1 );
      Waybill waybill=waybillDAO.readWaybill( 1 );
        Assert.assertEquals( waybill.getDate( ), "1231-02-27" );
        waybillPositionDAO.createWaybillPosition( waybill,CONST_INT,NOM_1 , CONST_INT2 );
        waybillPositionDAO.createWaybillPosition( waybill,CONST_INT,NOM_1,CONST_INT );
        List<WaybillPosition> waybills=waybillPositionDAO.readWaybillPositions(waybill);
        Assert.assertEquals( 2, waybills.size( ) );
        try {
            waybillDAO.deleteWaybill( 1 );
        }catch ( Exception e ){Assert.assertTrue( e instanceof  SQLException );}
        waybillPositionDAO.deleteWaybillPositions( waybill,CONST_INT );
        waybills=waybillPositionDAO.readWaybillPositions(waybill);
        Assert.assertEquals( 0, waybills.size( ) );
    }
    @Test
    public void allDAOUpdateTest() throws SQLException {
                organizationDAO.createOrganization( ORG_1,CONST_INT,CONST_INT );
                nomenclatureDAO.createNomenclature( NOM_1,CONST_INT );
                waybillDAO.createWaybill( 1,"20-01-1232",ORG_1 );
                Waybill waybill=waybillDAO.readWaybill( 1 );
        Assert.assertNotNull( waybill );
        Assert.assertEquals( CONST_INT, organizationDAO.readOrganization( ORG_1 ).getInn( ) );
        Assert.assertEquals( CONST_INT, nomenclatureDAO.readNomenclature( NOM_1 ).getCode( ) );
                waybillPositionDAO.createWaybillPosition( waybill,CONST_INT,NOM_1,CONST_INT );
                organizationDAO.updateOrganizationByName(ORG_1,CONST_INT2,CONST_INT2  );
        Assert.assertEquals( CONST_INT2, organizationDAO.readOrganization( ORG_1 ).getInn( ) );
        Assert.assertEquals( CONST_INT2, organizationDAO.readOrganization( ORG_1 ).getCheking( ) );
                nomenclatureDAO.updateNomenclature( NOM_1,CONST_INT2 );
        Assert.assertEquals( CONST_INT2, nomenclatureDAO.readNomenclature( NOM_1 ).getCode( ) );
                waybillDAO.updateWaybill( 1,"2019-07-19" , ORG_1 );
        Assert.assertEquals( "2019-07-19", waybillDAO.readWaybill( 1 ).getDate( ) );
        waybillPositionDAO.updateWaybillPositions( waybill,CONST_INT,CONST_INT2,NOM_1,CONST_INT);
        Assert.assertEquals( waybillPositionDAO.readWaybillPositions( waybill).get( 0 ).getQuantity(),CONST_INT );
        Assert.assertEquals( waybillPositionDAO.readWaybillPositions( waybill).get( 0).getPrice(),CONST_INT2 );

    }
}
