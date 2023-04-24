CREATE DATABASE DOZLAP_ESHOPPING

--1 discount, product_category, shipment_method, province, credit_card_type, admin | 6 tables
SET DATEFORMAT DMY
CREATE TABLE admin(
	id BIGINT,
	username VARCHAR(1000) UNIQUE,
	password VARCHAR(1000) UNIQUE,
	CONSTRAINT pk_admin PRIMARY KEY (id)
)
INSERT INTO admin VALUES(1, 'DOZLAP', 'BESTSERVICE')

-- 1 discount để demo
CREATE TABLE discount(
	id BIGINT,
	name NVARCHAR(1000),
	discount_percentage INT,
	-- !!! WRITING TRIGGERS start_date_utc < end_date_utc
	start_date_utc DATETIME,
	end_date_utc DATETIME
	CONSTRAINT pk_discount PRIMARY KEY (id)
)
INSERT INTO discount VALUES(1, 'Laptop Discount', 15, '24-04-2023', '26-04-2023')
INSERT INTO discount VALUES(2, 'PC Discount', 10, '15-06-2023', '22-07-2023')
INSERT INTO discount VALUES(3, 'Screen Discount', 20, '01-06-2023', '20-06-2023')
INSERT INTO discount VALUES(4, 'Keyboard Discount', 33, '30-04-2023', '01-05-2023')
INSERT INTO discount VALUES(5, 'Mouse Discount', 20, '22-06-2023', '01-07-2023')
INSERT INTO discount VALUES(6, 'Earphones & Speaker Discount', 50, '16-05-2023', '01-07-2023')
INSERT INTO discount VALUES(7, 'PC components', 25, '10-05-2023', '05-07-2023')
INSERT INTO discount VALUES(8, 'Apple', 10, '02-05-2023', '30-06-2023')
/*
- Laptop
- PC
- Màn hình
- Bàn phím
- Chuột
- Tai nghe - Loa
- Linh kiện PC
- Apple
*/

CREATE TABLE product_category(
	id BIGINT,
	name NVARCHAR(1000) UNIQUE,
	description NVARCHAR(1000)
	CONSTRAINT pk_category PRIMARY KEY (id)
)
INSERT INTO product_category VALUES(1, 'Laptop', 'A small, portable personal computer (PC)')
INSERT INTO product_category VALUES(2, 'PC', 'Personal Computer')
INSERT INTO product_category VALUES(3, 'Screen', 'An output device that displays information in pictorial or textual form')
INSERT INTO product_category VALUES(4, 'Keyboard', 'A peripheral input device modeled after the typewriter keyboard which uses an arrangement of buttons or keys to act as mechanical levers or electronic switches.')
INSERT INTO product_category VALUES(5, 'Mouse', 'A hand-held pointing device that detects two-dimensional motion relative to a surface.')
INSERT INTO product_category VALUES(6, 'Earphone & Speaker', 'Common output devices for generating sounds to the user.')
INSERT INTO product_category VALUES(7, 'PC components', 'Electrical components of a PC which can be replaced.')
INSERT INTO product_category VALUES(8, 'Apple', 'A brand which specializes in making digital devices.')
/*
- Thường
- Phát nhanh
- Phát nhanh trong ngày
*/ 
CREATE TABLE shipment_method(
	id BIGINT,
	name NVARCHAR(1000) UNIQUE,
	description NVARCHAR(1000),
	ship_charge MONEY
	CONSTRAINT pk_shipment_method PRIMARY KEY (id)
)
INSERT INTO shipment_method VALUES(1, 'Normal', 'Standard delivery', 15000)
INSERT INTO shipment_method VALUES(2, 'Express', 'The item will be shipped in 24-72 hours', 30000)
INSERT INTO shipment_method VALUES(3, 'In-day Express', 'The item will be shipped within the day purchased', 70000)
/*
- COD (Thanh toán khi nhận hàng)
- Thanh toán = thẻ tín dụng/ghi nợ
*/
CREATE TABLE payment_method(
	id BIGINT,
	name NVARCHAR(1000) UNIQUE,
	CONSTRAINT pk_payment_method PRIMARY KEY(id)
)
INSERT INTO payment_method VALUES(1, 'COD (Cash on Delivery)')
INSERT INTO payment_method VALUES(2, 'By credit / debit card')

