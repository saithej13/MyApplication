package com.vst.myapplication.Room;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.vst.myapplication.dataObject.MilkDataSummary;
import com.vst.myapplication.dataObject.RateAndDetails;
import com.vst.myapplication.dataObject.advanceDO;
import com.vst.myapplication.dataObject.customerDO;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.dataObject.ratedetailsDO;

import java.util.List;
@Dao
public interface RoomService {
//    @Insert
//    void insertOrder(tblbranch branch);
//    @Update
//    void updateOrder(tblbranch branch);
//    @Delete
//    void deleteOrder(tblbranch branch);
//
//    @Query("SELECT * FROM tblbranch order by slno desc")
//    LiveData<List<tblbranch>> getbranch();
//
//    @Query("SELECT IFNULL(MAX(slno), 0) + 1 AS slno FROM tblbranch")
//    LiveData<Integer> getGetBranchSlnoLiveData();

    //OrderDetails
//    @Insert
//    void insertDetails(tblsettings settings);
//    @Update
//    void updateDetails(tblsettings settings );
//    @Delete
//    void deleteDetails(tblsettings settings);
//
//    @Query("SELECT * FROM tblsettings")
//    LiveData<List<tblsettings>> getSettings();

    @Insert
    void insertMilkdata(milkDO mdata);
    @Insert
    void insertAdvance(advanceDO advance);

