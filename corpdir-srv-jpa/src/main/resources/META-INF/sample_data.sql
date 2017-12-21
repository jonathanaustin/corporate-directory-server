
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
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('DV-1', 'Division 1', true, true,(select id from UnitType where businessKey='DIV'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('BR-1-1', 'Branch 1-1', true, true,(select id from UnitType where businessKey='BR'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('SEC-1-1-1', 'Section 1-1-1', true, true,(select id from UnitType where businessKey='SEC'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('UNIT-1-1-1-1', 'Unit 1-1-1-1', true, true,(select id from UnitType where businessKey='UNIT'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('DV-2', 'Division 2', true, true,(select id from UnitType where businessKey='DIV'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('BR-2-1', 'Branch 2-1', true, true,(select id from UnitType where businessKey='BR'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('SEC-2-1-1', 'Section 2-1-1', true, true,(select id from UnitType where businessKey='SEC'));
insert into OrgUnit(businessKey, description, custom, active, type_id) values ('UNIT-2-1-1-1', 'Unit 2-1-1-1', true, true,(select id from UnitType where businessKey='UNIT'));

/** Org Unit Version Links */
insert into OrgUnitLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from OrgUnit where businessKey ='D'), (select id from VersionCtrl where description ='VR'));
insert into OrgUnitLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from OrgUnit where businessKey ='DV-1'), (select id from VersionCtrl where description ='VR'));
insert into OrgUnitLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from OrgUnit where businessKey ='BR-1-1'), (select id from VersionCtrl where description ='VR'));
insert into OrgUnitLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from OrgUnit where businessKey ='SEC-1-1-1'), (select id from VersionCtrl where description ='VR'));
insert into OrgUnitLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from OrgUnit where businessKey ='UNIT-1-1-1-1'), (select id from VersionCtrl where description ='VR'));
insert into OrgUnitLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from OrgUnit where businessKey ='DV-2'), (select id from VersionCtrl where description ='VR'));
insert into OrgUnitLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from OrgUnit where businessKey ='BR-2-1'), (select id from VersionCtrl where description ='VR'));
insert into OrgUnitLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from OrgUnit where businessKey ='SEC-2-1-1'), (select id from VersionCtrl where description ='VR'));
insert into OrgUnitLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from OrgUnit where businessKey ='UNIT-2-1-1-1'), (select id from VersionCtrl where description ='VR'));

/** Org Unit Tree */
update OrgUnitLinks set parent_versionCtrl_id = versionCtrl_id, parent_item_id = (select id from OrgUnit where businessKey ='D') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='DV-1');
update OrgUnitLinks set parent_versionCtrl_id = versionCtrl_id, parent_item_id = (select id from OrgUnit where businessKey ='DV-1') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='BR-1-1');
update OrgUnitLinks set parent_versionCtrl_id = versionCtrl_id, parent_item_id = (select id from OrgUnit where businessKey ='BR-1-1') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='SEC-1-1-1');
update OrgUnitLinks set parent_versionCtrl_id = versionCtrl_id, parent_item_id = (select id from OrgUnit where businessKey ='SEC-1-1-1') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='UNIT-1-1-1-1');
update OrgUnitLinks set parent_versionCtrl_id = versionCtrl_id, parent_item_id = (select id from OrgUnit where businessKey ='D') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='DV-2');
update OrgUnitLinks set parent_versionCtrl_id = versionCtrl_id, parent_item_id = (select id from OrgUnit where businessKey ='DV-2') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='BR-2-1');
update OrgUnitLinks set parent_versionCtrl_id = versionCtrl_id, parent_item_id = (select id from OrgUnit where businessKey ='BR-2-1') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='SEC-2-1-1');
update OrgUnitLinks set parent_versionCtrl_id = versionCtrl_id, parent_item_id = (select id from OrgUnit where businessKey ='SEC-2-1-1') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='UNIT-2-1-1-1');