-- 63 tỉnh thành: 1 - 63
CREATE TABLE province(
	id BIGINT,
	name NVARCHAR(1000) UNIQUE,
	shipping_charge MONEY, 
	CONSTRAINT pk_province PRIMARY KEY (id)
)

-- ATM / Debit / Credit 
CREATE TABLE credit_card_type(
	id BIGINT,
	name NVARCHAR(1000) UNIQUE,
	charge MONEY,
	CONSTRAINT pk_card_type PRIMARY KEY (id)
)
INSERT INTO credit_card_type VALUES(1, 'ATM', 0)
INSERT INTO credit_card_type VALUES(2, 'Debit Card', 0)
INSERT INTO credit_card_type VALUES(3, 'Credit Card', 10000)
--2 discount_applied_category, product, address, credit_card | 4 tables

CREATE TABLE discount_applied_category(
	discount_id BIGINT,
	category_id BIGINT,
	CONSTRAINT fk_discount_applied FOREIGN KEY (discount_id) REFERENCES discount(id),
	CONSTRAINT fk_category_applied FOREIGN KEY (category_id) REFERENCES product_category(id),
	CONSTRAINT pk_discount_applied_category PRIMARY KEY (discount_id, category_id)
)
INSERT INTO discount_applied_category VALUES(1, 1)
INSERT INTO discount_applied_category VALUES(2, 2)
INSERT INTO discount_applied_category VALUES(3, 3)
INSERT INTO discount_applied_category VALUES(4, 4)
INSERT INTO discount_applied_category VALUES(5, 5)
INSERT INTO discount_applied_category VALUES(6, 6)
INSERT INTO discount_applied_category VALUES(7, 7)
INSERT INTO discount_applied_category VALUES(8, 8)

-- bn sản phẩm / 1 category ??
CREATE TABLE product(
	id BIGINT,
	name NVARCHAR(1000) UNIQUE,
	image_url VARCHAR(1000), 
	description NVARCHAR(1000),
	price MONEY,
	-- AVAILABLE / OUT OF ORDER
	status BIT,
	category_id BIGINT,
	CONSTRAINT pk_product PRIMARY KEY (id),
	CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES product_category(id)
)
/*
- Laptop
- PC
- Màn hình
- Bàn phím
- Chuột
- Tai nghe - Loa
- Linh kiện PC
- Apple
*/

-- Laptop
INSERT INTO product VALUES(1, 
'Laptop MSI Modern 14 B11MOU-1065VN', 
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/t/e/text_ng_n_8__1_34.png',
'Laptop học tập - văn phòng cực mỏng nhẹ với trọng lượng chỉ 1.3 kg.
CPU Intel Core i7-1195G7 cân mọi tác vụ văn phòng như: Word, Excel, PowerPoint.
Card đồ họa Intel Iris Xe Graphics thoải mái chạy các ứng dụng văn phòng cơ bản hay các phần mềm thiết kế đơn giản.
RAM 8 GB chuẩn DDR4 giúp thao tác đa nhiệm mượt mà hơn.
Khởi động máy, ứng dụng nhanh chóng chỉ trong vài giây nhờ ổ cứng SSD 512 GB PCIe NVMe.
Độ phủ màu 45% NTSC, 63% sRGB hỗ trợ thiết kế, làm việc với màu sắc chuẩn xác', 
16100000, 1, 1)

