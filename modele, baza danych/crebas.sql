/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     02.08.2022 17:27:37                          */
/*==============================================================*/


drop table if exists address;

drop table if exists cart_offer;

drop table if exists category;

drop table if exists category_parameter;

drop table if exists category_parameter_type;

drop table if exists category_parameter_value;

drop table if exists country;

drop table if exists offer;

drop table if exists offer_details;

drop table if exists offer_parameter_value;

drop table if exists offer_photo;

drop table if exists offer_purchase_info;

drop table if exists offer_status;

drop table if exists offer_type;

drop table if exists purchase_status;

drop table if exists role;

drop table if exists user;

drop table if exists user_auction;

drop table if exists user_favorite_offer;

drop table if exists user_status;

drop table if exists users_roles;

/*==============================================================*/
/* Table: address                                               */
/*==============================================================*/
create table address
(
   ID                   bigint not null,
   ID_USER              bigint,
   ID_COUNTRY           int,
   FIRSTNAME            varchar(50),
   LASTNAME             varchar(50),
   STREET_NAME          varchar(100),
   POSTAL_CODE          varchar(10),
   PHONE                varchar(20),
   CITY                 varchar(50),
   primary key (ID)
);

/*==============================================================*/
/* Table: cart_offer                                            */
/*==============================================================*/
create table cart_offer
(
   ID                   bigint not null,
   ID_USER              bigint not null,
   ID_OFFER             bigint not null,
   QUANTITY             int,
   primary key (ID)
);

/*==============================================================*/
/* Table: category                                              */
/*==============================================================*/
create table category
(
   ID                   int not null,
   ID_PARENT            int,
   NAME                 varchar(50),
   primary key (ID)
);

/*==============================================================*/
/* Table: category_parameter                                    */
/*==============================================================*/
create table category_parameter
(
   ID                   int not null,
   ID_CATEGORY          int not null,
   ID_CATEGORY_PARAMETER_TYPE int not null,
   NAME                 varchar(50) not null,
   VALUE_MIN            int,
   VALUE_MAX            int,
   primary key (ID)
);

alter table category_parameter comment 'Parametry kategorii używane do opisu elementu z danej katego';

/*==============================================================*/
/* Table: category_parameter_type                               */
/*==============================================================*/
create table category_parameter_type
(
   ID                   int not null,
   NAME                 varchar(50),
   primary key (ID)
);

/*==============================================================*/
/* Table: category_parameter_value                              */
/*==============================================================*/
create table category_parameter_value
(
   ID                   int not null,
   ID_CATEGORY_PARAMETER int,
   VALUE                varchar(100),
   primary key (ID)
);