/** Positions */
insert into Position(businessKey, description, custom, active, type_id) values ('P1-1', 'Department Secretary', true, true,(select id from PositionType where businessKey='DS'));
insert into Position(businessKey, description, custom, active, type_id) values ('P1-2', 'DIV 1 - Division Head', true, true,(select id from PositionType where businessKey='SEC'));
insert into Position(businessKey, description, custom, active, type_id) values ('P1-3', 'DIV 1 - Branch Head', true, true,(select id from PositionType where businessKey='FAS'));
insert into Position(businessKey, description, custom, active, type_id) values ('P1-4', 'DIV 1 - Section Head', true, true,(select id from PositionType where businessKey='AS'));
insert into Position(businessKey, description, custom, active, type_id) values ('P1-5', 'DIV 1 - Unit Head', true, true,(select id from PositionType where businessKey='EL2'));
insert into Position(businessKey, description, custom, active, type_id) values ('P2-2', 'DIV 2 - Division Head', true, true,(select id from PositionType where businessKey='SEC'));
insert into Position(businessKey, description, custom, active, type_id) values ('P2-3', 'DIV 2 - Branch Head', true, true,(select id from PositionType where businessKey='FAS'));
insert into Position(businessKey, description, custom, active, type_id) values ('P2-4', 'DIV 2 - Section Head', true, true,(select id from PositionType where businessKey='AS'));
insert into Position(businessKey, description, custom, active, type_id) values ('P2-5', 'DIV 2 - Unit Head', true, true,(select id from PositionType where businessKey='EL2'));

/** Position Version Links */
insert into PositionLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Position where businessKey ='P1-1'), (select id from VersionCtrl where description ='VR'));
insert into PositionLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Position where businessKey ='P1-2'), (select id from VersionCtrl where description ='VR'));
insert into PositionLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Position where businessKey ='P1-3'), (select id from VersionCtrl where description ='VR'));
insert into PositionLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Position where businessKey ='P1-4'), (select id from VersionCtrl where description ='VR'));
insert into PositionLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Position where businessKey ='P1-5'), (select id from VersionCtrl where description ='VR'));
insert into PositionLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Position where businessKey ='P2-2'), (select id from VersionCtrl where description ='VR'));
insert into PositionLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Position where businessKey ='P2-3'), (select id from VersionCtrl where description ='VR'));
insert into PositionLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Position where businessKey ='P2-4'), (select id from VersionCtrl where description ='VR'));
insert into PositionLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Position where businessKey ='P2-5'), (select id from VersionCtrl where description ='VR'));

/** LATER - Create a Position Tree -- Like EAs */

/** Assign Positions to the OrgUnits */
update OrgUnitLinks set managerPosition_versionCtrl_id = versionCtrl_id, managerPosition_item_id = (select id from Position where businessKey ='P1-1') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='D');
update OrgUnitLinks set managerPosition_versionCtrl_id = versionCtrl_id, managerPosition_item_id = (select id from Position where businessKey ='P1-2') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='DV-1');
update OrgUnitLinks set managerPosition_versionCtrl_id = versionCtrl_id, managerPosition_item_id = (select id from Position where businessKey ='P1-3') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='BR-1-1');
update OrgUnitLinks set managerPosition_versionCtrl_id = versionCtrl_id, managerPosition_item_id = (select id from Position where businessKey ='P1-4') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='SEC-1-1-1');
update OrgUnitLinks set managerPosition_versionCtrl_id = versionCtrl_id, managerPosition_item_id = (select id from Position where businessKey ='P1-5') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='UNIT-1-1-1');
update OrgUnitLinks set managerPosition_versionCtrl_id = versionCtrl_id, managerPosition_item_id = (select id from Position where businessKey ='P2-2') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='DV-2');
update OrgUnitLinks set managerPosition_versionCtrl_id = versionCtrl_id, managerPosition_item_id = (select id from Position where businessKey ='P2-3') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='BR-2-1');
update OrgUnitLinks set managerPosition_versionCtrl_id = versionCtrl_id, managerPosition_item_id = (select id from Position where businessKey ='P2-4') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='SEC-2-1-1');
update OrgUnitLinks set managerPosition_versionCtrl_id = versionCtrl_id, managerPosition_item_id = (select id from Position where businessKey ='P2-5') where versionCtrl_id = (select id from VersionCtrl where description ='VR') and item_id = (select id from OrgUnit where businessKey ='UNIT-2-1-1');