INSERT INTO product VALUES(2, 
'Laptop Asus Gaming Rog Strix G15 G513IH HN015W', 
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/6/_/6_22_24.jpg',
'Chip R7-4800H cùng card đồ họa rời Geforce GTX 1650 cho khả năng chiến các tựa game nặng, chỉnh sửa hình ảnh trên PTS, Render video ngắn mượt mà.
Ram 8GB + 1 khe, nâng cấp tối đa 32GB cùng ổ cứng SSD có không gian lưu trữ lên tới 512GB cho tốc độ xử lý mượt mà, nhanh chóng, không lo giật lag.
Màn hình 15.6 inches trên tấm nền IPS với độ phân giải Full HD cùng tần số 144Hz cho trải nghiệm hình ảnh, màu sắc chân thực, sinh động.
Đa dạng cổng giao tiếp, dễ dàng sử dụng.
Trọng lượng 2.1 kg thuận tiện cho di chuyển và mang theo.Máy đi kèm Windows 11 SL bản quyền', 
19000000, 1, 1)

INSERT INTO product VALUES(3, 'Laptop Acer Aspire 3 A315-56-38B1 NX.HS5SV.00G',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/c/d/cd554_5_.jpg',
'Card đồ hoạ Intel UHD Graphics - Hiệu năng đáp ứng tốt nhu cầu chỉnh sửa ảnh cơ bản hay.
Dung lượng ổ cứng lớn 256GB.
SSD giúp khởi động, vào ứng dụng hay việc sao chép dữ liệu cực nhanh.
Màn hình Full HD sống động và sắc nét, đem lại hình ảnh và màu sắc tuyệt vời.
Thiết kế tương đối gọn nhẹ - Nặng 1.7kg đồng hành cùng bạn đi học, đi làm hay đến bất kỳ đâu một cách dễ dàng.
Dung lượng pin ổn 2 cell 37Wh, giúp cho người dùng có thể sử dụng thoải mái làm việc hay giải trí',
8700000, 1, 1)

INSERT INTO product VALUES(4, 'Laptop MSI Gaming GF63 Thin 11UD 473VN',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/9/_/9_9_21.jpg',
'Chip i5-11400H cùng card đồ họa rời RTX 3050 Ti cho khả năng chiến các tựa game nặng, chỉnh sửa hình ảnh trên PTS, Render video ngắn mượt mà.
Ram 8GB với 1 khe cắm có thể nâng cấp tối đa lên 64GB cùng ổ cứng SSD 512GB mang đến tốc độ xử lý nhanh cùng đa nhiệm mượt mà.
Màn hình LCD 15.6 inches với dải màu 72% NTSC cho trải nghiệm màu sắc chân thực.
Tích hợp web cam HD 720p cho phép đàm thoại thông qua video thoải mái.
Trọng lượng 1.86 kg thuận tiện di chuyển, mang theo.
Máy đi kèm Windows 11 Home bản quyền',
19400000, 1, 1)

INSERT INTO product VALUES(5, 'Laptop Lenovo Ideapad 5 Pro 14ITL6 82L300MAVN',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/t/e/text_ng_n_1__6.png',
'Sở hữu phong cách thiết kế tao nhã, thời thượng với lớp vỏ kim loại bền chắc.
Màn hình 14 inch đi kèm độ phân giải 2.2K mãn nhãn thị giác người xem với chất lượng hình ảnh tuyệt hảo, sắc nét đến từng chi tiết.
Bộ vi xử lý Intel Core i7-1195G7 giải quyết nhẹ nhàng các nhu cầu học tập và giải trí.
RAM 16 GB chuẩn DDR4 nâng cao hiệu suất với các thao tác chuyển động giữa các ứng dụng trở nên nhanh gọn hơn.
Khối lượng chỉ vỏn vẹn 1.38 kg luôn sẵn sàng đồng hành cùng người dùng trên mọi chuyến công tác xa',
22300000, 1, 1)

INSERT INTO product VALUES(6, 'Laptop Lenovo Ideapad 3 15IAU7 82RK001GVN',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/x/n/xnah_nh_.png',
'Thiết kế tông Xanh lịch thiệp, màn hình 15.6 inch quan sát bắt mắt.
Hiệu năng chuyên đa ứng dụng với Intel Gen 12 và 512 GB lưu trữ.
Laptop Lenovo IdeaPad 3 15IAU7 82RK001GVN sử dụng hệ điều hành Windows 11, dung lượng pin 45W cho bạn thời lượng pin đủ để không bị gián đoạn khi đang sử dụng làm việc hay giải trí.
Laptop Lenovo IdeaPad 3 15IAU7 82RK001GVN còn được trang bị hệ thống âm thanh Dolby cho ra chất lượng âm thanh tuyệt vời và chân thật',
13600000, 1, 1)

