const  config = {
  user: 'sa',
password: 'password123$',
server: "192.168.1.70",
database: 'DB_2024',
connectionTimeout: 1050000,
driver:'tedious',
stream:false,
requestTimeout: 1050000,
  options: {
    encrypt: false,
    enableArithAbort: true,
    trustedconnection:  true,
    instancename:  'MSSQLSERVER'  // SQL Server instance name
  },
  port: 4605
}

module.exports = config;