/** Contacts */
insert into Contact(businessKey, firstname, lastname, description, custom, active) values ('U1', 'Mary', 'Citizen', 'Desc', true, true);
insert into Contact(businessKey, firstname, lastname, description, custom, active) values ('U2', 'Jon', 'Citizen', 'Desc', true, true);
insert into Contact(businessKey, firstname, lastname, description, custom, active) values ('U3', 'Tom', 'Citizen', 'Desc', true, true);
insert into Contact(businessKey, firstname, lastname, description, custom, active) values ('U4', 'Debbie', 'Citizen', 'Desc', true, true);
insert into Contact(businessKey, firstname, lastname, description, custom, active) values ('U5', 'Fred', 'Citizen', 'Desc', true, true);
insert into Contact(businessKey, firstname, lastname, description, custom, active) values ('U6', 'Samantha', 'Citizen', 'Desc', true, true);

/** Create Contact Version Link*/
insert into ContactLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Contact where businessKey ='U1'), (select id from VersionCtrl where description ='VR'));
insert into ContactLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Contact where businessKey ='U2'), (select id from VersionCtrl where description ='VR'));
insert into ContactLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Contact where businessKey ='U3'), (select id from VersionCtrl where description ='VR'));
insert into ContactLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Contact where businessKey ='U4'), (select id from VersionCtrl where description ='VR'));
insert into ContactLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Contact where businessKey ='U5'), (select id from VersionCtrl where description ='VR'));
insert into ContactLinks(description, item_id, versionCtrl_id) values ('VERS', (select id from Contact where businessKey ='U6'), (select id from VersionCtrl where description ='VR'));

/** Channel */
insert into Channel(type, channelValue, active, custom,contact_id) values ('MOBILE', '1111111', true, true, (select id from Contact where businessKey ='U1'));
insert into Channel(type, channelValue, active, custom,contact_id) values ('MOBILE', '2222222', true, true, (select id from Contact where businessKey ='U2'));
insert into Channel(type, channelValue, active, custom,contact_id) values ('MOBILE', '3333333', true, true, (select id from Contact where businessKey ='U3'));
insert into Channel(type, channelValue, active, custom,contact_id) values ('MOBILE', '4444444', true, true, (select id from Contact where businessKey ='U4'));
insert into Channel(type, channelValue, active, custom,contact_id) values ('MOBILE', '5555555', true, true, (select id from Contact where businessKey ='U5'));
insert into Channel(type, channelValue, active, custom,contact_id) values ('MOBILE', '6666666', true, true, (select id from Contact where businessKey ='U6'));

/** Assign Contacts to a Position */
insert into ContactLinks_PositionLinks(contactVersions_versionCtrl_id, contactVersions_item_id, positionVersions_versionCtrl_id, positionVersions_item_id ) values ((select id from VersionCtrl where description ='VR'), (select id from Contact where businessKey ='U1'), (select id from VersionCtrl where description ='VR'),  (select id from Position where businessKey ='P1-1'));
insert into ContactLinks_PositionLinks(contactVersions_versionCtrl_id, contactVersions_item_id, positionVersions_versionCtrl_id, positionVersions_item_id ) values ((select id from VersionCtrl where description ='VR'), (select id from Contact where businessKey ='U2'), (select id from VersionCtrl where description ='VR'),  (select id from Position where businessKey ='P1-2'));
insert into ContactLinks_PositionLinks(contactVersions_versionCtrl_id, contactVersions_item_id, positionVersions_versionCtrl_id, positionVersions_item_id ) values ((select id from VersionCtrl where description ='VR'), (select id from Contact where businessKey ='U3'), (select id from VersionCtrl where description ='VR'),  (select id from Position where businessKey ='P1-3'));
insert into ContactLinks_PositionLinks(contactVersions_versionCtrl_id, contactVersions_item_id, positionVersions_versionCtrl_id, positionVersions_item_id) values ((select id from VersionCtrl where description ='VR'), (select id from Contact where businessKey ='U4'), (select id from VersionCtrl where description ='VR'),  (select id from Position where businessKey ='P1-4'));
insert into ContactLinks_PositionLinks(contactVersions_versionCtrl_id, contactVersions_item_id, positionVersions_versionCtrl_id, positionVersions_item_id ) values ((select id from VersionCtrl where description ='VR'), (select id from Contact where businessKey ='U5'), (select id from VersionCtrl where description ='VR'),  (select id from Position where businessKey ='P1-5'));
