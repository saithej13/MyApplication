package com.vst.myapplication.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;

@SuppressLint("CommitPrefEdits") public class Preference
{

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor edit;
    public static String IS_INSTALLED 					=	"isInstalled";

    public static String IS_UNLOADSTOCK 				=	"IS_UNLOADSTOCK";
    public static String DEVICE_DISPLAY_WIDTH 				=	"DEVICE_DISPLAY_WIDTH";
    public static String DEVICE_DISPLAY_HEIGHT 				=	"DEVICE_DISPLAY_HEIGHT";
    public static String SYNC_STATUS 					=	"SYNCSTATUS";
    public static String USER_ID 						=	"user_id";
    public static String USER_NAME 						=	"USERNAME";
    public static String SALES_ORG_CODE 				=	"SALES_ORG_CODE";
    public static String SALES_ORG_NAME 				=	"SALES_ORG_NAME";
    public static String USER_NAME_LOG 					=	"USERNAMELOG";
    public static String REGION 						=	"REGION";
    public static String PASSWORD 						=	"PASSWORD";
    public static String SALES_GROUP 					=	"SALES_GROUP";
    public static String USERSUBTYPE 					=	"USERSUBTYPE";
    public static String IS_ONLINE_ONLY 				=	"IS_ONLINE_ONLY";

    public static final String IS_SYNC_FAILED    ="IS_SYNC_FAILED";
    public static String USER_TYPE 						=	"USER_TYPE";
    public static final String LANGUAGE						=	"LANGUAGE";
    //	public static String CUREENT_LATTITUDE 				=	"CUREENT_LATTITUDE";
//	public static String CUREENT_LONGITUDE 				=	"CUREENT_LONGITUDE";
    public static String REMEMBER_ME					=	"REMEMBER_ME";
    public static String RECIEPT_NO						=	"RECIEPT_NO";
    public static String CUSTOMER_SITE_ID				=	"CUSTOMER_SITE_ID";
    public static String LAST_CUSTOMER_SITE_ID			=	"LAST_CUSTOMER_SITE_ID";
    public static String INVOICE_NO						=	"INVOICE_NO";
    public static String CUSTOMER_DETAIL				=	"CUSTOMER_DETAIL";
    public static String LAST_JOURNEY_DATE				=	"LAST_JOURNEY_DATE";
    public static String LAST_SYNC_TIME 				= 	"LAST_SYNC_TIME";
    public static String LAST_SYNC_LSD 					= 	"LAST_SYNC_LSD";
    public static String LAST_SYNC_LST	 				= 	"LAST_SYNC_LST";
    public static String TEMP_EMP_NO 					= 	"TEMP_EMP_NO";
    public static String EMP_NO 						= 	"EMP_NO";
    public static String JOURNEYCODE 					= 	"JOURNEYCODE";
    public static String SALESMAN_TYPE 					= 	"SALESMAN_TYPE";
    public static String IS_DATA_SYNCED_FOR_USER 		= 	"IS_DATA_SYNCED_FOR_USER";
    public static String IS_TELE_ORDER 					= 	"IS_TELE_ORDER";
    public static String IS_EOT_DONE					=	"IS_EOT_DONE";
    public static String IS_EOT_DONE_REPORT				=	"IS_EOT_DONE_REPORT";
    public static String IS_JOURNEY_ENDED				=	"IS_JOURNEY_ENDED";
    public static String IS_EOT_DONE_LATEST				=	"IS_EOT_DONE_LATEST";
    public static String TRIP_DATE_LATEST				=	"TRIP_DATE_LATEST";
    public static String IS_AUDIT_DONE					=	"IS_AUDIT_DONE";
    public static String AUDIT_DATE_LATEST				=	"AUDIT_DATE_LATEST";
    public static String PRINTER_URI					=	"PRINTER_URI";
    public static String AUDIT_DATE						=	"AUDIT_DATE";
    public static String IS_APP_CRASHED					=	"IS_APP_CRASHED";
    public static String TRIP_DATE_TYPE					=	"TRIP_DATE_TYPE";
    public static String BEGINNING_INVENTORY			=	"BEGINNING_INVENTORY";
    public static String IS_EOT_LASTDAY_DONE			=	"IS_EOT_LASTDAY_DONE";
    public static String IS_EOT_UPLOADED				=	"IS_EOT_UPLOADED";
    public static String IsStockVerified				=	"IsStockVerified";
    public static final String CURRENT_VEHICLE			= 	"LAST_TRUCK";
    public static final String OFFLINE_DATE 			= 	"OFFLINE_DATE";

