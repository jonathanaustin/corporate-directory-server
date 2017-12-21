-- Matches the constraint name and replaces the text after with a ;
-- FIND: ([A-Z]K_[^\s]+?)\ .+$
-- REPLACE: $1;

-- System Control Constraints
alter table SystemCtrl drop constraint FK_SystemCtrl_to_VersionCtrl;
-- Limit to one system control record
alter table SystemCtrl drop constraint CHECK_SystemCtrl_ID;

-- Location
alter table Location drop constraint FK_Location_to_Location;
-- OrgUnit
alter table OrgUnit drop constraint FK_OrgUnit_to_UnitType;
alter table OrgUnitLinks  drop constraint FK_OrgUnitLinks_to_OrgUnit;
alter table OrgUnitLinks  drop constraint FK_OrgUnitLinks_to_VersionCtrl;
alter table OrgUnitLinks  drop constraint FK_OrgUnitLinks_to_OrgUnitLinks;
alter table OrgUnitLinks  drop constraint FK_OrgUnitLinks_to_PositionLinks;
-- Position
alter table Position drop constraint FK_Position_to_PositionType;
alter table PositionLinks drop constraint FK_PositionLinks_to_Position;
alter table PositionLinks drop constraint FK_PositionLinks_to_VersionCtrl;
alter table PositionLinks drop constraint FK_PositionLinks_to_PositionLinks;
alter table PositionLinks drop constraint FK_PositionLinks_to_OrgUnitlinks;
-- Contact
alter table Contact drop constraint FK_Contact_to_Location;
alter table Contact drop constraint FK_Contact_to_Image;
alter table Channel drop constraint FK_Channel_to_Contact;
alter table ContactLinks  drop constraint FK_ContactLinks_to_Contact;
alter table ContactLinks  drop constraint FK_ContactLinks_to_VersionCtrl;
alter table ContactLinks_PositionLinks drop constraint FK_PositionLinks_ContactLinks_to_ContactLinks;
alter table ContactLinks_PositionLinks drop constraint FK_PositionLinks_ContactLinks_to_PositionLinks;

/** Business Keys*/
alter table PositionType drop constraint UK_PositionType_Business_Key;
alter table Position drop constraint UK_Position_Business_Key;
alter table OrgUnit drop constraint UK_OrgUnit_Business_Key;
alter table Location drop constraint UK_Location_Business_Key;
alter table Contact drop constraint UK_Contact_Business_Key;
alter table UnitType drop constraint UK_UnitType_Business_Key;

drop table VersionCtrl;
drop table SystemCtrl;

drop table PositionType;
drop table UnitType;
drop table Location;

drop table OrgUnit;
drop table OrgUnitLinks;

drop table Position;
drop table PositionLinks;

drop table Image;
drop table Channel;
drop table Contact;
drop table ContactLinks;
drop table ContactLinks_PositionLinks;
