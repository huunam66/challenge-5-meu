
create database if not exists challenge3meu;
use challenge3meu;

create table user(
	email varchar(50) primary key not null unique,
    password varchar(500) not null
);

create table role(
	email varchar(50) not null primary key,
    role varchar(50) not null,
    constraint check_roles check (role = 'USER' or role = 'ADMIN' or role = 'SUPER_ADMIN'),
    constraint fk_email foreign key(email) references user(email) on delete cascade,
    constraint fk_unique_email_role unique(email, role)
);

create table profile(
	id varchar(30) not null,
    first_name varchar(100),
    last_name varchar(100),
    identification_code varchar(12),
    bidthday date,
    gender varchar(4),
    avatar text,
    email varchar(50) not null,
	constraint pr_profile primary key(id),
    constraint unique_email unique(email),
    constraint fk_email__profile foreign key(email) references user(email)
);

create table profile_location(
	id varchar(30) not null,
    home_number varchar(50),
    street varchar(50),
    profile_id varchar(30) not null,
    ward_id varchar(30) not null,
    district_id varchar(30) not null,
    province_id varchar(30) not null,
    country varchar(30) default 'Việt Nam',
    constraint pr_profile_location primary key(id),
    constraint fk_profile_id__profile_location foreign key(profile_id) references profile(id),
    constraint fk_ward_code__profile_location foreign key(ward_id) references wards(id),
    constraint fk_district_code__profile_address foreign key(district_id) references districts(id),
    constraint fk_province_code__profile_address foreign key(province_id) references provinces(id)
);

CREATE TABLE products (
  id bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT primary key,
  code VARCHAR(9) NOT NULL unique,
  name VARCHAR(90) NOT NULL,
  category VARCHAR(28) NOT NULL,
  brand VARCHAR(28) DEFAULT NULL,
  type VARCHAR(21) DEFAULT NULL,
  description VARCHAR(180) DEFAULT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

insert into user(email, password) values('superadmin123123@gmail.com', 'SuperAdmin123123');
insert into role(email, role) values('superadmin123123@gmail.com', 'SUPER_ADMIN');

INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `type`, `description`) VALUES (1, "P001", "MASK ADULT Surgical 3 ply 50'S MEDICOS with box", "Health Accessories", "Medicos", "Hygiene", "Colour: Blue (ear loop outside, ear loop inside- random assigned), Green, Purple, White, Lime Green, Yellow, Pink");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `description`) VALUES (2, "P002", "Party Cosplay Player Unknown Battlegrounds Clothes Hallowmas PUBG", "Men's Clothing", "No Brand", "Suitable for adults and children.");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `type`, `description`) VALUES (3, "P003", "Xiaomi REDMI 8A Official Global Version 5000 mAh battery champion 31 days 2GB+32GB", "Mobile & Gadgets", "Xiaomi", "Mobile Phones", "Xiaomi Redmi 8A");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `type`, `description`) VALUES (4, "P004", "Naelofar Sofis - Printed Square", "Hijab", "Naelofar", "Multi Colour Floral", "Ornate Iris flower composition with intricate growing foliage");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `type`, `description`) VALUES (5, "P005", "Philips HR2051 / HR2056 / HR2059 Ice Crushing Blender Jar Mill", "Small Kitchen Appliances", "Philips", "Mixers & Blenders", "Philips HR2051 Blender (350W, 1.25L Plastic Jar, 4 stars stainless steel blade)");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `type`, `description`) VALUES (6, "P006", "Gemei GM-6005 Rechargeable Trimmer Hair Cutter Machine", "Hair Styling Tools", "Gemei", "Trimmer", "The GEMEI hair clipper is intended for professional use.");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `type`, `description`) VALUES (7, "P007", "Oreo Crumb Small Crushed Cookie Pieces 454g", "Snacks", "Oreo", "Biscuits & Cookies", "Oreo Crumb Small Crushed Cookie Pieces 454g - Retail & Wholesale New Stock Long Expiry!!!");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `description`) VALUES (8, "P008", "Non-contact Infrared Forehead Thermometer ABS", "Kids Health & Skincare", "No Brand", "Non-contact Infrared Forehead Thermometer ABS for Adults and Children with LCD Display Digital");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `type`, `description`) VALUES (9, "P009", "Nordic Marble Starry Sky Bedding Sets", "Bedding", "No Brand", "Bedding Sheets", "Printing process: reactive printing. Package：quilt cover ,bed sheet ,pillow case. Not include comforter or quilt.");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `type`, `description`) VALUES (10, "P010", "Samsung Galaxy Tab A 10.1""", "Mobile & Gadgets", "Samsung", "Tablets", "4GB RAM. - 64GB ROM. - 1.5 ghz Processor. - 10.1 inches LCD Display");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `type`, `description`) VALUES (11, "P011", "REALME 5 PRO 6+128GB", "Mobile & Gadgets", "Realme", "Mobile Phones", "REALME 5 PRO 6+128GB");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `type`, `description`) VALUES (12, "P012", "Nokia 2.3 - Cyan Green", "Mobile & Gadgets", "Nokia", "Mobile Phones", "Nokia smartphones get 2 years of software upgrades and 3 years of monthly security updates.");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `type`, `description`) VALUES (13, "P013", "AKEMI Cotton Select Fitted Bedsheet Set - Adore 730TC", "Bedding", "Akemi", "Bedding Sheets", "100% Cotton Twill. Super Single.");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `type`, `description`) VALUES (14, "P014", "Samsung Note10+ Phone", "Mobile & Gadgets", "OEM", "Mobile Phones", "OEM Phone Models");
INSERT INTO `products` (`id`, `code`, `name`, `category`, `brand`, `type`, `description`) VALUES (15, "P015", "Keknis Basic Wide Long Shawl", "Hijab", "No Brand", "Plain Shawl", "1.8m X 0.7m (+/-). Heavy chiffon (120 gsm).");
COMMIT;