/*==============================================================*/
/* Table: country                                               */
/*==============================================================*/
create table country
(
   ID                   int not null,
   NAME                 varchar(50) not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: offer                                                 */
/*==============================================================*/
create table offer
(
   ID                   bigint not null,
   ID_OFFER_TYPE        int not null,
   ID_OFFER_STATUS      int not null,
   ID_USER              bigint not null,
   ID_CATEGORY          int not null,
   PRICE                decimal(12,2),
   VIEWS                bigint,
   QUANTITY             int,
   primary key (ID)
);

/*==============================================================*/
/* Table: offer_details                                         */
/*==============================================================*/
create table offer_details
(
   ID                   bigint not null,
   ID_OFFER             bigint not null,
   TITLE                varchar(80) not null,
   DESCRIPTION          varchar(5000),
   primary key (ID)
);

/*==============================================================*/
/* Table: offer_parameter_value                                 */
/*==============================================================*/
create table offer_parameter_value
(
   ID                   bigint not null,
   ID_OFFER             bigint,
   ID_CATEGORY_PARAMETER int not null,
   ID_CATEGORY_PARAMETER_VALUE int,
   MIN_MAX_VALUE        int,
   primary key (ID)
);

/*==============================================================*/
/* Table: offer_photo                                           */
/*==============================================================*/
create table offer_photo
(
   ID                   bigint not null,
   ID_OFFER             bigint,
   PATH                 varchar(200),
   SEQUENCE             int,
   primary key (ID)
);

/*==============================================================*/
/* Table: offer_purchase_info                                   */
/*==============================================================*/
create table offer_purchase_info
(
   ID                   bigint not null,
   ID_OFFER             bigint not null,
   ID_BUYER             bigint not null,
   ID_SELLER            bigint not null,
   ID_ADDRESS           bigint,
   ID_PURCHASE_STATUS   int,
   PURCHASE_TIME        datetime,
   PRICE                decimal(12,2),
   primary key (ID)
);

alter table offer_purchase_info comment 'Informacje o kupnie.
Tabela przechowuje redundantne da';

/*==============================================================*/
/* Table: offer_status                                          */
/*==============================================================*/
create table offer_status
(
   ID                   int not null,
   NAME                 varchar(50) not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: offer_type                                            */
/*==============================================================*/
create table offer_type
(
   ID                   int not null,
   NAME                 varchar(50),
   primary key (ID)
);

alter table offer_type comment 'Przechowywanei informacji o typie oferty - czy:
- licy';

/*==============================================================*/
/* Table: purchase_status                                       */
/*==============================================================*/
create table purchase_status
(
   ID                   int not null,
   NAME                 varchar(50),
   primary key (ID)
);

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   ID                   int not null,
   NAME                 varchar(50) not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   ID                   bigint not null,
   ID_USER_STATUS       int,
   LOGIN                varchar(50),
   PASSWORD             varchar(80),
   FIRSTNAME            varchar(50),
   LASTNAME             varchar(50),
   EMAIL                varchar(50),
   AVATAR_PATH          varchar(50),
   AVERAGE_RATE         int,
   primary key (ID)
);

alter table user comment 'AVERAGE_RATE - aktualizować po każdym dodaniu oferty, gdzie ';

/*==============================================================*/
/* Table: user_auction                                          */
/*==============================================================*/
create table user_auction
(
   ID                   bigint not null,
   ID_USER              bigint not null,
   ID_OFFER             bigint not null,
   VALUE                decimal(12,2) not null,
   INSERT_TIME          datetime,
   primary key (ID)
);

alter table user_auction comment 'Przechowuje informacje o cenie zaoferowanej przez użytkownik';

/*==============================================================*/
/* Table: user_favorite_offer                                   */
/*==============================================================*/
create table user_favorite_offer
(
   ID                   bigint not null,
   ID_USER              bigint,
   ID_OFFER             bigint,
   INSERT_TIME          datetime,
   primary key (ID)
);

alter table user_favorite_offer comment 'INSERT_TIME - kiedy dodano do ulubonych';

/*==============================================================*/
/* Table: user_status                                           */
/*==============================================================*/
create table user_status
(
   ID                   int not null,
   NAME                 varchar(50),
   primary key (ID)
);

/*==============================================================*/
/* Table: users_roles                                           */
/*==============================================================*/
create table users_roles
(
   ID_USER              bigint not null,
   ID_ROLE              int not null,
   primary key (ID_USER, ID_ROLE)
);

alter table address add constraint FK_address_country foreign key (ID_COUNTRY)
      references country (ID) on delete restrict on update restrict;

alter table address add constraint FK_user_address foreign key (ID_USER)
      references user (ID) on delete restrict on update restrict;

alter table cart_offer add constraint FK_cart_user foreign key (ID_USER)
      references user (ID) on delete restrict on update restrict;

alter table cart_offer add constraint FK_offer_cart foreign key (ID_OFFER)
      references offer (ID) on delete restrict on update restrict;

alter table category add constraint FK_category_to_category foreign key (ID_PARENT)
      references category (ID) on delete restrict on update restrict;

alter table category_parameter add constraint FK_category_parameter_category_parameter_type foreign key (ID_CATEGORY_PARAMETER_TYPE)
      references category_parameter_type (ID) on delete restrict on update restrict;

alter table category_parameter_value add constraint FK_category_parameter_category_parameter_value foreign key (ID_CATEGORY_PARAMETER)
      references category_parameter (ID) on delete restrict on update restrict;

alter table offer add constraint FK_Reference_31 foreign key (ID_CATEGORY)
      references category (ID) on delete restrict on update restrict;

alter table offer add constraint FK_Reference_32 foreign key (ID_OFFER_STATUS)
      references offer_status (ID) on delete restrict on update restrict;

alter table offer add constraint FK_offer_offer_type foreign key (ID_OFFER_TYPE)
      references offer_type (ID) on delete restrict on update restrict;

alter table offer add constraint FK_user_offer foreign key (ID_USER)
      references user (ID) on delete restrict on update restrict;

alter table offer_details add constraint FK_offer_details_offer foreign key (ID_OFFER)
      references offer (ID) on delete restrict on update restrict;

alter table offer_parameter_value add constraint FK_category_parameter_value_offer_parameter_value foreign key (ID_CATEGORY_PARAMETER_VALUE)
      references category_parameter_value (ID) on delete restrict on update restrict;

alter table offer_parameter_value add constraint FK_offer_offer_parameter_value foreign key (ID_OFFER)
      references offer (ID) on delete restrict on update restrict;

alter table offer_parameter_value add constraint FK_offer_parameter_value_category_parameter foreign key (ID_CATEGORY_PARAMETER)
      references category_parameter (ID) on delete restrict on update restrict;

alter table offer_photo add constraint FK_offer_photo_offer foreign key (ID_OFFER)
      references offer (ID) on delete restrict on update restrict;

alter table offer_purchase_info add constraint FK_Reference_30 foreign key (ID_BUYER)
      references user (ID) on delete restrict on update restrict;

alter table offer_purchase_info add constraint FK_Reference_33 foreign key (ID_SELLER)
      references user (ID) on delete restrict on update restrict;

alter table offer_purchase_info add constraint FK_Reference_34 foreign key (ID_ADDRESS)
      references address (ID) on delete restrict on update restrict;

alter table offer_purchase_info add constraint FK_offer_purchase_info_offer foreign key (ID_OFFER)
      references offer (ID) on delete restrict on update restrict;

alter table offer_purchase_info add constraint FK_purchase_status_offer_purchase_info foreign key (ID_PURCHASE_STATUS)
      references purchase_status (ID) on delete restrict on update restrict;

alter table user add constraint FK_user_status_user foreign key (ID_USER_STATUS)
      references user_status (ID) on delete restrict on update restrict;

alter table user_auction add constraint FK_user_auction_offer foreign key (ID_OFFER)
      references offer (ID) on delete restrict on update restrict;

alter table user_auction add constraint FK_user_auction_user foreign key (ID_USER)
      references user (ID) on delete restrict on update restrict;

alter table user_favorite_offer add constraint FK_user_favorite_offer_offer foreign key (ID_OFFER)
      references offer (ID) on delete restrict on update restrict;

alter table user_favorite_offer add constraint FK_user_favorite_offer_user foreign key (ID_USER)
      references user (ID) on delete restrict on update restrict;

alter table users_roles add constraint FK_users_role_role foreign key (ID_ROLE)
      references role (ID) on delete restrict on update restrict;

alter table users_roles add constraint FK_users_role_user foreign key (ID_USER)
      references user (ID) on delete restrict on update restrict;