    public static String INT_USER_ID 					=	"int_user_id";
    public static final String DefaultLoad_Quantity 	= 	"DefaultLoad_Quantity";
    public static final String maxLoad_Quantity 	= 	"MaxLoad_Quantity";
    public static final String CUSTOMER_NAME 			= 	"CUSTOMER_NAME";
    public static final String PAYMENT_TYPE 			= 	"PAYMENT_TYPE";
    public static final String PAYMENT_TERMS_DESC 		= 	"PAYMENT_TERMS_DESC";
    public static final String SUB_CHANNEL_CODE 		= 	"SUB_CHANNEL_CODE";
    public static final String CHANNEL_CODE 			= 	"CHANNEL_CODE";
    public static final String CRASH_REPORT 			= 	"CRASH_REPORT";
    public static final String IS_UPLOAD_RUNNING 		= 	"IS_UPLOAD_RUNNING";
    public static final String GET_ALL_TRANSFER_SYNCHTIME = 	"GET_ALL_TRANSFER_SYNCHTIME";
    public static final String CUSTOMER_ID 				= 	"CUSTOMER_ID";
    public static final String LSD 						= 	"LSD";
    public static final String LST 						= 	"LST";
    public static String USER_TYPE_NEW					=	"USER_TYPE_NEW";
    public static String IS_VPN_CONNECTED				=	"IS_VPN_CONNECTED";
    public static final String BUILD_INSTALLATIONDATE    ="BUILD_INSTALLATION_DATE";
    public static final String LSDActiveStatus			= 	"LSDActiveStatus";
    public static final String LSTActiveStatus			= 	"LSTActiveStatus";

    //	public static final String SIGNATURE 				= 	"SIGNATURE";
    public static final String ROUTE_CODE 				= 	"ROUTE_CODE";
    public static final String ROUTE_TYPE 				= 	"ROUTE_TYPE";
    public static final String ROUTE_LIMIT_REMAINING 	= 	"ROUTE_LIMIT_REMAINING";

    public static final String GetAllPromotions			= 	"GetAllPromotions";

    public static final String GetSurveyMasters			= 	"GetSurveyMasters";
    public static final String GetCustomersByUserID		= 	"GetCustomersByUserID";
    public static final String GetTrxHeaderForApp		= 	"GetTrxHeaderForApp";
    public static final String GetJPAndRouteDetails		= 	"GetJPAndRouteDetails";


    public static final String STARTDAY_TIME			= 	"STARTDAY_TIME";
    public static final String TRIP_DATE				= 	"TRIP_DATE";
    public static final String JOURNEY_APP_ID			= 	"JOURNEY_APP_ID";
    public static final String TRIP_DATE_START_DAY		= 	"TRIP_DATE_START_DAY";
    public static final String IS_DAY_STARTED			= 	"IS_DAY_STARTED";
    public static final String IS_ATTANDENCE_DONE			= 	"IS_ATTANDENCE_DONE";
    public static String HTTP_RESPONSE_CODE						= 	"HTTP_RESPONSE_CODE";
    public static String HTTP_RESPONSE_METHOD						= 	"HTTP_RESPONSE_METHOD";
    public static final String NUMBER_OF_DECIMALS 	= 	"NUMBER_OF_DECIMALS";

    public static final String STARTDAY_TIME_ACTUAL		= 	"STARTDAY_TIME_ACTUAL";
    public static final String START_JOURNEY_TIME_ACTUAL= 	"START_JOURNEY_TIME_ACTUAL";
    public static final String START_JOURNEY_REQUEST	= 	"START_JOURNEY_REQUEST";
    public static final String START_JOURNEY_REQUEST_PENDING	= 	"START_JOURNEY_REQUEST_PENDING";

    public static final String ENDAY_TIME				= 	"STARTDAY_TIME";
    public static final String STARTDAY_VALUE			= 	"STARTDAY_VALUE";

    public static final String ENDDAY_VALUE				= 	"ENDDAY_VALUE";
    /** This variable Decides whether the coupon will apply or not */
    public static String PASSCODE_SYNC 					=	"PASSCODE_SYNC";
    public static String TOTAL_TIME_TO_SERVE			=	"TOTAL_TIME_TO_SERVE";

    public static final String DAY_VARIFICATION			= 	"DAY_VARIFICATION";
    public static String GetAllAcknowledgedTask		 	=	"GetAllAcknowledgedTask";
    public static String GetAllTask		 				=	"GetAllTask";

