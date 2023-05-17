

GO
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
INSERT INTO discount VALUES(1, N'Giảm giá Laptop', 15, '24-04-2023', '26-04-2023')
INSERT INTO discount VALUES(2, N'Giảm giá PC', 10, '15-06-2023', '22-07-2023')
INSERT INTO discount VALUES(3, N'Giảm giá màn hình', 20, '01-06-2023', '20-06-2023')
INSERT INTO discount VALUES(4, N'Giảm giá bàn phím', 33, '30-04-2023', '01-05-2023')
INSERT INTO discount VALUES(5, N'Giảm giá chuột', 20, '22-06-2023', '01-07-2023')
INSERT INTO discount VALUES(6, N'Giảm giá loa & tai nghe', 50, '16-05-2023', '01-07-2023')
INSERT INTO discount VALUES(7, N'Giảm giá linh kiện PC', 25, '10-05-2023', '05-07-2023')
INSERT INTO discount VALUES(8, N'Giảm giá Apple', 10, '02-05-2023', '30-06-2023')
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
INSERT INTO shipment_method VALUES(1, N'Thường', N'Chuyển hàng thông thường', 15000)
INSERT INTO shipment_method VALUES(2, N'Hỏa tốc', N'Hàng sẽ giao trong khoảng 24-72h', 30000)
INSERT INTO shipment_method VALUES(3, N'Hỏa tốc trong ngày', N'Hàng sẽ giao ngay trong ngày đặt hàng', 70000)
/*
- COD (Thanh toán khi nhận hàng)
- Thanh toán = thẻ tín dụng/ghi nợ
*/
CREATE TABLE payment_method(
	id BIGINT,
	name NVARCHAR(1000) UNIQUE,
	CONSTRAINT pk_payment_method PRIMARY KEY(id)
)
INSERT INTO payment_method VALUES(1, N'Giao tiền khi nhận hàng (COD)')
INSERT INTO payment_method VALUES(2, N'Thanh toán bằng chuyển khoản (thẻ tín dụng / ghi nợ)')

-- 63 tỉnh thành: 1 - 63
CREATE TABLE province(
	id BIGINT,
	name NVARCHAR(1000) UNIQUE,
	shipping_charge MONEY, 
	CONSTRAINT pk_province PRIMARY KEY (id)
)
INSERT INTO province VALUES(1,N'An Giang',18000)
INSERT INTO province VALUES(2,N'Bà Rịa-Vũng Tàu',88000)
INSERT INTO province VALUES(3,N'Bạc Liêu',33000)
INSERT INTO province VALUES(4,N'Bắc Giang',18000)
INSERT INTO province VALUES(5,N'Bắc Kạn',61000)
INSERT INTO province VALUES(6,N'Bắc Ninh',24000)
INSERT INTO province VALUES(7,N'Bến Tre',21000)
INSERT INTO province VALUES(8,N'Bình Dương',15000)
INSERT INTO province VALUES(9,N'Bình Định',13000)
INSERT INTO province VALUES(10,N'Bình Phước',2000)
INSERT INTO province VALUES(11,N'Bình Thuận',43000)
INSERT INTO province VALUES(12,N'Cà Mau',1000)
INSERT INTO province VALUES(13,N'Cao Bằng',61000)
INSERT INTO province VALUES(14,N'Cần Thơ',32000)
INSERT INTO province VALUES(15,N'Đà Nẵng',70000)
INSERT INTO province VALUES(16,N'Đắk Lắk',92000)
INSERT INTO province VALUES(17,N'Đắk Nông',25000)
INSERT INTO province VALUES(18,N'Điện Biên',65000)
INSERT INTO province VALUES(19,N'Đồng Nai',68000)
INSERT INTO province VALUES(20,N'Đồng Tháp',32000)
INSERT INTO province VALUES(21,N'Gia Lai',90000)
INSERT INTO province VALUES(22,'NHà Giang',94000)
INSERT INTO province VALUES(23,N'Hà Nam',35000)
INSERT INTO province VALUES(24,N'Hà Nội',50000)
INSERT INTO province VALUES(25,N'Hà Tĩnh',12000)
INSERT INTO province VALUES(26,N'Hải Dương',72000)
INSERT INTO province VALUES(27,N'Hải Phòng',95000)
INSERT INTO province VALUES(28,N'Hậu Giang',96000)
INSERT INTO province VALUES(29,N'Hòa Bình',18000)
INSERT INTO province VALUES(30,N'Hưng Yên',24000)
INSERT INTO province VALUES(31,N'Khánh Hòa',75000)
INSERT INTO province VALUES(32,N'Kiên Giang',86000)
INSERT INTO province VALUES(33,N'Kon Tum',70000)
INSERT INTO province VALUES(34,N'Lai Châu',71000)
INSERT INTO province VALUES(35,N'Lạng Sơn',73000)
INSERT INTO province VALUES(36,N'Lào Cai',78000)
INSERT INTO province VALUES(37,N'Lâm Đồng',73000)
INSERT INTO province VALUES(38,N'Long An',32000)
INSERT INTO province VALUES(39,N'Nam Định',77000)
INSERT INTO province VALUES(40,N'Nghệ An',21000)
INSERT INTO province VALUES(41,N'Ninh Bình',32000)
INSERT INTO province VALUES(42,N'Ninh Thuận',59000)
INSERT INTO province VALUES(43,N'Phú Thọ',66000)
INSERT INTO province VALUES(44,N'Phú Yên',4000)
INSERT INTO province VALUES(45,N'Quảng Bình',62000)
INSERT INTO province VALUES(46,N'Quảng Nam',33000)
INSERT INTO province VALUES(47,N'Quảng Ngãi',68000)
INSERT INTO province VALUES(48,N'Quảng Ninh',97000)
INSERT INTO province VALUES(49,N'Quảng Trị',61000)
INSERT INTO province VALUES(50,N'Sóc Trăng',36000)
INSERT INTO province VALUES(51,N'Sơn La',72000)
INSERT INTO province VALUES(52,N'Tây Ninh',8000)
INSERT INTO province VALUES(53,N'Thái Bình',9000)
INSERT INTO province VALUES(54,N'Thái Nguyên',13000)
INSERT INTO province VALUES(55,N'Thanh Hóa',35000)
INSERT INTO province VALUES(56,N'Thừa Thiên Huế',16000)
INSERT INTO province VALUES(57,N'Tiền Giang',53000)
INSERT INTO province VALUES(58,N'TP Hồ Chí Minh',30000)
INSERT INTO province VALUES(59,N'Trà Vinh',13000)
INSERT INTO province VALUES(60,N'Tuyên Quang',7000)
INSERT INTO province VALUES(61,N'Vĩnh Long',96000)
INSERT INTO province VALUES(62,N'Vĩnh Phúc',72000)
INSERT INTO province VALUES(63,N'Yên Bái',12000)
-- ATM / Debit / Credit 
CREATE TABLE credit_card_type(
	id BIGINT,
	name NVARCHAR(1000) UNIQUE,
	charge MONEY,
	CONSTRAINT pk_card_type PRIMARY KEY (id)
)
INSERT INTO credit_card_type VALUES(1, N'ATM', 0)
INSERT INTO credit_card_type VALUES(2, N'Thẻ ghi nợ (Debit)', 0)
INSERT INTO credit_card_type VALUES(3, N'Thẻ tín dụng (Credit)', 10000)
--2 discount_applied_category, product, address, credit_card | 4 tables

CREATE TABLE discount_applied_category(
	id BIGINT,
	discount_id BIGINT,
	category_id BIGINT,
	CONSTRAINT fk_discount_applied FOREIGN KEY (discount_id) REFERENCES discount(id),
	CONSTRAINT fk_category_applied FOREIGN KEY (category_id) REFERENCES product_category(id),
	CONSTRAINT pk_discount_applied_category PRIMARY KEY (id)
)
INSERT INTO discount_applied_category VALUES(1, 1, 1)
INSERT INTO discount_applied_category VALUES(2, 2, 2)
INSERT INTO discount_applied_category VALUES(3, 3, 3)
INSERT INTO discount_applied_category VALUES(4, 4, 4)
INSERT INTO discount_applied_category VALUES(5, 5, 5)
INSERT INTO discount_applied_category VALUES(6, 6, 6)
INSERT INTO discount_applied_category VALUES(7, 7, 7)
INSERT INTO discount_applied_category VALUES(8, 8, 8)

-- bn sản phẩm / 1 category ??
CREATE TABLE product(
	id BIGINT,
	name NVARCHAR(1000) UNIQUE,
	image_url VARCHAR(1000), 
	description NVARCHAR(1000),
	specs NVARCHAR(4000),
	-- CONSIDERING ADD SPECIFICATIONS
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
N'Laptop MSI Modern 14 B11MOU-1065VN', 
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/t/e/text_ng_n_8__1_34.png',

N'Laptop học tập - văn phòng cực mỏng nhẹ với trọng lượng chỉ 1.3 kg|
CPU Intel Core i7-1195G7 cân mọi tác vụ văn phòng như: Word, Excel, PowerPoint|
Card đồ họa Intel Iris Xe Graphics thoải mái chạy các ứng dụng văn phòng cơ bản hay các phần mềm thiết kế đơn giản|
RAM 8 GB chuẩn DDR4 giúp thao tác đa nhiệm mượt mà hơn|
Khởi động máy, ứng dụng nhanh chóng chỉ trong vài giây nhờ ổ cứng SSD 512 GB PCIe NVMe|
Độ phủ màu 45% NTSC, 63% sRGB hỗ trợ thiết kế, làm việc với màu sắc chuẩn xác',

N'Loại card đồ họa    Intel Iris Xe Graphics|
Dung lượng RAM    8GB|
Loại RAM    DDR4 3200MHz|
Số khe ram    2 khe (Nâng cấp tối đa 64GB)|
Ổ cứng    512GB PCIe NVMe M.2 SSD (1 slot)|
Kích thước màn hình    14 inches|
Công nghệ màn hình    Tần số quét 60Hz  Độ phủ màu 45% NTSC, 63% sRGB  Thin Bezel|
Pin    3 Cell, 39Whr|
Hệ điều hành    Windows 11 Home|
Độ phân giải màn hình    1920 x 1080 pixels (FullHD)|
Cổng giao tiếp    1x Type-C USB3.2 Gen 2  2x Type-A USB3.2 Gen 1 1x Micro SD 1x HDMI (4K @ 60Hz)|
',
16100000, 1, 1)

