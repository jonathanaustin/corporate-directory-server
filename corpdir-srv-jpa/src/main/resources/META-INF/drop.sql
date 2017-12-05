/** Foreign Keys. */
alter table Position drop constraint FK_Position_to_PositionType;
alter table OrgUnit drop constraint FK_OrgUnit_to_Type;
alter table Location drop constraint FK_Location_to_Location;
alter table Contact drop constraint FK_Contact_to_Location;
alter table Contact drop constraint FK_Contact_to_Image;
alter table Contact_Channel drop constraint FK_Contact_Channel_to_Channel;
alter table Contact_Channel drop constraint FK_Contact_Channel_to_Contact;

/** Business Keys*/
alter table PositionType drop constraint UK_PositionType_Business_Key;
alter table Position drop constraint UK_Position_Business_Key;
alter table OrgUnit drop constraint UK_OrgUnit_Business_Key;
alter table Location drop constraint UK_Location_Business_Key;
alter table Contact drop constraint UK_Contact_Business_Key;
alter table UnitType drop constraint UK_UnitType_Business_Key;
/** Unique channel id*/
alter table Contact_Channel drop constraint UK_Contact_Channel;

/** Versioned Data - Unique Keys */
alter table Contact_ContactLinks drop constraint UK_Contact_ContactLinks;
alter table OrgUnitLinks_Position drop constraint UK_OrgUnitLinks_Position;
alter table PositionLinks_OrgUnit drop constraint UK_PositionLinks_OrgUnit;

/** Versioned Data - Foreign Keys */
alter table Contact_ContactLinks drop constraint FK_Contact_ContactLinks_to_ContactLinks;
alter table Contact_ContactLinks drop constraint FK_Contact_ContactLinks_to_Contact;
alter table ContactLinks drop constraint FK_ContactLinks_to_Contact;
alter table ContactLinks drop constraint FK_ContactLinks_to_VersionCtrl;
alter table ContactLinks_Position drop constraint FK_ContactLinks_Position_to_Position;
alter table ContactLinks_Position drop constraint FK_ContactLinks_Position_to_ContactLinks;
alter table OrgUnit_OrgUnitLinks drop constraint FK_OrgUnit_OrgUnitLinks_to_OrgUnitLinks;
alter table OrgUnit_OrgUnitLinks drop constraint FK_OrgUnit_OrgUnitLinks_to_OrgUnit;
alter table OrgUnitLinks drop constraint FK_OrgUnitLinks_to_OrgUnit;
alter table OrgUnitLinks drop constraint FK_OrgUnitLinks_to_OrgUnit2;
alter table OrgUnitLinks drop constraint FK_OrgUnitLinks_to_Position;
alter table OrgUnitLinks drop constraint FK_OrgUnitLinks_to_VersionCtrl;
alter table OrgUnitLinks_OrgUnit drop constraint FK_OrgUnitLinks_OrgUnit_to_OrgUnit;
alter table OrgUnitLinks_OrgUnit drop constraint FK_OrgUnitLinks_OrgUnit_to_OrgUnitLinks;
alter table OrgUnitLinks_Position drop constraint FK_OrgUnitLinks_Position_to_Position;
alter table OrgUnitLinks_Position drop constraint FK_OrgUnitLinks_Position_to_OrgUnitLinks;
alter table Position_PositionLinks drop constraint FK_Position_PositionLinks_to_PositionLinks;
alter table Position_PositionLinks drop constraint FK_Position_PositionLinks_to_Position;
alter table PositionLinks drop constraint FK_PositionLinks_to_Position;
alter table PositionLinks drop constraint FK_PositionLinks_to_Position2;
alter table PositionLinks drop constraint FK_PositionLinks_to_OrgUnit;
alter table PositionLinks drop constraint FK_PositionLinks_to_VersionCtrl;
alter table PositionLinks_Contact drop constraint FK_PositionLinks_Contact_to_Contact;
alter table PositionLinks_Contact drop constraint FK_PositionLinks_Contact_to_PositionLinks;
alter table PositionLinks_OrgUnit drop constraint FK_PositionLinks_OrgUnit_to_OrgUnit;
alter table PositionLinks_OrgUnit drop constraint FK_PositionLinks_OrgUnit_to_PositionLinks;
alter table PositionLinks_Position drop constraint FK_PositionLinks_Position_to_Position;
alter table PositionLinks_Position drop constraint FK_PositionLinks_Position_to_PositionLinks;

/** System Control Constraints */
alter table SystemCtrl drop constraint FK_SystemCtrl_to_VersionCtrl;
/** Limit to one system control record */
alter table SystemCtrl drop constraint CHECK_SystemCtrl_ID;

drop table VersionCtrl;
drop table SystemCtrl;

drop table PositionType;
drop table Position;
drop table Position_PositionLinks;
drop table PositionLinks;
drop table PositionLinks_Contact;
drop table PositionLinks_OrgUnit;
drop table PositionLinks_Position;

drop table UnitType;
drop table OrgUnit;
drop table OrgUnit_OrgUnitLinks;
drop table OrgUnitLinks;
drop table OrgUnitLinks_OrgUnit;
drop table OrgUnitLinks_Position;

drop table Location;
drop table Image;
drop table Channel;
drop table Contact;
drop table Contact_Channel;
drop table Contact_ContactLinks;
drop table ContactLinks;
drop table ContactLinks_Position;