    public static final String CURRENCY_CODE			= 	"Currency_Code";
    public static final String CustomerPerfectStoreMonthlyScoreUID			= 	"CustomerPerfectStoreMonthlyScoreUID";

    public static final String VEHICLE_DO				= 	"vehicleDO";
    public static final String IS_VANSTOCK_FROM_MENU_OPTION	= 	"IS_VANSTOCK_FROM_MENU_OPTION";
    public static final String gcmId					= 	"gcmId";
    public static final String SQLITE_DATE              = 	"SQLITE_DATE";
    public static final String ORG_CODE		            = 	"ORG_CODE";
    public static final String PRINTER_TYPE		            = 	"PrinterType";

    public static final String PRESELLER	            = 	"PreSales";
    public static final String ORDER_PAYMENT_MODE	    = 	"ORDER_PAYMENT_MODE";
    public static final String VISIT_CODE = "VISIT_CODE";
    public static final String CUSTOMER_VISIT_ID = "CUSTOMER_VISIT_ID";

    public static final String ORG_NAME		            = 	"ORG_NAME";
    public static final String ORG_ADD		            = 	"ORG_ADD";
    public static final String ORG_FAX		            = 	"ORG_FAX";
    public static final String SERVER_NAME		            = 	"SERVER_NAME";
    public static final String IS_APP_FIRSTTIME_LAUNCH		= 	"is_app_first_time_launch";
    public static final String IS_FIRSTTIME_LAUNCH		= 	"is_first_time_launch";

    public static final String AUTH_TOKEN = "AUTH_TOKEN";
    public static final String ENDORSEMENT = "ENDORSEMENT";
    public static final String AUDIT_USER_CODE = "AUDIT_USER_CODE";
    public static final String AUDIT_USER_NAME = "AUDIT_USER_NAME";
    public static final String SETTINGS_URL						=	"SETTINGS_URL";
    public static final String DAILY_LOAD_VERIFICATION_DATE = "DAILY_LOAD_VERIFICATION_DATE";
    public static final String GCM_ID = "GCM_ID";

    public static String IS_TIME_STARTED 				= 	"IS_TIME_STARTED";
    public static String CHECK_IN_START_TIME 			= 	"CHECK_IN_START_TIME";
    public static String CHECK_IN_MAX_TIME 				= 	"CHECK_IN_MAX_TIME";
    public static String IS_EXCEED_ALERT_SHOWN				= 	"EXCEED_ALERT_SHOWN";
    public static String IS_CHECKED_IN 				= 	"IS_CHECKED_IN";
    public static String CPS_COUNT 		= "CPS_COUNT";

    public Preference(Context context)
    {
        preferences		=	PreferenceManager.getDefaultSharedPreferences(context);
        edit			=	preferences.edit();
    }

    public void saveStringInPreference(String strKey,String strValue)
    {
        edit.putString(strKey, strValue);
    }
    public void saveIntInPreference(String strKey,int value)
    {
        edit.putInt(strKey, value);
    }
    public void saveBooleanInPreference(String strKey,boolean value)
    {
        edit.putBoolean(strKey, value);
    }
    public void saveLongInPreference(String strKey,Long value)
    {
        edit.putLong(strKey, value);
    }
    public void saveDoubleInPreference(String strKey,String value)
    {
        edit.putString(strKey, value);
    }

    public void removeFromPreference(String strKey)
    {
        edit.remove(strKey);
    }
    public void commitPreference()
    {
        edit.commit();
    }
    public String getStringFromPreference(String strKey,String defaultValue )
    {
        return preferences.getString(strKey, defaultValue);
    }
    public boolean getbooleanFromPreference(String strKey,boolean defaultValue)
    {
        return preferences.getBoolean(strKey, defaultValue);
    }
    public int getIntFromPreference(String strKey,int defaultValue)
    {
        return preferences.getInt(strKey, defaultValue);
    }
    public double getDoubleFromPreference(String strKey,double defaultValue)
    {
        return	Double.parseDouble(preferences.getString(strKey, ""+defaultValue));
    }

    public long getLongInPreference(String strKey)
    {
        return preferences.getLong(strKey, 0);
    }

    public String getAllPreferences(){
        StringBuilder preferenes = new StringBuilder();
        Map<String,?> keys = preferences.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
//			Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
            preferenes.append(entry.getKey() + ": " + entry.getValue().toString() + "\t\t");
        }
        return preferenes.toString();
    }

    public void clearPreferences() {
        edit.clear();
        edit.commit();
    }

    public static Preference getInstance(){
        return new Preference(MyApplicationNew.mContext);
    }
}
