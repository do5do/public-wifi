drop table BookmarkGroup;
drop table Wifi;
drop table Bookmark;
drop table LocHistory;

CREATE TABLE Wifi (
	wno	INTEGER		NOT NULL PRIMARY KEY autoincrement,
	mgmtNum	TEXT		NOT NULL,
	borough	TEXT		NOT NULL,
	wifiName	TEXT		NOT NULL,
	address	TEXT		NOT NULL,
	addrDetail	TEXT		NOT NULL,
	installLoc	TEXT		NOT NULL,
	installType	TEXT		NOT NULL,
	installAgency	TEXT		NOT NULL,
	serviceType	TEXT		NOT NULL,
	netType	TEXT		NOT NULL,
	installYear	TEXT		NOT NULL,
	inoutDoor	TEXT		NOT NULL,
	wifiConnEnv	TEXT		NOT NULL,
	x	REAL		NOT NULL,
	y	REAL		NOT NULL,
	workDate	TEXT		NOT NULL,
	distance	REAL		NOT NULL
);

CREATE TABLE Bookmark (
	bno	INTEGER		NOT NULL PRIMARY KEY autoincrement,
	bgno	INTEGER		NOT NULL,
	wno	INTEGER		NOT NULL,
	bookmarkName	TEXT		NOT NULL,
	wifiName	TEXT		NOT NULL,
	createdDate	DATE		NOT NULL,
	FOREIGN KEY (bgno) REFERENCES BookmarkGroup (bgno),
	FOREIGN KEY (wno) REFERENCES Wifi (wno)
);

CREATE TABLE BookmarkGroup (
	bgno	INTEGER		NOT NULL PRIMARY KEY autoincrement,
	name	TEXT		NOT NULL,
	seq	Integer		NOT NULL,
	createdDate	DATE		NOT NULL,
	modifiedDate	DATE		NULL
);

CREATE TABLE LocHistory (
	hno	INTEGER		NOT NULL PRIMARY KEY autoincrement,
	lnt	REAL		NOT NULL,
	lat	REAL		NOT NULL,
	searchedDate	DATE		NOT NULL
);