INSERT INTO product VALUES(7, 'Laptop Acer Aspire 3 A315-58-53S6 NX.AM0SV.005',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/t/e/text_ng_n_9__1.png',
'Thoải mái lưu trữ - 256GB SSD + 1 khe HDD trống cho phép nâng cấp.
4GB onboard + 4GB trên khe cho khả năng nâng cấp tối đa 12GB.
Thoả sức giải trí với các bộ phim hay edit hình ảnh - Màn hình Full HD, kích thước 15.6 inch.
Chơi game nhẹ nhàng với card đồ hoạ Intel® Iris® Xe Graphics',
13000000, 1, 1)

INSERT INTO product VALUES(8, 'Laptop HP Pavilion 15-EG2037TX 6K783PA',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/_/0/_0005_screenshaot_1_.jpg',
'I5 1235U - Xử lý tốt các tác vụ văn phòng.
8GB Ram cho khả năng xử lý đa nhiệm tốt, có thể tháo ra nâng cấp.
Card đồ hoạ MX550 - Chỉnh sửa ảnh trên PTS hay giải trí nhẹ nhàng với các tựa game.
Màn hình lớn 15.6 inch thoả sức làm việc hay giải tr&iacute.
SSD 256GB cho khả năng lưu trữ đủ dùng, khởi động ứng dụng nhanh hơn',
16300000 , 1, 1)

INSERT INTO product VALUES(9, 'Laptop HP 245 G9 6L1N8PA',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/t/e/text_d_i_20.png',
'Phù hợp sinh viên - nhân viên văn phòng với thiết kế mỏng nhẹ, màn hình 14 inch Full HD.
Cân mọi tác vụ học tập, văn phòng với CPU AMD Ryzen 5-5625U.
RAM 8 GB đa nhiệm hỗ trợ mở hàng chục tab duyệt web mà không lo lag, giật.
Ổ cứng 256GB SSD thoải mái lưu trữ mọi file, dữ liệu học tập.
Camera HD hỗ trợ hình ảnh sắc nét trong các buổi họp, call video, meet, vv',
13000000, 1, 1)

INSERT INTO product VALUES(10, 'Laptop Lenovo Ideapad Gaming 3 15IHU6 82K101HGVN',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/t/e/text_ng_n_2__2_8.png',
'Bộ vi xử lý Intel Core i5-11320 giải quyết tất tần tật mọi tác vụ Word, Excel, PowerPoint,... đến thiết kế đồ họa hay chiến game một cách mượt mà.
Card đồ họa RTX 3050 4GB GDDR6 giúp các tác vụ thiết kế Adobe Illustrator hay Premiere nhanh gọn.
RAM 8 GB cho phép mở nhiều cửa sổ ứng dụng khác nhau rất mượt mà.
Mở máy nhanh chóng cũng như không gian lưu trữ thoải mái hơn nhờ ổ cứng 512 GB SSD.
Đèn nền Led RGB hỗ trợ làm việc trong môi trường thiếu sáng hay mang lại cảm giác phấn khích khi chiến game.
Màn hình 15.6 inch cùng tấm nền IPS cho góc nhìn lên đến 178 độ, giúp theo dõi đối thủ trên bản đồ một cách chính xác',
19600000 , 1, 1)
-- PC

-- Màn hình

-- Bàn phím

-- Chuột

-- Tai nghe - Loa

-- Linh kiện PC

-- Apple

