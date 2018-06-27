DROP TABLE COMAR_PRODUCT
/*sql-break*/
DROP TABLE COMAR_METRIC
/*sql-break*/
DROP TABLE COMAR_PRODUCT_HISTORIAL
/*sql-break*/
CREATE TABLE COMAR_METRIC(
   ID SMALLINT PRIMARY KEY NOT NULL,
   NAME VARCHAR(32) NOT NULL,
   SYMBOL VARCHAR(6) NOT NULL
)
/*sql-break*/
CREATE TABLE COMAR_PRODUCT(
    ID CHAR(16) FOR BIT DATA NOT NULL,
    CODE VARCHAR(16) NOT NULL,
    DESCRIPTION VARCHAR(128) DEFAULT '',
    BUYPRICE BIGINT DEFAULT 0,
    TAX BIGINT DEFAULT 190,
    SELLPRICE BIGINT DEFAULT 0,
    STOCK BIGINT DEFAULT 0,
    ID_METRIC SMALLINT REFERENCES COMAR_METRIC(ID),
    PRIMARY KEY (ID),
    UNIQUE(CODE)
)
/*sql-break*/
CREATE TABLE COMAR_PRODUCT_HISTORIAL(
    ID CHAR(16) FOR BIT DATA NOT NULL,
    CODE VARCHAR(16) NOT NULL,
    STATUS SMALLINT DEFAULT 0,
    SERIALIZATION VARCHAR(256),
    PRIMARY KEY (ID)
)
/*sql-break*/
INSERT INTO COMAR_METRIC VALUES (0, 'Unidad', '[u]')
/*sql-break*/
INSERT INTO COMAR_METRIC VALUES (1, 'Kilo', '[Kg]')
/*sql-break*/
INSERT INTO COMAR_METRIC VALUES (2, 'Gramos', '[gr]')
/*sql-break*/
INSERT INTO COMAR_METRIC VALUES (3, 'Litros', '[lt]')