/*User Address table insert scripts*/
INSERT INTO  UserAddress(addressId,street,city,state,postCode) VALUES (1001,'11 Pyrmont Street','Sydney','NSW','2000');
INSERT INTO  UserAddress(addressId,street,city,state,postCode) VALUES (1002,'22 Flinders Street','Melbourne','VIC','3000');
INSERT INTO  UserAddress(addressId,street,city,state,postCode) VALUES (1003,'33 Parrot Street','Sydney','NSW','2000');
INSERT INTO  UserAddress(addressId,street,city,state,postCode) VALUES (1004,'44 Archer Street','Sydney','NSW','2000');
INSERT INTO  UserAddress(addressId,street,city,state,postCode) VALUES (1005,'55 Delhi Street','Sydney','NSW','2000');
INSERT INTO  UserAddress(addressId,street,city,state,postCode) VALUES (1006,'66 Clower Street','Sydney','NSW','2000');
INSERT INTO  UserAddress(addressId,street,city,state,postCode) VALUES (1007,'77 Floral Street','Melbourne','VIC','2000');

/*User Detail table insert scripts*/
INSERT INTO  UserDetail(userId,title,firstName,lastName,gender,bCryptEncodedPassword,addressId) VALUES (1000001,'Mr','Alex','Smith','male','$2y$12$1nhG4Jk6QcWTdpIL8ht8A.LcXqu1KD.hqEB.SUu.SuYTRVF0VbgQm',1001);
INSERT INTO  UserDetail(userId,title,firstName,lastName,gender,bCryptEncodedPassword,addressId) VALUES (1000002,'Mr','David','Jackson','male','$2y$12$1nhG4Jk6QcWTdpIL8ht8A.LcXqu1KD.hqEB.SUu.SuYTRVF0VbgQm',1002);
INSERT INTO  UserDetail(userId,title,firstName,lastName,gender,bCryptEncodedPassword,addressId) VALUES (1000003,'Mr','Mark','Palmer','male','$2y$12$1nhG4Jk6QcWTdpIL8ht8A.LcXqu1KD.hqEB.SUu.SuYTRVF0VbgQm',1003);
INSERT INTO  UserDetail(userId,title,firstName,lastName,gender,bCryptEncodedPassword,addressId) VALUES (1000004,'Ms','Ann','Dodson','female','$2y$12$1nhG4Jk6QcWTdpIL8ht8A.LcXqu1KD.hqEB.SUu.SuYTRVF0VbgQm',1004);
INSERT INTO  UserDetail(userId,title,firstName,lastName,gender,bCryptEncodedPassword,addressId) VALUES (1000005,'Mr','Mike','Woolmer','male','$2y$12$1nhG4Jk6QcWTdpIL8ht8A.LcXqu1KD.hqEB.SUu.SuYTRVF0VbgQm',1005);
INSERT INTO  UserDetail(userId,title,firstName,lastName,gender,bCryptEncodedPassword,addressId) VALUES (1000006,'Mr','John','Dixon','male','$2y$12$1nhG4Jk6QcWTdpIL8ht8A.LcXqu1KD.hqEB.SUu.SuYTRVF0VbgQm',1006);
INSERT INTO  UserDetail(userId,title,firstName,lastName,gender,bCryptEncodedPassword,addressId) VALUES (1000007,'Ms','Rita','Lemaire','female','$2y$12$1nhG4Jk6QcWTdpIL8ht8A.LcXqu1KD.hqEB.SUu.SuYTRVF0VbgQm',1007);
/*User Role table insert scripts*/
INSERT INTO UserRole (roleId,roleName) VALUES (1001,'READ_ONLY_USER');
INSERT INTO UserRole (roleId,roleName) VALUES (1002,'READ_UPDATE_USER');
INSERT INTO UserRole (roleId,roleName) VALUES (1003,'ADMIN');
/*UserId and RoleId mapping table insert scripts*/
INSERT INTO UserRoleMapping (userId,roleId) VALUES (1000001,1003); /* 1000001 is ADMIN User*/
INSERT INTO UserRoleMapping (userId,roleId) VALUES (1000002,1002); /*1000002  READ_UPDATE_USER*/
INSERT INTO UserRoleMapping (userId,roleId) VALUES (1000003,1002); /* 1000003 READ_UPDATE USER*/
INSERT INTO UserRoleMapping (userId,roleId) VALUES (1000004,1001); /*1000004 READ_ONLY USER*/
INSERT INTO UserRoleMapping (userId,roleId) VALUES (1000005,1003); /* 1000005 is ADMIN USER*/