-- 4 - 5 địa chỉ để demo
CREATE TABLE address(
	id BIGINT,
	house_number VARCHAR(1000), 
	street NVARCHAR(1000), 
	province_id BIGINT,
	CONSTRAINT pk_address PRIMARY KEY (id),
	CONSTRAINT fk_address_province FOREIGN KEY (province_id) REFERENCES province(id)
)
-- 4 - 5 cái thẻ :>
-- !!!!!!! LEAVE AT HERE
CREATE TABLE credit_card(
	id BIGINT,
	card_type_id BIGINT,
	username VARCHAR(1000) UNIQUE, 
	-- BETTER HAVE CONSTRAINT CSV-NUMBER ONLY 3 OR 4
	csv_number VARCHAR(4), 
	balance MONEY, 
	expire_on_utc DATETIME, 
	-- CONSTRAINT ONLY NUMBER AND FIXED LENGTH IF NECESSARY
	account_number VARCHAR(1000) UNIQUE,
	CONSTRAINT pk_card PRIMARY KEY (id)
)

--3 customer | 1 tables
-- 4 - 5 khách hàng
CREATE TABLE customer(
	id BIGINT,
	username VARCHAR(1000),
	password VARCHAR(1000),
	email VARCHAR(1000),
	first_name NVARCHAR(1000),
	last_name NVARCHAR(1000), 
	-- CONSTRAINT ONLY MALE/FEMALE (AT_BIRTH)
	gender VARCHAR(6), 
	phone_number CHAR(10), 
	date_of_birth DATETIME, 
	card_id BIGINT,
	CONSTRAINT pk_customer PRIMARY KEY (id),
	CONSTRAINT fk_customer_card FOREIGN KEY (card_id) REFERENCES credit_card(id)
)

--4 shopping_cart_item, product_review, order, | 4 tables

CREATE TABLE shopping_cart_item(
	id BIGINT,
	customer_id BIGINT,
	product_id BIGINT,
	quantity BIGINT, 
	created_on_utc DATETIME,
	CONSTRAINT pk_cart_item PRIMARY KEY (id),
	CONSTRAINT fk_cart_customer FOREIGN KEY (customer_id) REFERENCES customer(id),
	CONSTRAINT fk_cart_product FOREIGN KEY (product_id) REFERENCES product(id)
)
-- optional
CREATE TABLE product_review(
	product_id BIGINT, 
	customer_id BIGINT, 
	-- ONLY 1 TO 5 STARS
	rate SMALLINT, 
	comment NVARCHAR(1000), 
	report NVARCHAR(1000)
	CONSTRAINT fk_review_product FOREIGN KEY (product_id) REFERENCES product(id),
	CONSTRAINT fk_review_customer FOREIGN KEY (customer_id) REFERENCES customer(id),
	CONSTRAINT pk_review PRIMARY KEY (product_id, customer_id)
)

-- USING orders since order cause an error in sql syntax
CREATE TABLE orders(
	id BIGINT, 
	note NVARCHAR(1000),
	created_on_utc DATETIME,
	customer_id BIGINT, 
	shipping_address_id BIGINT, 
	shipping_method_id BIGINT,
	payment_method_id BIGINT,
	total_price MONEY,
	-- PENDING (AWAITING CONFIRMATION), CONFIRMED, SHIPPING, COMPLETED, DECLINED
	status VARCHAR(10),
	is_paid BIT,
	CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customer(id),
	CONSTRAINT fk_order_shipping_addresses FOREIGN KEY (shipping_address_id) REFERENCES address(id),
	CONSTRAINT fk_order_shipping_method FOREIGN KEY (shipping_method_id) REFERENCES shipment_method(id),
	CONSTRAINT fk_order_payment_method FOREIGN KEY (payment_method_id) REFERENCES payment_method(id),
	CONSTRAINT pk_order PRIMARY KEY (id)
)

--5 order_item | 1 tables
CREATE TABLE order_item(
	id BIGINT, 
	order_id BIGINT,
	product_id BIGINT,
	quantity BIGINT,
	CONSTRAINT fk_item_order FOREIGN KEY (order_id) REFERENCES orders(id),
	CONSTRAINT fk_item_product FOREIGN KEY (product_id) REFERENCES product(id),
	CONSTRAINT pk_item PRIMARY KEY (id)
)
