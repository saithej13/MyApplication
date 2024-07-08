package com.vst.myapplication.Room;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.vst.myapplication.dataObject.RateAndDetails;
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertRates(rateDO rates);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRatesdetails(ratedetailsDO ratedetails);
    //Products
    @Insert
    void insertFarmer(farmerDO farmers);
    @Update
    void updateFarmer(farmerDO farmers);
    @Delete
    void deleteFarmer(farmerDO farmers);

    @Query("SELECT * FROM tblfarmers")
    LiveData<List<farmerDO>> getfarmers();

    @Transaction
    @Query("SELECT * FROM tblrates r inner join tblratesdetails rd on r.SLNO=rd.SLNO")
    LiveData<List<RateAndDetails>> getrates();

    @Query("SELECT * FROM tblfarmers WHERE FARMERID = :code")
    LiveData<List<farmerDO>> getFarmerByCode(int code);

    @Query("SELECT * FROM tblmilkdata where TDATE=:tdate and SHIFT=:shift")
    LiveData<List<milkDO>> getmilkdata(String tdate, String shift);

//    @Query("SELECT * FROM tblrates where MILKTYPE=:mtype and :tdate between STARTDATE and ENDDATE and :fat between FATMIN and FATMAX and :snf between SNFMIN and SNFMAX")
//    LiveData<List<rateDO>> gettsrate(String mtype, String tdate, double fat, double snf);

//    @Query("SELECT * FROM tblrates where MILKTYPE=:mtype and STARTDATE <=:ENDDATE AND ENDDATE >=:STARTDATE AND FATMIN<=:FATMAX AND FATMAX>=:FATMIN AND SNFMIN<=:SNFMAX AND SNFMAX>=:SNFMIN")
//    LiveData<List<rateDO>> validaterate(String mtype, String STARTDATE,String ENDDATE,double FATMIN,double FATMAX,double SNFMIN,double SNFMAX);

    @Query("SELECT * FROM tblfarmers where FARMERNAME like '%' || :firstname || '%'")
    LiveData<List<farmerDO>> getFilteredFarmers(String firstname);
    @Query("SELECT * FROM tblfarmers WHERE FARMERID = :code")
    List<farmerDO> getfarmerbyid(int code);

//    @Query("SELECT * FROM tblrates where MILKTYPE=:mtype and :fatmin between FATMIN and FATMAX and :fatmax between FATMIN and FATMAX")
//    LiveData<List<rateDO>> validateFat(String mtype,double fatmin,double fatmax);

//    @Query("update tblfarmers set avlqty=:remqty where productid=:productid")
//    void updateProductAvlQty(String productid,double remqty);
}
