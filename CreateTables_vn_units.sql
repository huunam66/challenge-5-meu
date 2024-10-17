-- DROP TABLE IF EXISTS wards;
-- DROP TABLE IF EXISTS districts;
-- DROP TABLE IF EXISTS provinces;
-- DROP TABLE IF EXISTS administrative_units;
-- DROP TABLE IF EXISTS administrative_regions;

-- CREATE administrative_regions TABLE

use challenge3meu;

-- CREATE provinces TABLE
CREATE TABLE provinces (
	id varchar(20) NOT NULL,
	name varchar(255) NOT NULL,
	name_en varchar(255) NULL,
	full_name varchar(255) NOT NULL,
	full_name_en varchar(255) NULL,
	code_name varchar(255) NULL,
	CONSTRAINT provinces_pkey PRIMARY KEY (id)
);


-- CREATE districts TABLE
CREATE TABLE districts (
	id varchar(20) NOT NULL,
	name varchar(255) NOT NULL,
	name_en varchar(255) NULL,
	full_name varchar(255) NULL,
	full_name_en varchar(255) NULL,
	code_name varchar(255) NULL,
	province_id varchar(20) NULL,
	CONSTRAINT districts_pkey PRIMARY KEY (id)
);


-- districts foreign keys

ALTER TABLE districts ADD CONSTRAINT districts_province_code_fkey FOREIGN KEY (province_id) REFERENCES provinces(id);

CREATE INDEX idx_districts_province ON districts(province_id);


-- CREATE wards TABLE
CREATE TABLE wards (
	id varchar(20) NOT NULL,
	name varchar(255) NOT NULL,
	name_en varchar(255) NULL,
	full_name varchar(255) NULL,
	full_name_en varchar(255) NULL,
	code_name varchar(255) NULL,
	district_id varchar(20) NULL,
	CONSTRAINT wards_pkey PRIMARY KEY (id)
);


-- wards foreign keys

ALTER TABLE wards ADD CONSTRAINT wards_district_code_fkey FOREIGN KEY (district_id) REFERENCES districts(id);

CREATE INDEX idx_wards_district ON wards(district_id);
