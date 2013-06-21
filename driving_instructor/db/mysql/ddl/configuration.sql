/*Table structure for table configuration */

CREATE TABLE configuration (
	id int(10) NOT NULL,
	property_name VARCHAR(100) NOT NULL,
	property_value VARCHAR(1000),
	PRIMARY KEY (id)
);