var Db = require('./dboperations');
var  config = require('./dbconfig');

var express = require('express');
var bodyParser = require('body-parser');
var cors = require('cors');
const { request, response } = require('express');
const jwt = require('jsonwebtoken');
const dboperations = require('./dboperations');
const xml = require('xml');
const  sql = require('mssql');
var app = express();
var router = express.Router();


app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(cors());
app.use('/api', router);




  router.route('/addfarmer').post(async(request, response) => {
    try {
        const { FARMERID,FARMERNAME,MOBILENO,MILKTYPE,ISACTIVE } = request.body;
        const  pooluser = await sql.connect(config);
        let  Farmer = await  pooluser.request()
        .input('mode',sql.Int,4)//PARAMETERS
        .input('ccode',sql.VarChar,user.toLowerCase())
        .input('password',sql.VarChar,getmd5(pwd))
        .execute("SP_APPUSERS");//STORED PROCEDURE
        await pooluser.close();
        if(Farmer.recordset.length > 0)
        {
          response.json({ 'code': response.statusCode, "description": response.get.status, "error": response.error, "Data": Farmer.recordset[0] });
        }
    }
    catch (err) {
        response.status(500).json({ error: err.message });
    }
})
router.route('/getfarmer').get(async(request, response) => {
    try {

        dboperations.getfarmers().then(data => {
            if (response != null) {
                response.json({ 'code': response.statusCode, "description": response.get.status, "error": response.error, "Data": data });
            }
            else {
                response.json({ "description": "error" });
            }
        }).catch((err) => {
            response.status(500).json({ error: err.message });
        })
    }
    catch (err) {
        response.status(500).json({ error: err.message });
    }
})
router.route('/getfarmerbyid').post(async(request, response) => {
    try {
        const { FARMERID } = request.body;
        dboperations.getfarmerbyid(FARMERID).then(data => {
            if (response != null) {
                response.json({ 'code': response.statusCode, "description": response.get.status, "error": response.error, "Data": data });
            }
            else {
                response.json({ "description": "error" });
            }
        }).catch((err) => {
            response.status(500).json({ error: err.message });
        })
    }
    catch (err) {
        response.status(500).json({ error: err.message });
    }
})
router.route('/gettsrate').post(async(request, response) => {
    try {
        const { MILKTYPE,TDATE,FAT,SNF } = request.body;
        dboperations.gettsrate(MILKTYPE,TDATE,FAT,SNF).then(data => {
            if (response != null) {
                response.json({ 'code': response.statusCode, "description": response.get.status, "error": response.error, "Data": data });
            }
            else {
                response.json({ "description": "error" });
            }
        }).catch((err) => {
            response.status(500).json({ error: err.message });
        })
    }
    catch (err) {
        response.status(500).json({ error: err.message });
    }
})
router.route('/addrate').post(async(request, response) => {
    try {
        const { MILKTYPE,STARTDATE,ENDDATE,FATMIN,FATMAX,SNFMIN,SNFMAX,RATE } = request.body;
        dboperations.addrate(MILKTYPE,STARTDATE,ENDDATE,FATMIN,FATMAX,SNFMIN,SNFMAX,RATE).then(data => {
            if (response != null) {
                response.json({ 'code': response.statusCode, "description": response.get.status, "error": response.error, "Data": "Success" });
            }
            else {
                response.json({ "description": "error" });
            }
        }).catch((err) => {
            response.status(500).json({ error: err.message });
        })
    }
    catch (err) {
        response.status(500).json({ error: err.message });
    }
})
router.route('/getrates').get(async(request, response) => {
    try {

        dboperations.getrates().then(data => {
            if (response != null) {
                response.json({ 'code': response.statusCode, "description": response.get.status, "error": response.error, "Data": data });
            }
            else {
                response.json({ "description": "error" });
            }
        }).catch((err) => {
            response.status(500).json({ error: err.message });
        })
    }
    catch (err) {
        response.status(500).json({ error: err.message });
    }
})
router.route('/addmilk').post(async(request, response) => {
    try {
        const { TDATE,SHIFT,FARMERID,FARMERNAME,MILKTYPE,QUANTITY,FAT,SNF,RATE,AMOUNT } = request.body;
        dboperations.addmilk(TDATE,SHIFT,FARMERID,FARMERNAME,MILKTYPE,QUANTITY,FAT,SNF,RATE,AMOUNT).then(data => {
            if (response != null) {
                response.json({ 'code': response.statusCode, "description": response.get.status, "error": response.error, "Data": data });
            }
            else {
                response.json({ "description": "error" });
            }
        }).catch((err) => {
            response.status(500).json({ error: err.message });
        })
    }
    catch (err) {
        response.status(500).json({ error: err.message });
    }
})
router.route('/getmilk').post(async(request, response) => {
    try {
        const { TDATE,SHIFT } = request.body;
        dboperations.getmilk(TDATE,SHIFT).then(data => {
            if (response != null) {
                response.json({ 'code': response.statusCode, "description": response.get.status, "error": response.error, "Data": data });
            }
            else {
                response.json({ "description": "error" });
            }
        }).catch((err) => {
            response.status(500).json({ error: err.message });
        })
    }
    catch (err) {
        response.status(500).json({ error: err.message });
    }
})



var port = process.env.PORT || 8080;
app.listen(port);
console.log('API  is running at ' + port);