INSERT INTO product VALUES(2, 
N'Laptop Asus Gaming Rog Strix G15 G513IH HN015W', 
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/6/_/6_22_24.jpg',

N'Chip R7-4800H cùng card đồ họa rời Geforce GTX 1650 cho khả năng chiến các tựa game nặng, chỉnh sửa hình ảnh trên PTS, Render video ngắn mượt mà|
Ram 8GB + 1 khe, nâng cấp tối đa 32GB cùng ổ cứng SSD có không gian lưu trữ lên tới 512GB cho tốc độ xử lý mượt mà, nhanh chóng, không lo giật lag|
Màn hình 15.6 inches trên tấm nền IPS với độ phân giải Full HD cùng tần số 144Hz cho trải nghiệm hình ảnh, màu sắc chân thực, sinh động|
Đa dạng cổng giao tiếp, dễ dàng sử dụng|
Trọng lượng 2.1 kg thuận tiện cho di chuyển và mang theo.Máy đi kèm Windows 11 SL bản quyền', 

N'Loại card đồ họa    Geforce GTX 1650 4GB|
Dung lượng RAM    8GB|
Loại RAM    DDR4 8GB (1 x 8GB) 3200MHz, up to 32GB|
Số khe ram    2 khe|
Ổ cứng    512GB SSD NVMe M.2 PCIe Gen 3 x 4|
Kích thước màn hình    15.6 inches|
Công nghệ màn hình    IPS Panel, 144Hz|
Hệ điều hành    Windows 11 SL 64 Bit|
Độ phân giải màn hình    1920 x 1080 pixels (FullHD)|
Cổng giao tiếp    1x Type C USB 3.2 Gen 1 (Power Delivery/Display Port)  3x USB 3.2 Gen 1 Type-A  1x 3.5mm Combo Audio Jack  1x HDMI 2.0b|
',19000000, 1, 1)

INSERT INTO product VALUES(3, N'Laptop Acer Aspire 3 A315-56-38B1 NX.HS5SV.00G',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/c/d/cd554_5_.jpg',

N'Card đồ hoạ Intel UHD Graphics - Hiệu năng đáp ứng tốt nhu cầu chỉnh sửa ảnh cơ bản hay|
Dung lượng ổ cứng lớn 256GB|
SSD giúp khởi động, vào ứng dụng hay việc sao chép dữ liệu cực nhanh|
Màn hình Full HD sống động và sắc nét, đem lại hình ảnh và màu sắc tuyệt vời|
Thiết kế tương đối gọn nhẹ - Nặng 1.7kg đồng hành cùng bạn đi học, đi làm hay đến bất kỳ đâu một cách dễ dàng|
Dung lượng pin ổn 2 cell 37Wh, giúp cho người dùng có thể sử dụng thoải mái làm việc hay giải trí',

N'Loại card đồ họa    Intel UHD Graphics|
Dung lượng RAM    4GB|
Loại RAM    DDR4 2400MHz|
Số khe ram    1 Khe cắm / Hỗ trợ tối đa 12GB|
Ổ cứng    256GB SSD M.2 NVMe1 x 2.5" SATA , 1 x M.2 NVMe|
Kích thước màn hình    15.6 inches|
Pin    2 cell 37 Wh|
Hệ điều hành    Windows 11|
Độ phân giải màn hình    1920 x 1080 pixels (FullHD)|
Cổng giao tiếp    1 x USB 3.1 , 2 x USB 2.0 , Audio combo , LAN 1 Gb/s|
',8700000, 1, 1)

INSERT INTO product VALUES(4, N'Laptop MSI Gaming GF63 Thin 11UD 473VN',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/9/_/9_9_21.jpg',

N'Chip i5-11400H cùng card đồ họa rời RTX 3050 Ti cho khả năng chiến các tựa game nặng, chỉnh sửa hình ảnh trên PTS, Render video ngắn mượt mà|
Ram 8GB với 1 khe cắm có thể nâng cấp tối đa lên 64GB cùng ổ cứng SSD 512GB mang đến tốc độ xử lý nhanh cùng đa nhiệm mượt mà|
Màn hình LCD 15.6 inches với dải màu 72% NTSC cho trải nghiệm màu sắc chân thực|
Tích hợp web cam HD 720p cho phép đàm thoại thông qua video thoải mái|
Trọng lượng 1.86 kg thuận tiện di chuyển, mang theo|
Máy đi kèm Windows 11 Home bản quyền',

N'Loại card đồ họa    NVIDIA® GeForce RTX™ 3050 Ti Max-Q 4GB|
Dung lượng RAM    8GB|
Loại RAM    8 GB (8GBx1) DDR4 3200MHz (2 Khe, tối đa 64GB)|
Ổ cứng    512GB NVMe PCIe Gen3x4 SSD|
Kích thước màn hình    15.6 inches|
Công nghệ màn hình    72% NTSC IPS-Level, close to 100%s RGB|
Pin    3 cell, 51Whr|
Hệ điều hành    Windows 11 Home SEA|
Độ phân giải màn hình    1920 x 1080 pixels (FullHD)|
Cổng giao tiếp    1x Type-C USB3.2 Gen1  3x Type-A USB3.2 Gen1  1x (4K @ 30Hz) HDMI  1x Mic-in  1x Headphone-out|
',19400000, 1, 1)

INSERT INTO product VALUES(5, N'Laptop Lenovo Ideapad 5 Pro 14ITL6 82L300MAVN',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/t/e/text_ng_n_1__6.png',

N'Sở hữu phong cách thiết kế tao nhã, thời thượng với lớp vỏ kim loại bền chắc|
Màn hình 14 inch đi kèm độ phân giải 2.2K mãn nhãn thị giác người xem với chất lượng hình ảnh tuyệt hảo, sắc nét đến từng chi tiết|
Bộ vi xử lý Intel Core i7-1195G7 giải quyết nhẹ nhàng các nhu cầu học tập và giải trí|
RAM 16 GB chuẩn DDR4 nâng cao hiệu suất với các thao tác chuyển động giữa các ứng dụng trở nên nhanh gọn hơn|
Khối lượng chỉ vỏn vẹn 1.38 kg luôn sẵn sàng đồng hành cùng người dùng trên mọi chuyến công tác xa',

N'Loại card đồ họa    Intel Iris Xe Graphics|
Dung lượng RAM    16GB|
Loại RAM    16GB Onboard DDR4-3200|
Ổ cứng    512GB SSD M.2 2242 PCIe 3.0x4 NVMe|
Kích thước màn hình    14 inches|
Công nghệ màn hình    Độ sáng 300nits  Màn hình Anti-glare Độ phủ màu 100% sRGB TÜV Low Blue Light|
Pin    56.5Wh|
Hệ điều hành    Windows 11 Home 64 Single Language|
Độ phân giải màn hình    2240 x 1400 pixels|
Cổng giao tiếp    1x USB 3.2 Gen 1 1x USB 3.2 Gen 1 (Always On) 1x USB-C 3.2 Gen 1 (Truyền dữ liệu, Power Delivery 3.0, DisplayPort 1.4) 1x USB-C 3.2 Gen 1 (Truyền dữ liệu, Power Delivery 3.0) 1x HDMI 1.4b 1x Đầu đọc thẻ 1x Headphone / microphone co|
',22300000, 1, 1)

INSERT INTO product VALUES(6, N'Laptop Lenovo Ideapad 3 15IAU7 82RK001GVN',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/x/n/xnah_nh_.png',

N'Thiết kế tông Xanh lịch thiệp, màn hình 15.6 inch quan sát bắt mắt|
Hiệu năng chuyên đa ứng dụng với Intel Gen 12 và 512 GB lưu trữ|
Laptop Lenovo IdeaPad 3 15IAU7 82RK001GVN sử dụng hệ điều hành Windows 11, dung lượng pin 45W cho bạn thời lượng pin đủ để không bị gián đoạn khi đang sử dụng làm việc hay giải trí|
Laptop Lenovo IdeaPad 3 15IAU7 82RK001GVN còn được trang bị hệ thống âm thanh Dolby cho ra chất lượng âm thanh tuyệt vời và chân thật',

N'Loại card đồ họa    Intel UHD Graphics|
Dung lượng RAM    8GB|
Loại RAM    8GB DDR4-3200|
Ổ cứng    512GB SSD M.2 2242 PCIe 4.0x4 NVMe|
Kích thước màn hình    15.6 inches|
Công nghệ màn hình    TN 250nits Chống chói|
Pin    45Wh|
Hệ điều hành    Windows 11|
Độ phân giải màn hình    1920 x 1080 pixels (FullHD)|
Cổng giao tiếp    1x USB 2.0  1x USB 3.2 Thế hệ 1  1x USB-C 3.2 Gen 1 (hỗ trợ truyền dữ liệu, Power Delivery 3.0 và DisplayPort 1.2)  1x HDMI 1.4b  1x đầu đọc thẻ 1 x giắc cắm kết hợp tai nghe / micrô (3,5 mm)  1x Đầu nối nguồn|
',13600000, 1, 1)

INSERT INTO product VALUES(7, N'Laptop Acer Aspire 3 A315-58-53S6 NX.AM0SV.005',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/t/e/text_ng_n_9__1.png',

N'Thoải mái lưu trữ - 256GB SSD + 1 khe HDD trống cho phép nâng cấp|
4GB onboard + 4GB trên khe cho khả năng nâng cấp tối đa 12GB|
Thoả sức giải trí với các bộ phim hay edit hình ảnh - Màn hình Full HD, kích thước 15.6 inch|
Chơi game nhẹ nhàng với card đồ hoạ Intel® Iris® Xe Graphics',

N'Loại card đồ họa    Intel Iris Xe Graphics|
Dung lượng RAM    8GB|
Loại RAM    DDR4, tốc độ bus 2400Mhz|
Số khe ram    Nâng cấp tối đa 12GB (4GB onboard cắm sẵn + 4GB khe rời)|
Ổ cứng    256GB PCIe NVMe SSD (nâng cấp tối đa 2TB HDD và 1TBSSD PCIe NVMe)|
Kích thước màn hình    15.6 inches|
Pin    36.7 Wh 2-cell Li-ion battery|
Hệ điều hành    Windows 11 Home SL|
Độ phân giải màn hình    1920 x 1080 pixels (FullHD)|
Cổng giao tiếp    1 x USB 2.01 x HDMI®2.0 1 x Combo mic tai nghe 3.5mm 1 x Ethernet (RJ-45)  2 x USB 3.2 Gen 1|
',13000000, 1, 1)

INSERT INTO product VALUES(8, N'Laptop HP Pavilion 15-EG2037TX 6K783PA',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/_/0/_0005_screenshaot_1_.jpg',

N'I5 1235U - Xử lý tốt các tác vụ văn phòng|
8GB Ram cho khả năng xử lý đa nhiệm tốt, có thể tháo ra nâng cấp|
Card đồ hoạ MX550 - Chỉnh sửa ảnh trên PTS hay giải trí nhẹ nhàng với các tựa game|
Màn hình lớn 15.6 inch thoả sức làm việc hay giải tr&iacute.|
SSD 256GB cho khả năng lưu trữ đủ dùng, khởi động ứng dụng nhanh hơn',

N'Loại card đồ họa    NVIDIA® GeForce® MX550 2GB GDDR6|
Dung lượng RAM    8GB|
Loại RAM    DDR4  3200 MHz|
Ổ cứng    256 GB PCIe® NVMe™ M.2 SSD|
Kích thước màn hình    15.6 inches|
Pin    3-cell, 41 Wh Li-ion|
Hệ điều hành    Windows 11 Home SL|
Độ phân giải màn hình    1920 x 1080 pixels (FullHD)|
Cổng giao tiếp    1 USB Type-C2 USB Type-A1 HDMI 2.1  1 cổng mic tai nghe|
',16300000 , 1, 1)

INSERT INTO product VALUES(9, N'Laptop HP 245 G9 6L1N8PA',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/t/e/text_d_i_20.png',

N'Phù hợp sinh viên - nhân viên văn phòng với thiết kế mỏng nhẹ, màn hình 14 inch Full HD|
Cân mọi tác vụ học tập, văn phòng với CPU AMD Ryzen 5-5625U|
RAM 8 GB đa nhiệm hỗ trợ mở hàng chục tab duyệt web mà không lo lag, giật|
Ổ cứng 256GB SSD thoải mái lưu trữ mọi file, dữ liệu học tập|
Camera HD hỗ trợ hình ảnh sắc nét trong các buổi họp, call video, meet, vv',

N'Loại card đồ họa    AMD Radeon Graphics|
Dung lượng RAM    8GB|
Loại RAM    DDR4 3200MHz|
Số khe ram    2 khe|
Ổ cứng    256GB SSD M.2 NVMe|
Kích thước màn hình    14 inches|
Pin    3 cell 41 Wh|
Hệ điều hành    Windows 11 Home SL|
Độ phân giải màn hình    1920 x 1080 pixels (FullHD)|
Cổng giao tiếp    1x HDMI  1x USB Type C  2x USB 3.2  1x Audio combo  1x LAN 1 Gb/s|
',13000000, 1, 1)

INSERT INTO product VALUES(10, N'Laptop Lenovo Ideapad Gaming 3 15IHU6 82K101HGVN',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/t/e/text_ng_n_2__2_8.png',

N'Bộ vi xử lý Intel Core i5-11320 giải quyết tất tần tật mọi tác vụ Word, Excel, PowerPoint,... đến thiết kế đồ họa hay chiến game một cách mượt mà|
Card đồ họa RTX 3050 4GB GDDR6 giúp các tác vụ thiết kế Adobe Illustrator hay Premiere nhanh gọn|
RAM 8 GB cho phép mở nhiều cửa sổ ứng dụng khác nhau rất mượt mà|
Mở máy nhanh chóng cũng như không gian lưu trữ thoải mái hơn nhờ ổ cứng 512 GB SSD|
Đèn nền Led RGB hỗ trợ làm việc trong môi trường thiếu sáng hay mang lại cảm giác phấn khích khi chiến game|
Màn hình 15.6 inch cùng tấm nền IPS cho góc nhìn lên đến 178 độ, giúp theo dõi đối thủ trên bản đồ một cách chính xác',

N'Loại card đồ họa    NVIDIA GeForce RTX 3050 4GB GDDR6, Boost Clock 1500 / 1740MHz, TGP 90W|
Dung lượng RAM    8GB|
Loại RAM    8GB SO-DIMM DDR4-3200|
Số khe ram    2 khe cắm DDR4 SO-DIMM, hỗ trợ kênh đôi|
Ổ cứng    512GB SSD M.2 2280 PCIe 3.0x4 NVMe1 khe 2.5" tối nâng tối đa 1TB M.2 2242 SSD nâng tối đa 512GB|
Kích thước màn hình    15.6 inches|
Công nghệ màn hình    Độ sáng 250 nits  Màn hình chống chói Độ phủ màu 45% NTSC Tần số quét 120Hz|
Pin    45Wh|
Hệ điều hành    Windows 11 Home Single Language|
Độ phân giải màn hình    1920 x 1080 pixels (FullHD)|
Cổng giao tiếp    2x USB 3.2 Gen 1 1x USB-C 3.2 Gen 1 (chỉ hỗ trợ truyền dữ liệu) 1x HDMI 2.0 1x Ethernet (RJ-45) 1x Headphone / microphone combo jack (3.5mm) 1x Đầu nối nguồn|
',19600000 , 1, 1)
-- PC
INSERT INTO product VALUES(11, N'PC CPS Gaming G3',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/p/c/pc_gaming_3_1.png',

N'PC chứa đựng bộ vi xử lý Intel Core i3 cùng khả năng ép xung với đồ họa GTX 1660 có nhiều nâng cấp|
Dung lượng bộ nhớ trong đạt 240 GB|
RAM 8GB|
Đồng thời bộ nguồn Deepcool PF550D 550W sẽ giúp cho thiết bị đảm bảo được sự ổn định của nguồn điện, trong suốt quá trình vận hành',

N'Socket    Intel LGA 1700|
Loại card đồ họa    GTX 1660 Super|
Dung lượng RAM    8GB|
Loại RAM    DDR4 3200Mhz|
Số khe ram    2 x DDR4|
Ổ cứng    SSD 240GB Sata III 2.5inch|
Chipset (PC lắp ráp)    H610|
Cổng I/O mặt sau (PC lắp ráp)    2 x Antenna Mounting Points1 x PS/2 Mouse/Keyboard Port1 x D-Sub Port1 x HDMI Port1 x DisplayPort 1.42 x USB 3.2 Gen1 Ports (Supports ESD Protection)2 x USB 2.0 Ports (Supports ESD Protection)1 x RJ-45 LAN Port with LED (ACT/LINK LED and SPEED LED)HD Audio Jacks: Line in / Front Speaker / Microphone|
',26700000, 1, 2)

INSERT INTO product VALUES(12, N'PC CPS Gaming G8',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/p/c/pc-cps-gaming-g8-12.png',

N'Thùng PC CPS Gaming G8 sở hữu con chip Intel Core i7-13700F với cấu trúc 16 nhân, 24 luồng cùng mức xung nhịp đạt đến 5.2GHz|
Chiếc card đồ họa rời cao cấp RTX 3070TI, thùng PC có khả năng xử lý mọi tựa game yêu cầu mức cấu hình cao|
Nhờ trang bị bộ nhớ SSD đạt đến 500GB, thùng PC giúp bạn tiết kiệm thời gian khởi động máy và các phần mềm|
Với SSD 500GB cùng dung lượng RAM 8GB, tốc độ hoạt động của thùng PC được nâng cấp đáng kể',

N'Socket    Intel LGA 1700|
Loại card đồ họa    RTX 4070Ti OC|
Dung lượng RAM    16GB|
Loại RAM    2 x 8GB DDR4 3200Mhz|
Số khe ram    2 x DDR4|
Ổ cứng    SSD 500GB M2 PCIe|
Chipset (PC lắp ráp)    Z790|
Cổng I/O mặt sau (PC lắp ráp)    1 x USB Type-C, hỗ trợ USB 3.2 Gen 2x22 x USB 3.2 Gen 2 Type-A (màu đỏ)3 x USB 3.2 Gen 14 x USB 2.0/1.12 x SMA (2T2R)1 x HDMI 2.01 x RJ-451 x S/PDIF Out2 x jack âm thanh|
',56740000, 1, 2)

INSERT INTO product VALUES(13, N'PC CPS văn phòng S3',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_112_2_.png',

N'Bộ vi xử lý Intel Core i5-11400|
Những phụ kiện được lắp đặt trên mainboard ASUS PRIME B560M-A  có tính tương thích cao, tạo được hiệu suất ổn định, bền vững|
RAM 8GB đa nhiệm với mọi công việc|
Ổ cứng SSD Sata III chuẩn chứa đựng dung lượng lên đến 120GB|
Thiết kế case XIGMATEK XA-20 một mặt trong suốt giúp người chơi có thể quan sát hết những thức bên trong được build',

N'Socket    Intel LGA 1200|
Loại card đồ họa    Intel® UHD 630|
Dung lượng RAM    8GB|
Loại RAM    DDR4 3200Mhz|
Số khe ram    4 x DDR4|
Ổ cứng    SSD 120GB Sata III|
Chipset (PC lắp ráp)    B560|
Cổng I/O mặt sau (PC lắp ráp)    2 x USB 3.2 Gen 2 port(s) (1 x Type-A, 1 x USB Type-C4 x USB 2.0 port(s) (4 x Type-A)1 x DisplayPort2 x HDMI™ port1 x Intel® I219-V 1Gb Ethernet port1 x V-M.2 slot (Key E)3 x Audio jacks1 x PS/2 Keyboard/Mouse combo port|
',11680000, 1, 2)

INSERT INTO product VALUES(14, N'PC CPS đồ họa D2',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_209_4_.png',

N'Phần vỏ case bên ngoài của thiết bị được thiết kế theo kiểu dáng XIGMATEK ELITE 1 3F giúp người dùng vừa làm việc vừa có thể chiêm ngưỡng được các linh kiện, bộ phận phía bên trong của máy|
Con chip siêu mạnh tới từ nhà Intel - Core i7|
Hệ thống bo mạch chủ thế hệ mới với chipset hiện đại B760 cùng card đồ họa đỉnh cao GTX1650 đem tới những trải nghiệm tuyệt vời nhất cho người dùng trong quá trình sử dụng|
Với 16GB RAM và 500GB bộ nhớ SSD(vẫn có thể được mở rộng thêm), người dùng có thể thỏa sức lưu trữ dữ liệu, trải nghiệm game đồ họa nặng',

N'Socket    Intel LGA 1700|
Loại card đồ họa    GTX 1650|
Dung lượng RAM    16GB|
Loại RAM    1 x 16GB DDR4 3200Mhz|
Số khe ram    4 x DDR4|
Ổ cứng    SSD 500GB M2 PCIe|
Chipset (PC lắp ráp)    B760|
Cổng I/O mặt sau (PC lắp ráp)    2 cổng USB 2.0/1.11 cổng bàn phím/chuột PS/22 x SMA (2T2R)1 x HDMI2 x DisplayPorts3 x USB 3.2 Gen 11 x USB Type-C1 x RJ-453 x jack âm thanh|
',29000000, 1, 2)

INSERT INTO product VALUES(15, N'PC CPS đồ họa D1',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/p/c/pc_cps_o_hoa_d1_1_.png',

N'PC CPS đồ họa D1 được trang bị bộ vi xử lý mạnh mẽ Intel Core i7 thế hệ mới nhất, có khả năng tương thích với nhiều dòng mainboard trên thị trường|
Và để khả năng xử lý hình ảnh, đồ hoạ được nâng cao hơn, PC CPS D1 còn được trang bị thêm card đồ hoạ VGA GIGABYTE GT 1030 với khả năng hỗ trợ độ phân giải tối đa lên đến 4K|
PC CPS đồ họa D1 được trang bị tới 16GB RAM, cao hơn mức RAM tiêu chuẩn tới 2 lần để bạn có thể thực hiện công việc một cách trơn tru, mượt mà|
Và để việc lưu trữ dữ liệu thoải mái hơn, PC CPS D1 còn sở hữu ổ cứng SSD với dung lượng lưu trữ lên đến 240GB',

N'Socket    Intel LGA 1700|
Loại card đồ họa    GT1030|
Dung lượng RAM    16GB|
Loại RAM    DDR4 3200Mhz|
Số khe ram    1 x 16GB DDR4 3200Mhz|
Ổ cứng    SSD 240GB Sata III|
Chipset (PC lắp ráp)    B760|
Cổng I/O mặt sau (PC lắp ráp)    2 cổng USB 2.0/1.11 cổng bàn phím/chuột PS/22 x SMA (2T2R)1 x HDMI2 x DisplayPorts3 x USB 3.2 Gen 11 x USB Type-C1 x RJ-453 x jack âm thanh|
',22730000 , 1, 2)

INSERT INTO product VALUES(16, N'Máy tính văn phòng All In One văn phòng Asus V241EAT-BA057W',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_182.png',

N'CPU Intel Core i3 1115G4 cùng Card Intel UHD đảm bảo mọi tác vụ văn phòng hay xem phim, giải trí,vv diễn ra trơn tru|
RAM 4 GB cho khả năng đa nhiệm tốt. SSD 512 GB cho không gian lưu trữ đủ dùng, thời gian phản hồi nhanh|
Màn hình cảm ứng 23.8 inch với công nghệ Wide View cung cấp góc nhìn rộng mở cùng độ phủ màu đạt 100% sRGB|
Đa dạng cổng giao tiếp với 2 x HDMI, 4 x USB 3.2, Jack tai nghe 3.5 mm, LAN (RJ45), USB 2.0|
Thiết kế sang trọng, tinh tế nhưng gọn gàng, phù hợp cho mọi không gian làm việc',

N'Loại card đồ họa    Intel® UHD Graphics|
Dung lượng RAM    4GB|
Loại RAM    4GB DDR4/ 3200MHz - RAM NoteBook|
Số khe ram    2 khe|
Ổ cứng    512 GB SSD PCIe (M.2 2280)|
Kích thước màn hình    23.8 inches|
Công nghệ màn hình    FHD (1920 x 1080) IPS  Wide view  Anti-glare  Touch-screen | 16:9|
Hệ điều hành    Windows 11 Home Single Language 64-bit|
Độ phân giải màn hình    1920 x 1080 pixels (FullHD)|
Cổng giao tiếp    Side I/O Ports: 1x USB 2.0 Type-A  1x Headphone/ Microphone combo audio jack  1x Mode button  1x Kensington lock Back I/O Ports: 4x USB 3.2 Gen 1 Type-A  1 x HDMI-in  1x HDMI-out  1x RJ45 (LAN)  1x SATA3 (không cáp)|
',18990000, 1, 2)

INSERT INTO product VALUES(17, 'Máy tính để bàn All In One văn phòng ASUS E5202WHAK BAO19W',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_186.png',

N'Thiết kế thời trang, thanh lịch|
Bộ nhớ RAM 4GB cho khả năng đa nhiệm ổn định, cùng với ổ cứng SSD 512 GB truy xuất dữ liệu dễ dàng, khởi động máy nhanh hơn|
Chip bảo mật TPM 2.0 với khả năng mã hóa thông tin cá nhân, nhằm giúp người dùng đảm bảo dữ liệu danh tính cá nhân tránh sự xâm nhập trái phép từ nguồn bên ngoài|
Trọng lượng thích hợp với mọi không gian phòng (7,7kg)',

N'Loại card đồ họa    Intel UHD Graphics|
Dung lượng RAM    4GB|
Loại RAM    DDR4 2 khe (1 khe 4GB + 1 khe trống)|
Số khe ram    2 khe tối đa 32GB|
Ổ cứng    512 GB SSD   Hỗ trợ thêm 1 khe cắm HDD SATA (Nâng cấp tối đa 1TB)   Hỗ trợ thêm 1 khe cắm SSD M2 PCIe mở rộng|
Kích thước màn hình    21.5 inches|
Công nghệ màn hình    Màn hình chống loá, 100% sRGB, 250 nits|
Hệ điều hành    Windows 11 Home SL|
Độ phân giải màn hình    1920 x 1080 pixels (FullHD)|
Cổng giao tiếp    1x USB 3.2 Gen 2 Type-   1x Thunderbolt 4   2 x USB 2.0   3 x USB 3.2   HDMI   Jack 3.5 mm   LAN (RJ45)|
',16990000, 1, 2)

INSERT INTO product VALUES(18, N'PC Gaming Asus Rog Strix GA35 G35DX-VN003W',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_160.png',

N'Chip được tích hợp dưới nền tảng socket AM4 với 8 nhân 16 luồng cho xung nhịp tối đa|
Bộ nhớ RAM đến 16GB được cung cấp khả năng đa nhiệm và hiệu năng mạnh mẽ|
PC Asus Rog Strix Ga35 G35dx VN003W dựa theo kiến trúc RTX của nhà NVIDIA',

N'Loại card đồ họa    NVIDIA GeForce RTX3070 8GB DDR6|
Dung lượng RAM    16GB|
Loại RAM    DDR4-3200 LO-DIMM|
Ổ cứng    1TB M.2 NVMe™ PCIe® 4.0 SSD|
Cổng giao tiếp    Mặt trước:  2x USB 3.2 Gen 1 Type-A  2x USB 3.2 Gen 1 Type-C  1x Headphone  1x Microphone  Mặt sau:  1x USB 3.2 Gen 2 Type-C  3x USB 3.2 Gen 2 Type-A  4x USB 3.2 Gen 1 Type-A  1x Mic-in  1x Center / Sub Wo|
',49890000, 1, 2)

INSERT INTO product VALUES(19, N'PC Gaming Asus ROG Strix GT15 G15CF-71270F110W',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_176_2_.png',

N'Sở hữu con chip Intel Core i7-12700F mới nhất hiện nay, với khả năng ép xung tối đa lên tới 4,9GHz cùng 12 nhân và 20 luồng có thể hoạt động song song|
Được trang bị 1 ổ cứng SSD 512GB|
Dung lượng RAM tiêu chuẩn của PC là 16GB có tốc độ bus 3200MHz|
Card đồ hoạ rời GeForce RTX 3070 8GB',

N'Loại card đồ họa    NVIDIA GeForce RTX RTX3070 8GB DDR6 (LHR: 3x DP, 2x HDMI)|
Dung lượng RAM    16GB|
Loại RAM    16 GB DDR4 U-DIMM|
Số khe ram    2 khe tối đa 64 GB|
Ổ cứng    512 GB M.2 NVMe PCIe 4.0 SSD|
Chipset (PC lắp ráp)    B660|
Cổng I/O mặt sau (PC lắp ráp)    1x RJ45 Gigabit Ethernet 1x HDMI 2.0a1x Display port 1.45 x Audio jacks4x USB 2.0 Type-A1x USB 3.2 Gen 2 Type-C2x USB 3.2 Gen 2 Type-A|
Hệ điều hành    Windows 11 Home|
Cổng giao tiếp    Cổng trước: 1x Headphone1x Mic in1x USB 3.2 Gen 1 Type-A1x USB 3.2 Gen 1 Type-C|
',45990000, 1, 2)

INSERT INTO product VALUES(20, N'PC Gaming Asus ROG Strix GT15 G15CF-51240F141W',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_176_1__1.png',

N'Một mặt kính trong suốt giúp người dùng có thể dễ dàng nhìn thấy hiệu ứng đèn RGB bên trong|
Hiệu năng mượt mà mạnh mẽ, kết nối đa dạng|
Ngoài ra, trên PC còn được trang bị card đồ hoạ rời cho mọi trải nghiệm tuyệt đỉnh hơn bao giờ hết',

N'Loại card đồ họa    NVIDIA GeForce RTX3060Ti 8GB DDR6 (3x DP, 2x HDMI)|
Dung lượng RAM    16GB|
Loại RAM    8GB DDR4 U-DIMM x 2|
Số khe ram    2 khe tối đa 64 GB|
Ổ cứng    512 GB M.2 NVMe PCIe 4.0 SSD|
Chipset (PC lắp ráp)    B660|
Cổng I/O mặt sau (PC lắp ráp)    1x RJ45 Gigabit Ethernet 1x HDMI 2.0a1x Display port 1.45 x Audio jacks4x USB 2.0 Type-A1x USB 3.2 Gen 2 Type-C2x USB 3.2 Gen 2 Type-A|
Hệ điều hành    Windows 11 Home|
Cổng giao tiếp    Cổng trước: 1x Headphone1x Mic in1x USB 3.2 Gen 1 Type-A1x USB 3.2 Gen 1 Type-C|
',37990000 , 1, 2)

-- Màn hình
INSERT INTO product VALUES(21, N'Màn hình Samsung LS24R350FZEXXV 23.8',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/3/7/37_1_10.jpg',

N'Màn hình viền mỏng Full HD (1920x1080)|
Công nghệ AMD Radeon FreeSync™trải nghiệm không gián đoạn, chuyển động mượt mà|
Tốc độ làm mới đạt 75Hz giúp hiển thị rõ nét mọi chuyển động',

N'Tần số quét    75 Hz|
Thời gian phản hồi    5ms|
Tỉ lệ màn hình    16:9|
Cổng kết nối    1x HDMI  1x D-Sub|
Độ tương phản tĩnh    1000:1|
Góc nhìn    178°/178°|
Tấm nền    IPS|
Kích thước màn hình    23.8 inches|
Trọng lượng    3.4 kg bao gồm chân đế|
Độ phân giải màn hình    Full HD (1920x1080)|
',4789000 , 1, 3)

INSERT INTO product VALUES(22, N'Màn hình LG Ultrawide 29WN600 29 inch',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/m/a/man-hinh-cong-lg-ultrawide-29-inch-29wn600-2.png',

N'Màn hình 29 inch Full HD 2560 x 1080 tỷ lệ 21:9|
Tần số quét 75Hz|
Độ phủ màu 99% sRGB|
Chế độ tùy chỉnh không gian làm việc trên màn hình|
Công nghệ AMD FreeSync, HDR10|
Thiết kế gần như không viền',

N'Tần số quét    75 Hz|
Thời gian phản hồi    5ms|
Tỉ lệ màn hình    21:9|
Cổng kết nối    2 cổng HDMI1 DisplayPort 1.2|
Độ tương phản tĩnh    1000:1|
Góc nhìn    178/178|
Tấm nền    IPS|
Kích thước màn hình    29 inches|
Trọng lượng    Khối lượng gồm chân để:4,7kg|
Độ phân giải màn hình    2560x1080 pixels|
Tính năng khác    Hiệu ứng HDR Chống chớp hình Chế độ mù màu AMD FreeSync™	Dynamic Action Sync Tiết kiệm năng lượng thông minh|
',6990000, 1, 3)

INSERT INTO product VALUES(23, N'Màn hình LG UltraWide 29WP60G 29 inch',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/m/a/man-hinh-lg-ultrawide-29-inch-29wp60g-4.jpg',

N'Màn hình 29" UltraWide Full HD nhiều không gian hơn cho công việc đa nhiệm|
Công nghệ HDR10 giúp bạn có thể thưởng thức nội dung với màu sắc ấn tượng|
Tấm nền IPS với sRGB 99% hiển thị màu sắc chính xác hoàn hảo với góc nhìn rộng|
1ms MBR giúp chơi game mượt mà, không bị nhòe hay bóng mờ, chiến thắng mọi trận đấu|
Công nghệ AMD FreeSync, người chơi game có thể trải nghiệm chuyển động liền mạch, mượt mà',

N'Tần số quét    75 Hz|
Thời gian phản hồi    5ms|
Tỉ lệ màn hình    21:9|
Cổng kết nối    1 x HDMI  1 x DisplayPort 1.4  1 x USB-C  1 x Jack tai nghe 3.5|
Độ tương phản tĩnh    1000:1|
Góc nhìn    178º(R/L), 178º(U/D)|
Tấm nền    IPS|
Kích thước màn hình    29 inches|
Trọng lượng    Khối lượng không tính chân đế: 4kg  Khối lượng tính cả chân đế: 4.8kg|
Độ phân giải màn hình    2560 x 1080 pixels|
',7290000, 1, 3)

INSERT INTO product VALUES(24, N'Màn hình Samsung LU28R550UQEXXV 28 inch',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/2/_/2_63_1.png',

N'Tận hưởng từng chi tiết khung hình với chất lượng 4K Ultra HD ấn tượng|
Tấm nền IPS siêu việt giúp tái hiện hình ảnh rõ ràng với sắc màu sống động|
Công nghệ HDR ưu việt giúp hình ảnh trở nên rõ ràng, sắc nét đến từng chi tiết phức tạp nhất|
Tính năng hạn chế tối đa phát xạ ánh sáng xanh dễ gây ra tình trạng mỏi mắt|
Công nghệ AMD FreeSync chuyển động mượt mà sắc nét, nâng tầm giải trí|
Thiết kế 3 cạnh không viền thời thượng mở rộng tối đa không gian',

N'Tần số quét    60 Hz|
Thời gian phản hồi    4ms|
Tỉ lệ màn hình    16:9|
Cổng kết nối    Cổng màn hìnhCổng HDMICổng Display Port 1.2|
Độ tương phản tĩnh    1.000:1|
Góc nhìn    178°(ngang) x 178° (dọc)|
Tấm nền    IPS|
Kích thước màn hình    28 inches|
Trọng lượng    Có chân đế 5.8 kg  Không có chân đế 5.0 kg|
Độ phân giải màn hình    3,840 x 2,160 pixels|
',8889000, 1, 3)

INSERT INTO product VALUES(25, N'Màn hình HKC MB24V9 24 inch',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_179_1.png',

N'Thiết kế với đường viền hẹp tạo cảm giác tinh tế khi sử dụng|
Kích thước 24" FHD hiển thị rõ nét, sinh động từng chi tiết|
Tốc độ làm mới 75Hz tạo nên không gian giải trí mượt mà|
Tích hợp cổng kết nối VGA/HDMI giúp truyền tải tốt chất lượng hình ảnh',
'Tần số quét    75 Hz|
Thời gian phản hồi    8ms|
Tỉ lệ màn hình    16:9|
Cổng kết nối    VGA  HDMI|
Độ tương phản tĩnh    1000 : 1|
Góc nhìn    178/178|
Tấm nền    IPS|
Kích thước màn hình    24 inches|
Kích thước màn hình (studio display)    23.8 inch|
Trọng lượng    4.1 kg|
Độ phân giải màn hình    1920 x 1080 pixels|
',3790000, 1, 3)

INSERT INTO product VALUES(26, N'Màn hình Samsung Gaming Odyssey G32 LS27AG320NEXXV 27 inch',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/a/gaming_002_1.png',

N'Tần số quét 165Hz trải nghiệm mượt mà khi chiến đấu|
Phản xạ nhanh chóng trước mọi tình huống nhờ tốc độ phản hồi 1ms|
Công nghệ AMD FreeSync Premium mỗi phân cảnh chuyển động đều trở nên liền mạch và mượt mà|
Thiết kế tràn viền 3 cạnh ấn tượng và màn hình cho phép bạn xoay, nghiêng dễ dàng điều chỉnh',

N'Tần số quét    165 Hz|
Thời gian phản hồi    1ms|
Tỉ lệ màn hình    16:9|
Cổng kết nối    Display Port 1.2 (1), HDMI 1.4 (1), Tai nghe|
Độ tương phản động    3000:1(Typ.)|
Góc nhìn    178°(H)/178°(V)|
Tấm nền    WVA|
Kích thước màn hình    27 inches|
Trọng lượng    4.8 kg bao gồm chân đế|
Độ phân giải màn hình    1920 x 1080 pixels|
',7890000 , 1, 3)

INSERT INTO product VALUES(27, N'Màn hình Gaming LG Ultragear 24GN650-B 24 inch',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/7/_/7_18_78.jpg',

N'Thiết kế bắt mắt và viền màn hình siêu mỏng, phần đế có thể thay đổi độ cao, nghiêng và xoay|
IPS 1ms giảm thiểu hiện tượng dư ảnh và thời gian phản hồi nhanh|
Trang bị tốc độ làm mới 144Hz mang đến trải nghiệm chơi game mượt mà|
Công nghệ HDR10 với sRGB 99% mang lại hình ảnh chân thực với màu sắc phong phú|
Công nghệ AMD FreeSync Premium giúp trải nghiệm chuyển động liền mạch, trôi chảy',

N'Tần số quét    144 Hz|
Thời gian phản hồi    1ms|
Tỉ lệ màn hình    16:9|
Cổng kết nối    2 x HDMI  1 x DisplayPor  Headphone Out|
Độ tương phản tĩnh    1000:1|
Góc nhìn    178º (R/L), 178º (U/D)|
Tấm nền    IPS|
Kích thước màn hình    24 inches|
Trọng lượng    5.6Kg|
Độ phân giải màn hình    1920 x 1080|
',5990000, 1, 3)

-- Bàn phím
INSERT INTO product VALUES(28, N'Bàn phím cơ Rapoo V500 Pro Yellow Blue',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/i/m/image_9_1.png',

N'Bàn phím có LED trắng và 12 hiệu ứng ánh sáng khác nhau cho người dùng trải nghiệm chơi game tốt hơn|
trang bị cho mình Blue Switch, là loại switch màu xanh. Bàn phím cơ Rapoo V500 Pro Yellow Blue sẽ mang lại cảm giác vui tai khi sử dụng|
Nhấn tổ hợp phím cũng dễ dàng, cải thiện trải nghiệm chơi game của người dùng',

N'Loại bàn phím (bàn phím)    Full-size|
Số phím (bàn phím)    104|
Keycap (bàn phím)    Thiết kế keycap có chủ ý để chiếu sáng chính xác và rõ ràng|
Nút nhấn    Thiết kế chống tràn, không xung đột cho tất cả các phím|
Tương thích    Windows|
Kết nối    Dây USB|
Đèn LED    Đèn nền phím màu trắng với 12 chế độ chỉnh độ sáng|
Tiện ích    Tuổi thọ bàn phím lên đến 60 triệu lần gõCảm giác cơ học thuần túy, phù hợp với những game thủ cao|
Hãng sản xuất    Rapoo|
Chất liệu    Nhựa PBT|
Kết nối    USB 2.0|
', 990000, 1, 4)

INSERT INTO product VALUES(29, N'Bàn Phím Cơ Gaming Predator Aethon TKL 301',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_163_4.png',

N'Thiết kế bàn phím gọn nhẹ, kích thước tối ưu|
Đèn LED đa dạng màu sắc với N-Key Rollover đạt chuẩn bền bỉ|',

N'Đèn nền LED    Có|
Hãng sản xuất    Acer|
Chất liệu    Nhựa ABS|
Tiện ích    Trang bị Switch Outemu Blue, lực nhấn 60g   N-Key Rollover đầy đủ, đạt tiêu chuẩn 50 triệu lần nhấn|'
,2590000, 1, 4)

INSERT INTO product VALUES(30, N'Bàn Phím Gaming ZADEZ G-852K',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_184_3.png',

N'Bàn phím thiết kế đầy đủ phím bấm kèm bàn phím số tích hợp hỗ trợ nhập dữ liệu, tính toán và điều hướng diễn ra nhanh chóng|
Phím bấm cơ học sử dụng Switch bán cơ cho phản ứng chính xác và nhanh hơn, cùng với âm thanh nhấn - click và cảm giác cơ học|
Bàn phím chống nước cực ổn, giúp người dùng không cần llo lắng trong trường hợp không may đánh đổ nước khi sử dụng|
Trang bị dây USB kết nối dài 1.8m chỉ cần cắm vào cổng USB máy tính để sử dụng|
Chức năng cài đặt Macro và 3 phím nóng để chuyển hồ sơ macro (Macro Profile), rất hữu dụng với game thủ chơi nhiều thể loại game|
Đèn nền LED 7 màu hỗ trợ điều chỉnh độ sáng, chế độ hiển thị bằng cả phần mềm và phím tắt',

N'Tương thích    Windows|
Cách kết nối    Dây cắm USB|
Hãng sản xuất    Zadez|
Tính năng khác    - Thiết kế Ergonomic - 11 phím Multimedia  4 phím Macro, 3 phím chuyển Profile  Chức năng ghi macro bằng phím nóng  - 11 phím Multimedia, 26 phím N-Rollover   Độ bền phím: >10 triệu lần nhấn|
',600000 , 1, 4)

INSERT INTO product VALUES(31, N'Bàn phím Bluetooth Logitech K380',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_149_1_.png',

N'Thiết kế hiện đại, mỏng nhẹ cùng với nhiều màu sắc trẻ trung, tiện lợi khi mang theo và không tốn nhiều diện tích trên bàn làm việc|
Trang bị kết nối Bluetooth tiện lợi với khoảng cách tối đa 10m|
Tương thích với nhiều hệ điều hành như Windows®, macOS, Chrome OS™, Android™, iOS, và thậm chí cả Apple TV|
Khả năng kết nối cùng lúc 3 thiết bị cùng chức năng Easy-Switch hỗ trợ chuyển đổi giữa các thiết bị nhanh chóng|
Bàn phím thiết kế thấp dạng cắt kéo hơi lõm ôm lấy hình dáng đầu ngón tay cho trải nghiệm gõ mượt mà, yên tĩnh|
Trang bị 2 Pin AAA cho thời gian sử dụng thoải mái lên tới 24 tháng',

N'Pin    2 pin AAA, tuổi thọ pin 24 tháng.|
Tương thích    Windows, MacOS, ChromeOS, iOS, Android, PadOS|
Cách kết nối    Bluetooth|
Độ dài dây / Khoảng cách kết nối    10 m|
Kích thước bàn phím    Tenkeyless|
Đèn nền LED    Không|
Hãng sản xuất    Logitech|
',750000, 1, 4)

INSERT INTO product VALUES(32, N'Bàn phím không dây Logitech K270',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_165_2__1.png',

N'Thiết kế đơn giản và tiện dụng|
Được phủ lớp chống tia cực tím|
Bộ phím full size cho khả năng gõ nhanh, êm với độ nhạy phím tốt|
Hiệu năng sử dụng cao trong phạm vi 10m mà hoàn toàn không có tình trạng bị trễ, mất kết nối và nhiễu sóng',

N'Hãng sản xuất    Logitech|
Cổng giao tiếp    USB (Khoảng cách kết nối: 10m)|
Tính năng khác    - Bàn phím gõ thoải mái- Phím dễ đọc- Phím nhấn bền|
',450000, 1, 4)

INSERT INTO product VALUES(33, N'Bàn phím không dây Logitech MX Keys mini',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/f/r/frame_1_1__2.png',

N'Thiết kế tối giản, thanh lịch giúp bạn đặt gọn gàng trên bàn làm việc|
Tạo hình vừa với ngón tay giúp gõ siêu mượt mà và giảm tiếng ồn|
Phím F6 đọc chính tả giúp chuyển từ đàm thoại sang văn bản|
Trang bị đèn LED chiếu sáng hỗ trợ bạn làm việc vào buổi tối|
Sử dụng pin sạc thời lượng đến 10 ngày hoặc 5 tháng (tắt đèn nền)|
Dễ dàng chuyển tệp, tài liệu và hình ảnh trên nhiều thiết bị hoặc hệ điều hành',

N'Loại bàn phím (bàn phím)    Mini-size|
Nút nhấn    Phím được tạo hình vừa vặn với ngón tayMỗi phím được trang bị lớp phủ mờ|
Tương thích    MacOS, Windows, Linux|
Kết nối    USB Receiver, Dây USB|
Đèn LED    Có hỗ trợ|
Tiện ích    Tính năng tiết kiệm pin thông minhPhím đọc chính tả giúp chuyển đàm thoại thành văn bản|
Hãng sản xuất    Logitech|
',2690000 , 1, 4)

INSERT INTO product VALUES(34, N'Bàn phím cơ AKKO MonsGeek MG108',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_21_4.png',

N'Thiết kế hiện đại|
Sở hữu Switch độc quyền',

N'Loại bàn phím (bàn phím)    Full-size|
Số phím (bàn phím)    108|
Keycap (bàn phím)    PBT Double-Shot, ASA profile|
Tương thích    MacOS, Windows, Linux|
Kết nối    Dây USB to Type-C|
Hãng sản xuất    AKKO|
Kết nối    USB Type-C to Type-A|
Tính năng khác    AKKO switch v3 (Cream Blue / Cream Yellow) kết hợp cùng foam tiêu âm PCB cho trải nghiệm gõ tối ưu và êm ái, LED nền RGB / PCBA 1.6mm|
',1399000 , 1, 4)
-- Chuột
INSERT INTO product VALUES(35,N'Chuột Gaming Rapoo V16',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/1/_/1.6_4.png',

N'Thiết kế cân xứng, bề mặt phủ lớp chống trơn trượt mang lại cảm giác thoải mái khi sử dụng|
Trang bị cảm biến 2000DPI hoạt động hiệu quả và di chuyển mượt mà|
Dây USB kết nối dài 2.1m dễ dàng kết nối với thiết bị và sử dụng ổn định|
Tốc độ phản hồi nhanh chóng chỉ 1 ms (0.001 giây), xử lý nhanh và chính xác|
Trang bị đèn LED với và tính năng chớp tắt giúp có những trải nghiệm thú vị hơn',

N'Tương thích    Windows, MacOS|
Cách kết nối    Dây cắm USB|
Đèn nền LED    Có|
Hãng sản xuất    Rapoo|
Tính năng khác    DPI: Lên tới 2000 DPI Nút chuyển đổi DPI nhanh trên chuột với 3 mức 750/1350/2000 DPI. Tuổi thọ: 5 triệu lần click. Tốc độ xử lý hình ảnh lên đến 6400/giây|
',
390000 ,1,5)

INSERT INTO product VALUES(36, N'Chuột không dây Elecom M-IR07DRBK',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/u/n/untitled-dcd.jpg',

N'Thân chuột được thiết kế thon gọn cho cảm giác cầm nắm thoải mái đồng thời giảm mỏi tay khi sử dụng trong thời gian dài|
Độ phân giải 1200 DPI sẽ giúp sử dụng ổn định và trơn tru hoàn hảo|
Sử dụng pin AA thông dụng cùng với chế độ tiết kiệm năng lượng cho thời gian sử dụng kéo dài lên đến 12 tháng',

N'Pin    Lên đến 12 tháng|
Tương thích    Windows, MacOS|
Cách kết nối    USB Receiver (Đầu thu USB)|
Độ dài dây / Khoảng cách kết nối    10 m|
Hãng sản xuất    Elecom|
Lõi pin    1 pin AA|
Cảm biến    1200 DPI|
',
249000 ,1,5)

INSERT INTO product VALUES(37, N'Chuột Bluetooth Microsoft Mouse',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_95.png',

N'Thiết kế nhỏ gọn, hiện đại vừa vặn thoải mái trong tay để cuộn và điều hướng chính xác|
Trang bị cảm biến 1000 DPI hoạt động hiệu quả khi di chuyển trên hầu hết các bề mặt|
Hỗ trợ kết nối với thiết bị thông qua Bluetooth trong khoảng cách lên tới 10m|
Hoạt động bằng 1 viên Pin AA, thời lượng sử dụng cực kỳ cao lên đến 12 tháng',

N'Độ phân giải    1000 DPI|
Nút nhấn    4 nút: trái, phải, bánh xe, nguồn (nhấn >3 giây để kết nối)|
Tương thích    MacOS, Windows|
Kết nối    Bluetooth|
Tiện ích    Bánh xe hiện đại để cuộn dọc và nhấp nút. Swift Pair để kết nối chuột với máy tính dễ dàng. Điều chỉnh độ phân giải. Xác định lại chức năng nút bánh xe. Hoán đổi nhấp chuột trái và phải. Tùy chỉnh bánh xe, báo cáo mức pin|
Hãng sản xuất    Microsoft|
',
590000 ,1,5)

INSERT INTO product VALUES(38, N'Chuột có dây MSI M88',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_98_1.png',

N'Thiết kế đối xứng, sử dụng tốt cho cả hai tay|
Độ phân giải 1600 DPI giúp di chuyển mượt mà|
Kết nối tiện lợi thông qua dây USB dài đến 2m|
Trọng lượng nhẹ giúp sử dụng linh hoạt hơn',

N'Cảm biến    1600 DPI|
Tương thích    Windows|
Kết nối    Dây USB|
Hãng sản xuất    MSI|',
300000 ,1,5)

INSERT INTO product VALUES(39, N'Chuột không dây Dareu LM115G',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_51_2_.png',

N'Thiết kế bo tròn tạo cảm giác thoải mái khi sử dụng|
Kết nối không dây tiện lợi, khoảng cách tối đa đến 10m|
Di chuyển mượt mà với độ phân giải lên đến 1600 DPI|
Trang bị 6 nút nhấn giúp nhanh chóng mọi thao tác',

N'Độ phân giải    800- 1200 - 1600 DPI|
Nút nhấn    6 nút gồm:+ Bi lăn+ Chuột trái/phải+ Nút chỉnh DBI+ Nút trở về/chuyển tiếp|
Tương thích    Windows, Linux|
Kết nối    USB Receiver|
Đèn LED    Không|
Hãng sản xuất    DAREU|
Cảm biến    Lên đến 1600DPI|
',
169000 ,1,5)

INSERT INTO product VALUES(40, N'Chuột Bluetooth Rapoo M160 Silent',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/1/_/1.3_5.png',

N'Kiểu dáng đơn giản, phù hợp cho người thuận cả 2 tay|
Độ phân giải 1300 DPI, đáp ứng nhiều nhu cầu sử dụng|
Kết nối Bluetooth 3.0, 4.0 và đầu thu USB 2.4 GHz mượt mà|
Dùng 1 viên pin AA, dễ tìm mua và thay thế',

N'Pin    Lên đến 9 tháng|
Tương thích    Windows, MacOS|
Cách kết nối    USB Receiver (Đầu thu USB), Bluetooth|
Độ dài dây / Khoảng cách kết nối    10m|
Hãng sản xuất    Rapoo|
Chất liệu    Nhựa|
Lõi pin    1 pin AA|
Cảm biến    1300 DPI|
',
300000 ,1,5)

INSERT INTO product VALUES(41,N'Chuột không dây Microsoft 1850',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_101.png',

N'Thiết kế nhỏ gọn, vừa vặn thoải mái trong tay để cuộn và điều hướng chính xác|
Trang bị cảm biến 1000 DPI hoạt động hiệu quả và di chuyển mượt mà|
Hỗ trợ kết nối với thiết bị thông qua đầu thu USB trong khoảng cách lên tới 10m|
Hoạt động bằng 1 viên Pin AA, thời lượng sử dụng lên đến 6 tháng',

N'Độ phân giải    1000 DPI|
Tương thích    MacOS, Windows|
Kết nối    USB Receiver|
Đèn LED    Không có|
Tiện ích    Lướt tốt trên mặt kính và gỗ|
Hãng sản xuất    Microsoft|
',
350000 ,1,5)

INSERT INTO product VALUES(42, N'Chuột có dây Rapoo N200',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/1/_/1.11_1_1.png',

N'Thiết kế nhỏ gọn, sử dụng thoải mái|
Độ phân giải lên đến 1600 DPI, giúp chuột di chuyển mượt mà|
Thiết kế phù hợp cho người thuận tay phải hay tay trái|
Thuận tiện điều khiển, nút bấm êm và nhạy',

N'Tương thích    Windows|
Cách kết nối    Dây cắm USB|
Độ dài dây / Khoảng cách kết nối    180 cm|
Đèn nền LED    Không|
Hãng sản xuất    Rapoo|
Chất liệu    Nhựa|
Cảm biến    1600 DPI|
',
100000 ,1,5)

INSERT INTO product VALUES(43, N'Chuột có dây Gaming MSI M99',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_102_1.png',

N'Sử dụng bền bỉ với độ thử nghiệm đến 10 triệu lần nhấn|
Độ phân giải 4000 DPI giúp di chuyển mượt mà, ổn định|
Trang bị đèn LED RGB với 16.8 triệu màu siêu ấn tượng|
Thiết kế nút cuộn 3D trống trượt, giúp bạn cuộn chính xác',

N'Độ phân giải    4000 DPI|
Cảm biến    Avago 3050|
Nút nhấn    8 nút có thể tùy chỉnh|
Tương thích    Windows|
Kết nối    Dây USB|
Đèn LED    RGB|
Tiện ích    Độ bền đến 10 triệu lần nhấn Thiết kế tản nhiệt tốt|
Tương thích    Windows|
Cách kết nối    Dây cắm USB|
Độ dài dây / Khoảng cách kết nối    1.8m|
Đèn nền LED    Có|
Hãng sản xuất    MSI|
',
500000 ,1,5)

INSERT INTO product VALUES(44, N'Chuột có dây Gaming Rapoo V280',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/1/_/1.2_1_6.png',

N'Thiết kế cân xứng, dễ dàng thao tác cho mọi game thủ, mang lại cảm giác thoải mái khi sử dụng lâu|
Trang bị cảm biến độ phân giải lên tới 7000DPI hoạt động hiệu quả và di chuyển mượt mà|
Dây USB kết nối dài 1.8m dễ dàng kết nối với thiết bị và sử dụng ổn định|
Tốc độ phản hồi nhanh chóng chỉ 1 ms (0.001 giây), xử lý nhanh và chính xác|
Trang bị đèn LED RGB hỗ trợ 16.8 triệu màu và có thể tùy chỉnh qua phần mềm|
Tích hợp cảm biến chạm tay làm thay đổi ánh sáng nền mang lại trải nghiệm thú vị',

N'Tương thích    Windows, MacOS|
Cách kết nối    Dây cắm USB|
Độ dài dây / Khoảng cách kết nối    1.8m|
Đèn nền LED    Có|
Hãng sản xuất    Rapoo|
Tính năng khác    DPI: Lên tới 7000 DPI. Nút chuyển đổi DPI nhanh trên chuột. LED RGB với 16 triệu màu. Cảm biến chạm tay làm thay đổi ánh sáng nền|
',
690000 ,1,5)
-- Tai nghe - Loa
INSERT INTO product VALUES(45,N'Tai nghe Devia Smart Series Dual Speakers 3,5mm',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/f/r/frame_28_1.png',

N'Thiết kế độc đáo với phần củ tai được đặt 2 màng loa bên trong|
Trang bị 2 driver 9mm mang đến chất âm mạnh mẽ và bùng nổ|
Cổng 3.5mm tương thích nhiều thiết bị: Smartphone, laptop, PC, vv|
Dây dẫn chất liệu nhựa PC chống rối, bền bỉ hạn chế bị đứt gãy',

N'Cổng kết nối    3.5mm|
Micro    Có|
Kích thước driver    9mm|
Phương thức điều khiển    Nút bấm trên dây|
Hãng sản xuất    Devia|
Chất liệu    Nhựa + Kim loại|
',
200000 ,1,6)

INSERT INTO product VALUES(46, N'Tai nghe chụp tai JBL Live 660NC',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_269_1.png',

N'Tính năng chống ồn chủ động ANC giúp loại bỏ mọi tạp âm môi trường xung quanh|
Kiểu dáng thời thượng, tai nghe được bọc vải Fabric tạo cảm giác thoải mái khi đeo|
Màng loa kích thước 40 mm mang đến chất lượng âm thanh sống động, chân thực|
Thời lượng pin ấn tượng khi có thể hoạt động đến 50 giờ liên tục chỉ với một lần sạc',

N'Thời lượng pin    50 giờ|
Công nghệ âm thanh    JBL Signature Sound, Chống ồn chủ động ANC|
Micro    Có|
Tương thích    Các thiết bị có hỗ trợ Bluetooth|
Tính năng khác    Chế độ môi trường xung quanh|
Kích thước driver    40mm|
Phương thức điều khiển    Nút bấm vật lý|
Hãng sản xuất    JBL|
Phạm vi kết nối    10 m|
',
4490000 ,1,6)

INSERT INTO product VALUES(47,N'Tai nghe không dây Havit TW959',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/f/r/frame_4_1_1.png',

N'Chuẩn Bluetooth 5.1 cho chất lượng kết nối ổn định, nhanh chóng|
Độ trễ thấp chỉ 65 ms mang đến trải nghiệm chơi game mượt mà|
Kháng nước, bụi chuẩn IPX4 giúp bảo vệ tai nghe khi bị dính nước|
Thiết kế chuẩn công thái học mang đến cảm giác thoải mái khi đeo',

N'Thời lượng pin    Tai nghe: 5.5 giờ. Hộp sạc: 20 giờ|
Chống nước    IPX4|
Micro    Có|
Tương thích    Các thiết bị có hỗ trợ Bluetooth|
Kích thước driver    10 mm|
Độ trễ    65 ms|
Phương thức điều khiển    Chạm cảm ứng|
Hãng sản xuất    Havit|
Phạm vi kết nối    10 m|
',
690000 ,1,6)

INSERT INTO product VALUES(48,N'Tai nghe KZ ZST',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_192_1__1_1.png',

N'Lõi dây mạ bạc giúp giúp giảm hiện tượng thất thoát, méo tiếng khi nghe|
Kiểu dáng nhỏ gọn ôm khít tai tạo cảm giác dễ chịu ngay cả khi đeo lâu|
Tích hợp phím bấm vật lý trên dây giúp thao tác nhanh chóng và tiện lợi|
Trang bị jack cắm 3.5 mm và chuẩn Bluetooth hỗ trợ chuyển đổi đa dạng',

N'Cổng kết nối    3.5mm|
Micro    Có|
Tương thích    Các thiết bị có hỗ trợ cổng 3.5 mm|
Tính năng khác    Dây đeo có thể tháo rời|
Kích thước driver    10 mm|
Phương thức điều khiển    Nút bấm vật lý|
Hãng sản xuất    KZ|
',
550000 ,1,6)

INSERT INTO product VALUES(49,N'Tai nghe chụp tai Sony WH-1000XM5',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_172_2.png',

N'Công nghệ Auto NC Optimizer tự động khử tiếng ồn dựa theo môi trường|
Trải nghiệm nghe chân thật, sống động nhờ tính năng 360 Reality Audio|
Thiết kế sang trọng, trọng lượng nhẹ với phần da mềm mại, ôm khít đầu|
Năng lượng cho cả ngày dài với thời lượng sử dụng pin lên đến 40 giờ',

N'Thời lượng pin    Tắt chống ồn ANC: Dùng 40 giờ. Bật chống ồn ANC: Dùng 30 giờ|
Công nghệ âm thanh    Auto NC Optimizer (Tự động điều chỉnh chống ồn). Âm thanh 360 độ|
Micro    Có|
Tương thích    Các thiết bị có hỗ trợ Bluetooth|
Tính năng khác    Tự động dừng nhạc khi bắt đầu trò chuyện. Điều khiển bằng giọng nói thông qua Google, Alexa|
Kích thước driver    30mm|
Phương thức điều khiển    Cảm ứng chạm|
Hãng sản xuất    Sony|
Phạm vi kết nối    10 m|
',
9490000 ,1,6)

INSERT INTO product VALUES(50,N'Tai nghe Bluetooth Havit TW945',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_196_1_.png',

N'Thiết kế nhỏ gọn, đột phá với tông màu trong suốt|
Trang bị Bluetooth 5.3, kết nối nhanh chóng, ổn định|
Gaming Mode 0.05s, mang đến trải nghiệm tuyệt vời|
Sử dụng driver 13mm cho âm bass sâu và nội lực',

N'Thời lượng pin    Tai nghe: 3 giờ. Hộp sạc: 30 giờ|
Công nghệ âm thanh    Gaming Mode 0.05s|
Micro    Có|
Tương thích    Các thiết bị có hỗ trợ Bluetooth|
Kích thước driver    13mm|
Phương thức điều khiển    Chạm cảm ứng|
Hãng sản xuất    Havit|
',
450000 ,1,6)

INSERT INTO product VALUES(51,N'Tai nghe chụp tai Robot RH-G20',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/f/r/frame_1_3_7.png',

N'Trang bị micro có thể điều chỉnh linh hoạt giúp thu âm, đàm thoại tốt hơn|
Cổng kết nối 3.5mm tương thích nhiều thiết bị như điện thoại, laptop, PC, vv|
Đèn LED có khả năng chuyển đổi 7 màu sắc, chuẩn phong cách gaming|
Thiết kế gọng kim loại dẻo ôm khít vào tai và dây dẫn bọc dù chắc chắn',

N'Cổng kết nối    3.5mm|
Micro    Có|
Tương thích    PC, Laptop, Smartphone, Máy chơi game|
Kích thước driver    50mm|
Phương thức điều khiển    Nút bấm vật lý|
Hãng sản xuất    Robot|
Chất liệu    Gọng kim loại dẻo Dây dẫn bọc dù|
Công suất    30mW|
',
390000 ,1,6)

INSERT INTO product VALUES(52,N'Tai nghe không dây Nokia E3100',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/f/r/frame_7_1_1.png',

N'Thiết kế trẻ trung, siêu cuốn hút với đa dạng lựa chọn màu sắc nổi bật|
Tận hưởng chất lượng âm thanh tốt với màng loa có kích thước 6 mm|
Chuẩn kháng nước IPX3 giúp đảm bảo an toàn với những tia nước bắn|
Trang bị công nghệ Bluetooth 5.0 cho phép kết nối nhanh chóng, ổn định',

N'Thời lượng pin    Tai nghe: Dùng 2.5 giờHộp sạc: Dùng 10 giờ|
Chống nước    IPX3|
Micro    Có|
Tương thích    Các thiết bị có hỗ trợ Bluetooth|
Tính năng khác    Tự động kết nối khi mở hộp sạc|
Kích thước driver    6 mm|
Phương thức điều khiển    Nút điều khiển vật lý|
Hãng sản xuất    Nokia|
',
590000 ,1,6)

INSERT INTO product VALUES(53,N'Tai nghe không dây Soundpeats H2',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_198_2_1.png',

N'Đàm thoại rõ ràng với 2 micro mỗi bên tai cùng với công nghệ khử ồn cVc 8.0|
Chất lượng âm thanh tiên tiến độc đáo với khả năng tái tạo nhịp điệu nổi bật|
Thời lượng pin tốt có thể hoạt động đến 20 giờ liên tục khi dùng kèm hộp sạc|
Độ trễ thấp chỉ 60ms cho bạn chơi game thỏa thích mà không bị gián đoạn',

N'Thời lượng pin    Tai nghe: 5 giờHộp sạc: 20 giờ|
Chống nước    IPX4|
Micro    Có|
Tương thích    Các thiết bị có hỗ trợ Bluetooth|
Tính năng khác    Tự động kết nối khi mở hộp|
Kích thước driver    8 mm|
Độ trễ    60 ms (Chế độ Game mode)|
Phương thức điều khiển    Chạm cảm ứng|
Hãng sản xuất    Soundpeats|
Phạm vi kết nối    10 m|
',
1690000 ,1,6)

INSERT INTO product VALUES(54,N'Tai nghe chụp tai Gaming Sony Inzone H7',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/s/o/sonyyy.jpg',

N'Công nghệ âm thanh 360 Spatial Sound - cho phép phát hiện vị trí của đối thủ, nghe rõ từng tiếng bước chân|
Âm trầm mạnh mẽ - Tái hiện chân thật tứng tiếng nổ và tiếng động cơ|
Cảm giác đeo thoải mái - Đệm tai ôm sát tai|
Siêu bền bỉ - Sử dụng lên đến 40 giờ chỉ với 1 lần sạc|
Kết nối không dây thông qua bluetooth hay kết nối không dây USB ',

N'Thời lượng pin    Tối đa 40 giờ|
Công nghệ âm thanh    360 Spatial Sound|
Micro    Micro boom, tập trung vào giọng nói|
Tính năng khác    Có thể vừa chơi vừa sạc  Cho phép tinh chỉnh cá nhân hóa nội dung game  Có thể điều chỉnh cài đặt tai nghe trong trung tâm điều khiển PS5|
Kích thước driver    40 mm, loại vòm (Cuộn dây âm thanh EDCCA)|
Độ trễ    Độ trễ thấp với kết nối không dây 2.4 GHz|
Pin    Pin sạc lithium-ion tích hợp|
Hãng sản xuất    Sony|
',
5300000 ,1,6)
-- Linh kiện PC
INSERT INTO product VALUES(55,N'VGA Radeon RX 6400 Aero ITX 4G',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_311_2_.png',
'',
N'Chipset    AMD|
Dung lượng    4GB|
Kiểu bộ nhớ    GDDR6|
Giao diện bộ nhớ    64-bit|
GPU clock    2039 MHz|
Giao tiếp PCI    PCI Express 4.0 x4|
Số lượng đơn vị xử lí    768 CUDA|
Nguồn đề xuất    350W|
Hãng sản xuất    MSI|
',
4990000 ,1,7)

INSERT INTO product VALUES(56,N'Ổ cứng SSD Kimtigo 128GB 2.5 inch SATA K128S3A25KTA320',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/1/5/15_3_106.jpg',
'',
N'Loại ổ cứng    SSD|
Kết nối    SATA III|
Dung lượng    128GB|
Tốc độ đọc    530 MB/s|
Hãng sản xuất    KIMTIGO|
',
590000 ,1,7)

INSERT INTO product VALUES(57,N'VGA Gigabyte GeForce RTX 2060 6G',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/5/_/5.1_5.png',
'',
N'Chipset    NVIDIA|
Dung lượng    6GB|
Kiểu bộ nhớ    GDDR6|
Giao diện bộ nhớ    192 bit|
GPU    NVIDIA RTX 2060|
GPU clock    1680 MHz|
Giao tiếp PCI    16x PCI-E 3.0|
Số lượng đơn vị xử lí    1920 CUDA|
Đầu cấp nguồn    8 Pin*1|
Nguồn đề xuất    500W|
Hãng sản xuất    Gigabyte|
Cổng giao tiếp    3x DisplayPort 1.4 1x HDMI 2.0b|
',
10190000 ,1,7)

INSERT INTO product VALUES(58,N'Ổ cứng SSD Kingston SA400S37 480GB',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/o/-/o-cung-ssd-kingston-sa400-s37-480gb-5.jpg',
'',
N'Bộ nhớ trong    480 GB|
Loại ổ cứng    SSD|
Kết nối    SATA III|
Tốc độ đọc    500 MB/s|
Hãng sản xuất    Kingston|
',
1510000 ,1,7)

INSERT INTO product VALUES(59,N'Ổ cứng HDD WD Blue 1TB WD10EZEX',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/t/_/t_i_xu_ng_-_2023-01-28t224540.685.png',
'',
N'Loại ổ cứng    HDD|
Kết nối    SATA III|
Dung lượng    1TB|
Tốc độ vòng quay    7200 RPM|
Bộ nhớ đệm    64MB|
Hãng sản xuất    Western Digital|
',
1290000 ,1,7)

INSERT INTO product VALUES(60,N'Ổ cứng HDD Seagate Barracuda 1TB 64MB ST1000DM010',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/o/-/o-cung-hdd-seagate-barracuda-1tb-64mb-st1000dm010-1.jpg',
'',
N'Loại ổ cứng    HDD|
Kết nối    SATA III|
Dung lượng    1TB|
Tốc độ đọc    ~ 210 MB/s|
Hãng sản xuất    Seagate|
',
1190000 ,1,7)

INSERT INTO product VALUES(61,N'Ổ cứng SSD Gigabyte 120GB SATA III',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/1/6/16_1_10.jpg',
'',
N'Loại ổ cứng    SSD|
Kết nối    SATA III|
Dung lượng    120GB|
Tốc độ đọc    500 MBps|
Hãng sản xuất    Gigabyte|
',
590000 ,1,7)

INSERT INTO product VALUES(62,N'RAM PC G.SKILL Aegis 8GB 3200MHz DDR4',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/r/a/ram-gskill-aegis-8gb-3200mhz-ddr4-f4-3200c16s-8gis-2.jpg',
'',
N'Dung lượng    8GB|
Bus RAM    3200MHz|
Hỗ trợ    DIMM (PC, Desktop)|
Timing    CL16-18-18-38|
Voltage    1.2V|
Hãng sản xuất    G.SKILL|
',
990000 ,1,7)

INSERT INTO product VALUES(63,N'RAM Laptop G.SKILL Ripjaw 8GB 3200MHz DDR4',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/2/_/2_64_29.jpg',
'',
N'Dung lượng    8GB|
Bus RAM    3200MHz|
Hỗ trợ    SO-DIMM (Laptop)|
Voltage    1.2 V|
Hãng sản xuất    G.SKILL|
',
1190000 ,1,7)

INSERT INTO product VALUES(64,N'RAM Laptop Lexar 16GB 3200MHZ DDR4',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/g/r/group_303_1_.png',
'',
N'Dung lượng    16GB|
Bus RAM    3200MHz|
Hỗ trợ    SO-DIMM (Laptop)|
Voltage    1.2V|
Hãng sản xuất    LEXAR|
',
1250000 ,1,7)
-- Apple
INSERT INTO product VALUES(65,N'Mac mini M2 2023 (8 CPU - 10 GPU - 8GB - 256GB) | Chính hãng Apple Việt Nam',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/m/2/m2_256gb.png',

N'Hiệu năng vượt trội với con chip M2 cùng 10 nhân GPU - Chạy tốt các tác vụ như render video, chỉnh sửa hình ảnh|
Đa nhiệm tốt, mở cùng lúc nhiều ứng dụng với dung lượng ram lên đến 8GBSSD 256GB giúp máy khởi động nhanh chóng, cho phép người dùng lưu trữ nhiều dữ liệu|
Thiết kế nhỏ gọn cùng trọng lượng chỉ 1.18kg - dễ dàng di chuyển mọi lúc mọi nơi|
Hệ điều hành MacOS Ventura mang đến khả năng bảo mật vượt trội, bảo vệ mọi dữ liẹu của người dùng',

N'Loại card đồ họa    10 nhận GPU, 16 nhân Neural Engine|
Dung lượng RAM    8GB|
Loại RAM    8GB|
Ổ cứng    256GB SSD|
Công nghệ màn hình    Hỗ trợ xuất hình ảnh 6K 60HZ qua cổng Thunderbolt. Xuất hình ảnh 4K qua cổng HDMI|
Cổng giao tiếp    Jack 3.5mm, cổng HDMI, 2 cổng USB-A, Cổng Ethernet|
',
15990000 ,1,8)

INSERT INTO product VALUES(66,N'MacBook Pro 16 inch M2 Max 2023 (12 CPU - 38 GPU - 32GB - 1TB)',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/1/6/16_m2_max.png',

N'Chip M2 Max - Hoạt động mượt mà từ các ứng dụng đến các tác vụ render video 4K, 8K32GB ram - Vừa render video, vừa mở cùng lúc nhiều tab trình duyệt mà không lo giật lag|
Âm thanh hình ảnh chân thật - Màn hình 120Hz cùng công nghệ Dolby Atmos|
Thiết kế thời thượng củng phần vỏ kim loại chắc chắn|
Bảo mật tuyệt đối với tính năng bảo mật vân tay',

N'Loại card đồ họa    38 nhân GPU|
Dung lượng RAM    32GB|
Số khe ram    Không hỗ trợ nâng cấp|
Ổ cứng    1TB SSD|
Kích thước màn hình    16 inches|
Công nghệ màn hình    Tần số quét 120Hz. Công nghệ Liquid Retina, Mini LED, XDR1 tỳ màu|
Hệ điều hành    MacOS|
Độ phân giải màn hình    3456 x 2234 pixels|
Cổng giao tiếp    1x Headphone jack  1x MagSafe port  1x HDMI  1x Thunderbolt 4  1x SDXC|
',
92990000 ,1,8)

INSERT INTO product VALUES(67,N'Apple MacBook Air M1 256GB 2020 I Chính hãng Apple Việt Nam',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/a/i/air_m2.png',

N'Phù hợp cho lập trình viên, thiết kế đồ họa 2D, dân văn phòng|
Hiệu năng vượt trội - Cân mọi tác vụ từ word, exel đến chỉnh sửa ảnh trên các phần mềm như AI, PTS|
Đa nhiệm mượt mà - Ram 8GB cho phép vừa mở trình duyệt để tra cứu thông tin, vừa làm việc trên phần mềm|
Trang bị SSD 256GB - Cho thời gian khởi động nhanh chóng, tối ưu hoá thời gian load ứng dụng|
Chất lượng hình ảnh sắc nét - Màn hình Retina cao cấp cùng công nghệ TrueTone cân bằng màu sắc|
Thiết kế sang trọng - Nặng chỉ 1.29KG, độ dày 16.1mm. Tiện lợi mang theo mọi nơi',

N'Loại card đồ họa    GPU 7 nhân, 16 nhân Neural Engine|
Dung lượng RAM    8GB|
Loại RAM    LPDDR4|
Ổ cứng    256GB SSD|
Kích thước màn hình    13.3 inches|
Công nghệ màn hình    Độ sáng 400 nits Hỗ trợ dải màu P3 True-Tone|
Pin    49.9-watt lithium-polymer, củ sạc công suất 30W|
Hệ điều hành    macOS Big Sur|
Độ phân giải màn hình    2560 x 1600 pixels (2K)|
Cổng giao tiếp    2 cổng Thunderbolt / USB 4|
Tính năng khác    Cảm biến vân tay Touch ID, 720p FaceTime HD camera|
',
28990000 ,1,8)

INSERT INTO product VALUES(68,N'MacBook Pro 14 inch M2 Pro 2023 (10 CPU - 16 GPU - 32GB - 512GB)',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/m/b/mbp14-silver-select-202301_1_4_1.jpg',

N'Thiết kế sang trọng - Vỏ kim loại cùng trọng lượng chỉ 1.6kg dễ dàng mang theo mọi nơi|
Hiển thị chân thật - Kích thước màn hình 14 inch cùng tần số quét lên đến 120Hz|
Cấu hình mạnh mẽ - Ram 32GB cùng SSD 512 GB đa nhiệm mượt mà, tránh tình trạng giật lag|
Xử lý tốt các tác vụ đồ hoạ - GPU 19 nhân giúp việc render video hay chỉnh sửa ảnh diễn ra một cách nhẹ nhàng',

N'Loại card đồ họa    16 nhân GPU|
Dung lượng RAM    32GB|
Ổ cứng    512GB SSD|
Kích thước màn hình    14.2 inches|
Công nghệ màn hình    120Hz, Liquid Retina, Mini LED, XDR|
Pin    70Wh, Đi kèm sạc 67W|
Hệ điều hành    MacOS|
Độ phân giải màn hình    3024 x 1964 pixels|
Cổng giao tiếp    1 x 3.5mm Cổng MagSafe 1x HDMI  1x SDXC|
',
63990000 ,1,8)

INSERT INTO product VALUES(69,N'Apple MacBook Pro 13 M2 2022 8GB 512GB I Chính hãng Apple Việt Nam',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/m/a/macbook13.png',

N'Vẻ ngoài sang trọng, siêu mỏng nhẹ - trọng lượng chỉ 1.4kg|
Chip Apple M2 chạy mượt các úng dụng đồ hoạ như Phototshop, Premier hay các phần mềm văn phòng Word, Exel, Powerpoint|
Ram 8GB - Đa nhiệm mượt mà, mở cùng lúc nhiều ứng dụng hay tab trình duyệt mà không lo giật lag|
Màn hình Retina cùng các công nghệ như True Tone, Wide Color - Màu sắc hiển thị sắc nét, trải nghiệm giải trí tuyệt vời hơn bao giờ hết|
Tích hợp touch bar - Các thao tác nhu chụp màn hình, chỉnh âm lượng trở nên dễ dàng hơn|
Webcam 720p - Sẵn sàng tham gia các buổi họp trực tuyến hay họp online',

N'Loại card đồ họa    10 nhân GPU|
Dung lượng RAM    8GB|
Loại RAM    8GB|
Ổ cứng    512GB SSD|
Kích thước màn hình    13 inches|
Pin    58.2Whrs|
Hệ điều hành    MacOS|
Cổng giao tiếp    Cổng sạc, Cổng màn hình Thunderbolt 3 (lên đến 40Gb/s)USB 4 (lên đến 40Gb/s) USB 3.1 Gen 2 (lên đến 10Gb/s) Cổng 3.5mm|
',
41990000 ,1,8)

INSERT INTO product VALUES(70,N'Apple Macbook Air M2 2022 24GB 256GB I Chính hãng Apple Việt Nam',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/m/a/macbook_air_m2_1.png',

N'Thiết kế sang trọng, lịch lãm - siêu mỏng 11.3mm, chỉ 1.24kg|
Hiệu năng hàng đầu - Chip Apple m2, 8 nhân GPU, hỗ trợ tốt các phần mềm như Word, Axel, Adoble Premier|
Ram lên đến 24GB - Tốc độ khởi động ứng dụng nhanh chóng, cho phép chạy máy ảo mở cùng lúc nhiều ứng dụng|
Màn hình sắc nét - Độ phân giải 2560 x 1664 cùng độ sáng 500 nits|
Âm thanh sống động - 4 loa tramg bị công nghệ dolby atmos và âm thanh đa chiều',

N'Loại card đồ họa    8 nhân GPU, 16 nhân Neural Engine|
Dung lượng RAM    24GB|
Ổ cứng    256GB|
Kích thước màn hình    13.6 inches|
Công nghệ màn hình    Liquid Retina Display|
Pin    52,6 Wh|
Hệ điều hành    MacOS|
Độ phân giải màn hình    2560 x 1664 pixels|
Cổng giao tiếp    2 x Thunderbolt 3, Jack tai nghe 3.5 mm MagSafe 3|
',
45990000 ,1,8)

INSERT INTO product VALUES(71,N'Apple Macbook Pro 13 M2 2022  8GB 256GB I Chính hãng Apple Việt Nam',
'https://cdn2.cellphones.com.vn/x358,webp,q100/media/catalog/product/p/r/pro-m2.jpg',
N'Chip M2 mới nhất - hiệu năng hàng đầu, thoải mái sử dụng các phần mềm đồ hoạ hay render video|
Màn hình Retina - màu sắc hiển thị sống động tạo ra không gian giải trí đỉnh cao|
Thiết kế sang trọng - Trọng lượng máy chỉ 1.4kg, độ dày chỉ 15.6mm giúp bạn dễ dàng mang theo|
Âm thanh chân thật - Tích hợp loa kép cùng công nghệ Dolby Atmos mang đến chất lượng âm thanh tuyệt vời',

N'Loại card đồ họa    10 nhân GPU|
Dung lượng RAM    8GB|
Loại RAM    8GB|
Ổ cứng    256GB SSD|
Kích thước màn hình    13 inches|
Pin    58.2Whrs|
Hệ điều hành    MacOS|
Độ phân giải màn hình    2560 x 1664 pixels|
Cổng giao tiếp    Cổng sạc Cổng màn hình Thunderbolt 3 (lên đến 40Gb/s)USB 4 (lên đến 40Gb/s) USB 3.1 Gen 2 (lên đến 10Gb/s) Cổng 3.5mm|
',
35990000 ,1,8)


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
	rate SMALLINT, 
	comment NVARCHAR(1000), 
	CONSTRAINT fk_item_order FOREIGN KEY (order_id) REFERENCES orders(id),
	CONSTRAINT fk_item_product FOREIGN KEY (product_id) REFERENCES product(id),
	CONSTRAINT pk_item PRIMARY KEY (id)
)
