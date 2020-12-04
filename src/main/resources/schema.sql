DROP TABLE IF EXISTS UserAddress;
DROP TABLE IF EXISTS UserRoleMapping;
DROP TABLE IF EXISTS UserRole;
DROP TABLE IF EXISTS UserDetail;


CREATE TABLE UserAddress (
    addressId INT AUTO_INCREMENT  PRIMARY KEY,
    street VARCHAR(100),
    city  VARCHAR(50),
    state VARCHAR(50),
    postcode VARCHAR(10)
);

CREATE TABLE UserDetail (
    userId INT AUTO_INCREMENT  PRIMARY KEY,
    title VARCHAR(3),
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    gender VARCHAR(10),
    bCryptEncodedPassword VARCHAR(100),
    addressId INT,
    constraint FK_Address_Id foreign key (addressId) references UserAddress(addressId)
);

CREATE TABLE UserRole (
    roleId INT AUTO_INCREMENT  PRIMARY KEY,
    roleName VARCHAR(30)
);

CREATE TABLE UserRoleMapping (
    userId INT,
    roleId INT,
    constraint FK_User_Id foreign key (userId) references UserDetail(userId),
    constraint FK_Role_Id foreign key (roleId) references UserRole(roleId)
);
