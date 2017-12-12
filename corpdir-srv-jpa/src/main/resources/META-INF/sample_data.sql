/**
delete from VersionCtrl;
delete from SystemCtrl;
delete from Location;
delete from PositionType;
*/

/** Version Ctrl */
insert into VersionCtrl(description) values ('VR');

/** System Ctrl */
insert into SystemCtrl(description, versionCtrl_id) values ('System', (select id from VersionCtrl where description ='VR'));

/** Locations */
insert into Location(businessKey, description, custom, active) values ('ACT', 'Canberra', true, true);
insert into Location(businessKey, description, custom, active,parentId) values ('BEL', 'Belconnen', true, true, (select id from Location where businessKey='ACT'));
insert into Location(businessKey, description, custom, active,parentId) values ('GUN', 'Gungahlin', true, true, (select id from Location where businessKey='ACT'));
insert into Location(businessKey, description, custom, active,parentId) values ('CIV', 'Civic', true, true, (select id from Location where businessKey='ACT'));
insert into Location(businessKey, description, custom, active) values ('NSW', 'New South Wales', true, true);
insert into Location(businessKey, description, custom, active,parentId) values ('SYD', 'Sydney', true, true, (select id from Location where businessKey='NSW'));
insert into Location(businessKey, description, custom, active,parentId) values ('PAR', 'Parramatta', true, true, (select id from Location where businessKey='NSW'));
insert into Location(businessKey, description, custom, active) values ('QLD', 'Queensland', true, true);
insert into Location(businessKey, description, custom, active,parentId) values ('BRIS', 'Brisbane', true, true, (select id from Location where businessKey='QLD'));

/** PositionTypes */
insert into PositionType(businessKey, description, custom, active,typeLevel) values ('SEC', 'Secretary', true, true, 1);
insert into PositionType(businessKey, description, custom, active,typeLevel) values ('DS', 'Department Secretary', true, true, 2);
insert into PositionType(businessKey, description, custom, active,typeLevel) values ('FAS', 'First Assistant Secretary', true, true, 3);
insert into PositionType(businessKey, description, custom, active,typeLevel) values ('AS', 'Assistant Secretary', true, true, 4);
insert into PositionType(businessKey, description, custom, active,typeLevel) values ('EL2', 'Director', true, true, 5);
insert into PositionType(businessKey, description, custom, active,typeLevel) values ('EA', 'Executive Assistant', true, true, 6);

/** Unit Types */
insert into UnitType(businessKey, description, custom, active) values ('DEPT', 'Department', true, true);
insert into UnitType(businessKey, description, custom, active) values ('DIV', 'Division', true, true);
insert into UnitType(businessKey, description, custom, active) values ('BR', 'Branch', true, true);
insert into UnitType(businessKey, description, custom, active) values ('SEC', 'Section', true, true);
insert into UnitType(businessKey, description, custom, active) values ('UNIT', 'Unit', true, true);

/** Org units */
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('D', 'Department', true, true,(select id from UnitType where businessKey='DEPT'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('DV1', 'Division 1', true, true,(select id from UnitType where businessKey='DIV'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('BR1-1', 'Branch 1-1', true, true,(select id from UnitType where businessKey='BR'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('SEC1-1-1', 'Section 1-1-1', true, true,(select id from UnitType where businessKey='SEC'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('UNIT1-1-1-1', 'Unit 1-1-1-1', true, true,(select id from UnitType where businessKey='UNIT'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('DV2', 'Division 2', true, true,(select id from UnitType where businessKey='DIV'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('BR2-1', 'Branch 2-1', true, true,(select id from UnitType where businessKey='BR'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('SEC2-1-1', 'Section 2-1-1', true, true,(select id from UnitType where businessKey='SEC'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('UNIT2-1-1-1', 'Unit 2-1-1-1', true, true,(select id from UnitType where businessKey='UNIT'));

