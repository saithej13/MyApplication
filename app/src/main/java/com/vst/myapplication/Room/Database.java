package com.vst.myapplication.Room;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.vst.myapplication.Utils.MyApplicationNew;
import com.vst.myapplication.dataObject.advanceDO;
import com.vst.myapplication.dataObject.customerDO;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.dataObject.ratedetailsDO;
import com.vst.myapplication.dataObject.userDO;

@androidx.room.Database(entities = {farmerDO.class,   milkDO.class, rateDO.class, ratedetailsDO.class, userDO.class, advanceDO.class, customerDO.class},version = 1,exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static Database instance;
    public abstract RoomService roomService();
    public static synchronized Database getInstance(){
        if(instance==null){
            instance = Room.databaseBuilder(MyApplicationNew.mContext,Database.class,"Database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
        }
    };
//    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
//        private final WinITServices winITServices;
//
//        private PopulateDbAsyncTask(Database db){
//            winITServices = db.WinITServices();
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            winITServices.insertProduct(new products("WIN25046582","SmartTV",10000,25,"PCS",10,0));
//            winITServices.insertProduct(new products("WIN3465148","LED TV",30000,20,"PCS",10,0));
//            winITServices.insertProduct(new products("WIN147521645","WALLMOUNT",5000,10,"PCS",0,0));
//            winITServices.insertProduct(new products("WIN14751234","LAPTOP",50000,20,"PCS",0,0));
//            winITServices.insertProduct(new products("147525678","MOBILE",15000,10,"PCS",0,0));
//            return null;
//        }
//    }
}