    @Insert
    void insertCustomer(customerDO customer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertRates(rateDO rates);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void UpdateRates(rateDO rates);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRatesdetails(ratedetailsDO ratedetails);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void UpdateRatesdetails(ratedetailsDO ratedetails);

    @Query("UPDATE tblrates SET MILKTYPE=:milkType,RATETYPE=:ratetype, STARTDATE=:startDate, ENDDATE=:endDate,BCODE=:BCODE WHERE SLNO=:slno")
    void updateRates(int slno, String milkType,String ratetype, String startDate, String endDate,String BCODE);

    @Query("DELETE FROM tblratesdetails WHERE SLNO=:slno AND BCODE=:BCODE")
    void deleteRateDetails(int slno,int BCODE);

    //Products
    @Insert
    void insertFarmer(farmerDO farmers);
    @Update
    void updateFarmer(farmerDO farmers);
    @Delete
    void deleteFarmer(farmerDO farmers);

    @Query("SELECT * FROM tblfarmers WHERE BCODE=:BCODE")
    LiveData<List<farmerDO>> getfarmers(int BCODE);

    @Transaction
    @Query("SELECT * FROM tblrates r inner join tblratesdetails rd on r.SLNO=rd.SLNO WHERE r.BCODE=:BCODE AND rd.BCODE=:BCODE")
    LiveData<List<RateAndDetails>> getrates(int BCODE);

    @Transaction
    @Query("SELECT * FROM tblrates r inner join tblratesdetails rd on r.SLNO=rd.SLNO where r.SLNO=:slno AND r.BCODE=:BCODE AND rd.BCODE=:BCODE")
    LiveData<List<RateAndDetails>> getratesbyslno(String slno,int BCODE);

    //    @Query("SELECT * FROM tblrates where MILKTYPE=:mtype and :tdate between STARTDATE and ENDDATE and :fat between FATMIN and FATMAX and :snf between SNFMIN and SNFMAX")
//    LiveData<List<rateDO>> gettsrate(String mtype, String tdate, double fat, double snf);
    @Transaction
    @Query("SELECT RD.RATE FROM tblrates R INNER JOIN tblratesdetails RD ON R.SLNO = RD.SLNO WHERE MILKTYPE=:MILKTYPE AND RATETYPE='Purchase' AND :TDATE BETWEEN R.STARTDATE AND R.ENDDATE AND :FAT BETWEEN RD.FATMIN AND RD.FATMAX AND :SNF BETWEEN RD.SNFMIN AND RD.SNFMAX AND R.BCODE=:BCODE AND RD.BCODE=:BCODE LIMIT 1")
    LiveData<Double> gettsrate(String MILKTYPE, String TDATE, double FAT, double SNF,int BCODE);

    @Transaction
    @Query("SELECT RD.RATE FROM tblrates R INNER JOIN tblratesdetails RD ON R.SLNO = RD.SLNO WHERE MILKTYPE=:MILKTYPE AND RATETYPE='Sale' AND :TDATE BETWEEN R.STARTDATE AND R.ENDDATE AND :FAT BETWEEN RD.FATMIN AND RD.FATMAX AND :SNF BETWEEN RD.SNFMIN AND RD.SNFMAX AND R.BCODE=:BCODE AND RD.BCODE=:BCODE LIMIT 1")
    LiveData<Double> gettssalerate(String MILKTYPE, String TDATE, double FAT, double SNF,int BCODE);

    @Query("SELECT * FROM tblfarmers WHERE FARMERID = :code AND BCODE=:BCODE")
    LiveData<List<farmerDO>> getFarmerByCode(int code,int BCODE);

    @Query("SELECT * FROM tblfarmers WHERE SLNO = :SLNO AND BCODE=:BCODE")
    LiveData<List<farmerDO>> getFarmerBySLNO(int SLNO,int BCODE);

    @Query("SELECT * FROM tblmilkdata where TDATE=:tdate and SHIFT=:shift AND BCODE=:BCODE")
    LiveData<List<milkDO>> getmilkdata(String tdate, String shift, int BCODE);
    @Query("SELECT TDATE,SUM(QUANTITY) AS QTY FROM tblmilkdata WHERE TDATE BETWEEN :STARTDATE AND :ENDDATE AND BCODE=:BCODE GROUP BY TDATE ORDER BY TDATE DESC")
    LiveData<List<MilkDataSummary>> getmilkdataforbarchart(String STARTDATE, String ENDDATE,int BCODE);

    @Query("SELECT * FROM tblmilkdata where TDATE BETWEEN :STARTDATE AND :ENDDATE AND BCODE=:BCODE")
    LiveData<List<milkDO>> getmilkdata2(String STARTDATE, String ENDDATE,int BCODE);

//    @Query("SELECT * FROM tblrates where MILKTYPE=:mtype and :tdate between STARTDATE and ENDDATE and :fat between FATMIN and FATMAX and :snf between SNFMIN and SNFMAX")
//    LiveData<List<rateDO>> gettsrate(String mtype, String tdate, double fat, double snf);
    //SELECT * FROM TBLRATES R WHERE MILKTYPE AND STARTDATE AND ENDDATE INNER JOIN TBLRATESDETAILS RD ON R.SLNO = RD.SLNO

//    @Query("SELECT * FROM tblrates where MILKTYPE=:mtype and STARTDATE <=:ENDDATE AND ENDDATE >=:STARTDATE AND FATMIN<=:FATMAX AND FATMAX>=:FATMIN AND SNFMIN<=:SNFMAX AND SNFMAX>=:SNFMIN")
//    LiveData<List<rateDO>> validaterate(String mtype, String STARTDATE,String ENDDATE,double FATMIN,double FATMAX,double SNFMIN,double SNFMAX);

    @Query("SELECT * FROM tblfarmers where FARMERNAME like '%' || :firstname || '%' AND BCODE=:BCODE")
    LiveData<List<farmerDO>> getFilteredFarmers(String firstname,int BCODE);
    @Query("SELECT * FROM tblfarmers WHERE FARMERID = :code AND BCODE=:BCODE")
    List<farmerDO> getfarmerbyid(int code,int BCODE);

//    @Query("SELECT * FROM tblrates where MILKTYPE=:mtype and :fatmin between FATMIN and FATMAX and :fatmax between FATMIN and FATMAX")
//    LiveData<List<rateDO>> validateFat(String mtype,double fatmin,double fatmax);

//    @Query("update tblfarmers set avlqty=:remqty where productid=:productid")
//    void updateProductAvlQty(String productid,double remqty);


    @Query("SELECT * FROM tbladvances WHERE BCODE=:BCODE")
    LiveData<List<advanceDO>> getadvances(int BCODE);
    @Query("SELECT * FROM tbladvances where SLNO=:SLNO AND BCODE=:BCODE LIMIT 1")
    LiveData<List<advanceDO>> getadvancesbyslno(int SLNO,int BCODE);

    @Query("SELECT * FROM tblcustomer WHERE BCODE=:BCODE")
    LiveData<List<customerDO>> getcustomers(int BCODE);
    @Query("SELECT * FROM tblcustomer WHERE SLNO=:SLNO AND BCODE=:BCODE LIMIT 1")
    LiveData<List<customerDO>> getcustomerbyslno(int SLNO,int BCODE);

    @Query("DELETE FROM tblfarmers WHERE SLNO = :SLNO AND BCODE=:BCODE")
    void deleteFarmerId(int SLNO,int BCODE);

    @Query("DELETE FROM TBLCUSTOMER WHERE SLNO = :SLNO AND BCODE=:BCODE")
    void deleteCustomerId(int SLNO,int BCODE);

    @Query("SELECT FARMERID FROM TBLFARMERS WHERE SLNO = :SLNO AND BCODE=:BCODE")
    int getFarmerIdBySlno(int SLNO,int BCODE);

    @Query("SELECT CUSTOMERCODE FROM TBLCUSTOMER WHERE SLNO = :SLNO AND BCODE=:BCODE")
    int getCustomerIdBySlno(int SLNO, int BCODE);

    @Query("SELECT COUNT(*) FROM TBLMILKDATA WHERE FARMERID = :FARMERID AND BCODE=:BCODE")
    int countMilkpurchaseDataRecordsByFarmerId(int FARMERID,int BCODE);

    @Query("SELECT COUNT(*) FROM TBLMILKSALEDATA WHERE CUSTOMERID = :CUSTOMERID AND BCODE=:BCODE")
    int countMilksaleDataRecordsByFarmerId(int CUSTOMERID,int BCODE);

    @Query("SELECT IFNULL(MAX(FARMERID), 0) + 1 AS ID FROM tblfarmers where BCODE=:BCODE")
    int getNextFarmerId(int BCODE);

    @Query("SELECT IFNULL(MAX(CUSTOMERCODE), 0) + 1 AS ID FROM tblcustomer where BCODE=:BCODE")
    int getNextCustomerId(int BCODE);


    @Query("UPDATE tbladvances SET TDATE=:TDATE,NAME=:NAME,CUSTOMERTYPE=:CUSTOMERTYPE,AMOUNT=:AMOUNT,REMARKS=:REMARKS WHERE ID=:ID AND SLNO=:SLNO AND BCODE=:BCODE")
    void updateAdvance(String TDATE,String NAME,String CUSTOMERTYPE,String AMOUNT,String REMARKS,String ID,int BCODE,int SLNO);

    @Query("UPDATE tblcustomer SET CUSTOMERCODE=:CUSTOMERCODE,CUSTOMERNAME=:CUSTOMERNAME,MOBILENO=:MOBILENO,ISACTIVE=:ISACTIVE WHERE  SLNO=:SLNO AND BCODE=:BCODE")
    void updateCustomer(String CUSTOMERCODE,String CUSTOMERNAME,String MOBILENO,boolean ISACTIVE,int SLNO,int BCODE);

    @Query("UPDATE tblfarmers SET FARMERNAME=:FARMERNAME,MOBILENO=:MOBILENO,ISACTIVE=:ISACTIVE,MILKTYPE=:MILKTYPE WHERE FARMERID=:FARMERID AND SLNO =:SLNO AND BCODE=:BCODE")
    void updateFarmer(String FARMERNAME,String MOBILENO,boolean ISACTIVE,String MILKTYPE,int FARMERID,int BCODE,int SLNO);
}
