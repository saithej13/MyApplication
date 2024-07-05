var  config = require('./dbconfig');
const  sql = require('mssql');
const { response } = require('express');


  async function addfarmer(FARMERID,FARMERNAME,MOBILENO,MILKTYPE,ISACTIVE){
    try{
            let pool = await sql.connect(config);
            let insertuser = await pool.request()
            .input('mode',sql.Int,1) //PARAMETERS
            .input('farmerid',sql.Int,FARMERID)
            .input('farmername',sql.VarChar,FARMERNAME)
            .input('mobileno',sql.VarChar,MOBILENO)
            .input('milktype',sql.VarChar,MILKTYPE)
            .input('isactive',sql.Int,ISACTIVE)
            .execute("SP_FARMERS");// STORED PROCEDURE
            return insertuser.recordsets;

    }
    catch (err) {
      throw new Error('Error executing SQL request: '+err.message);
  }
  }
  async function addmilk(TDATE,SHIFT,FARMERID,FARMERNAME,MILKTYPE,QUANTITY,FAT,SNF,RATE,AMOUNT){
    try{
            let pool = await sql.connect(config);
            let insertuser = await pool.request()
            .input('mode',sql.Int,1) //PARAMETERS
            .input('TDATE',sql.VarChar,TDATE)
            .input('SHIFT',sql.VarChar,SHIFT)
            .input('FARMERID',sql.Int,FARMERID)
            .input('FARMERNAME',sql.VarChar,FARMERNAME)
            .input('MILKTYPE',sql.VarChar,MILKTYPE)
            .input('QUANTITY',sql.Decimal,QUANTITY)
            .input('FAT',sql.Decimal,FAT)
            .input('SNF',sql.Decimal,SNF)
            .input('RATE',sql.Decimal,RATE)
            .input('AMOUNT',sql.Decimal,AMOUNT)
            //.input('MILKTYPE',sql.Int,ISACTIVE)
            .execute("SP_MILKDATA");// STORED PROCEDURE
            return insertuser.recordsets;

    }
    catch (err) {
      throw new Error('Error executing SQL request: '+err.message);
  }
  }

  async  function  getfarmers() {
    try {
      let  pool = await  sql.connect(config);
      let  Farmers = await  pool.request()
      .input('mode',sql.Int,2)//PARAMETERS
      .execute("SP_FARMERS");//STORED PROCEDURE
      if(Farmers.recordsets.length>0){
        return  Farmers.recordsets[0];
      }
      else{
       return null;
      }
    }
    catch (err) {
      throw new Error('Error executing SQL request: '+err.message);
    }
  }
  async  function  getfarmerbyid(FARMERID) {
    try {
      let  pool = await  sql.connect(config);
      let  Farmers = await  pool.request()
      .input('mode',sql.Int,5)//PARAMETERS
      .input('FARMERID',sql.Int,FARMERID)//PARAMETERS
      .execute("SP_FARMERS");//STORED PROCEDURE
      if(Farmers.recordsets.length>0){
        return  Farmers.recordsets[0];
      }
      else{
       return null;
      }
    }
    catch (err) {
      throw new Error('Error executing SQL request: '+err.message);
    }
  }
  async  function  gettsrate(MILKTYPE,TDATE,FAT,SNF) {
    try {
      let  pool = await  sql.connect(config);
      let  Farmers = await  pool.request()
      .input('mode',sql.Int,6)//PARAMETERS
      .input('MILKTYPE',sql.VarChar,MILKTYPE)//PARAMETERS
      .input('TDATE',sql.VarChar,TDATE)//PARAMETERS
      .input('FAT',sql.Decimal,FAT)//PARAMETERS
      .input('SNF',sql.Decimal,SNF)//PARAMETERS
      .execute("SP_RATES");//STORED PROCEDURE
      if(Farmers.recordsets.length>0){
        return  Farmers.recordsets[0];
      }
      else{
       return null;
      }
    }
    catch (err) {
      throw new Error('Error executing SQL request: '+err.message);
    }
  }
  async  function  getrates() {
    try {
      let  pool = await  sql.connect(config);
      let  RATES = await  pool.request()
      .input('mode',sql.Int,2)//PARAMETERS
      .execute("SP_RATES");//STORED PROCEDURE
      if(RATES.recordsets.length>0){
        return  RATES.recordsets[0];
      }
      else{
       return null;
      }
    }
    catch (err) {
      throw new Error('Error executing SQL request: '+err.message);
    }
  }
  async  function  getmilk(TDATE,SHIFT) {
    try {
      let  pool = await  sql.connect(config);
      let  MILK = await  pool.request()
      .input('mode',sql.Int,5)//PARAMETERS
      .input('TDATE',sql.VarChar,TDATE)//PARAMETERS
      .input('SHIFT',sql.VarChar,SHIFT)//PARAMETERS
      .execute("SP_MILKDATA");//STORED PROCEDURE
      if(MILK.recordsets.length>0){
        return  MILK.recordsets[0];
      }
      else{
       return null;
      }
    }
    catch (err) {
      throw new Error('Error executing SQL request: '+err.message);
    }
  }
  async function addrate(MILKTYPE,STARTDATE,ENDDATE,FATMIN,FATMAX,SNFMIN,SNFMAX,RATE){
    try{
            let pool = await sql.connect(config);
            let insertuser = await pool.request()
            .input('mode',sql.Int,1) //PARAMETERS
            .input('milktype',sql.VarChar,MILKTYPE)
            .input('startdate',sql.VarChar,STARTDATE)
            .input('enddate',sql.VarChar,ENDDATE)
            .input('fatmin',sql.Decimal,FATMIN)
            .input('fatmax',sql.Decimal,FATMAX)
            .input('snfmin',sql.Decimal,SNFMIN)
            .input('snfmax',sql.Decimal,SNFMAX)
            .input('rate',sql.Decimal,RATE)
            .execute("SP_RATES");// STORED PROCEDURE
            return insertuser.recordsets;

    }
    catch (err) {
      throw new Error('Error executing SQL request: '+err.message);
  }
  }
  async function adduser(USERNAME,PASSWORD,MOBILENO,BRANCHNAME,ADDRESS,DEVICEID){
    try{
            let pool = await sql.connect(config);
            let insertuser = await pool.request()
            .input('mode',sql.Int,1) //PARAMETERS
            .input('USERNAME',sql.VarChar,USERNAME)
            .input('PASSWORD',sql.VarChar,PASSWORD)
            .input('MOBILENO',sql.VarChar,MOBILENO)
            .input('BRANCHNAME',sql.VarChar,BRANCHNAME)
            .input('ADDRESS',sql.VarChar,ADDRESS)
            .input('DEVICEID',sql.VarChar,DEVICEID)
            .execute("SP_USERS");// STORED PROCEDURE
            return insertuser.recordsets;

    }
    catch (err) {
      throw new Error('Error executing SQL request: '+err.message);
  }
  }

  module.exports = {
    addfarmer:addfarmer,
    getfarmers:getfarmers,
    getrates:getrates,
    addrate:addrate,
    getfarmerbyid:getfarmerbyid,
    gettsrate:gettsrate,
    addmilk:addmilk,
    getmilk:getmilk,
    adduser:adduser
  }