run_all_in_parallel:
	make --output-sync clean_it test_base_configs

clean_it:
	mvn clean

test_base_configs:
	make -j test_chrome_72_win10 test_chrome_72_w81

test_chrome_72_w10:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=chrome -Dbrowser_version=72.0 -Dresolution=1920x1080

test_chrome_71_w10:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=chrome -Dbrowser_version=71.0 -Dresolution=1920x1080

test_chrome_72_w81:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=8.1 -Dbrowser=chrome -Dbrowser_version=72.0 -Dresolution=1920x1080

test_chrome_71_w8:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=8 -Dbrowser=chrome -Dbrowser_version=71.0 -Dresolution=1920x1080

test_chrome_70_w7:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=7 -Dbrowser=chrome -Dbrowser_version=70.0 -Dresolution=1920x1080

test_chrome_49_wXP:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=XP -Dbrowser=chrome -Dbrowser_version=49.0 -Dresolution=1920x1080

test_chrome_72_osxmoj:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Mojave -Dbrowser=chrome -Dbrowser_version=72.0 -Dresolution=1920x1080

test_chrome_71_osxmoj:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Mojave -Dbrowser=chrome -Dbrowser_version=71.0 -Dresolution=1920x1080

test_chrome_72_osxhs:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version="High Sierra" -Dbrowser=chrome -Dbrowser_version=72.0 -Dresolution=1920x1080

test_chrome_71_osxs:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Sierra -Dbrowser=chrome -Dbrowser_version=71.0 -Dresolution=1920x1080

test_firefox_65_w10:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=Firefox -Dbrowser_version=65.0 -Dresolution=1920x1080

test_firefox_64_w81:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=8.1 -Dbrowser=Firefox -Dbrowser_version=64.0 -Dresolution=1920x1080

test_firefox_65_w7:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=7 -Dbrowser=Firefox -Dbrowser_version=65.0 -Dresolution=1920x1080

test_firefox_65_osxmoj:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Mojave -Dbrowser=Firefox -Dbrowser_version=65.0 -Dresolution=1920x1080

test_firefox_65_osxhs:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version="High Sierra" -Dbrowser=Firefox -Dbrowser_version=65.0 -Dresolution=1920x1080

test_firefox_64_osxs:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Sierra -Dbrowser=Firefox -Dbrowser_version=64.0 -Dresolution=1920x1080

test_ie_11_w10:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=IE -Dbrowser_version=11.0 -Dresolution=1920x1080

test_ie_10_w8:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=8 -Dbrowser=IE -Dbrowser_version=10.0 -Dresolution=1920x1080

test_ie_11_w7:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=7 -Dbrowser=IE -Dbrowser_version=11.0 -Dresolution=1920x1080

test_edge_18_w10:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=Edge -Dbrowser_version=18.0 -Dresolution=1920x1080

test_edge_17_w10:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=Edge -Dbrowser_version=17.0 -Dresolution=1920x1080

test_safari_12_0_osxmoj:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Mojave -Dbrowser=Safari -Dbrowser_version=12.0 -Dresolution=1920x1080

test_safari_11_1_osxhs:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version="High Sierra" -Dbrowser=Safari -Dbrowser_version=11.1 -Dresolution=1920x1080

test_safari_10_1_osxs:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Sierra -Dbrowser=Safari -Dbrowser_version=10.1 -Dresolution=1920x1080

test_galaxyNote9_8_1:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy Note 9" -Dos_version=8.1 -Dreal_mobile=true

test_galaxyS9_8_0:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy S9" -Dos_version=8.0 -Dreal_mobile=true

test_galaxyS9Plus_8_0:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy S9 Plus" -Dos_version=8.0 -Dreal_mobile=true

test_galaxyS8_7_0:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy S8" -Dos_version=7.0 -Dreal_mobile=true

test_galaxyS7_6_0:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy S7" -Dos_version=6.0 -Dreal_mobile=true

test_galaxyTabS3_7_0:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy Tab S3" -Dos_version=7.0 -Dreal_mobile=true

test_Pixel3_9_0:
	mvn install -DLOCAL=true -Ddevice="Google Pixel 3" -Dos_version=9.0 -Dreal_mobile=true

test_Pixel_8_0:
	mvn install -DLOCAL=true -Ddevice="Google Pixel" -Dos_version=8.0 -Dreal_mobile=true

test_Pixel_7_1:
	mvn install -DLOCAL=true -Ddevice="Google Pixel" -Dos_version=7.1 -Dreal_mobile=true

test_Nexus6_6_0:
	mvn install -DLOCAL=true -Ddevice="Google Nexus 6" -Dos_version=6.0 -Dreal_mobile=true

test_Nexus9_5_1:
	mvn install -DLOCAL=true -Ddevice="Google Nexus 9" -Dos_version=5.1 -Dreal_mobile=true

test_Nexus5_4_4:
	mvn install -DLOCAL=true -Ddevice="Google Nexus 5" -Dos_version=4.4 -Dreal_mobile=true

test_iPhoneXS_12_0:
	mvn install -DLOCAL=true -Ddevice="iPhone XS" -Dos_version=12 -Dreal_mobile=true

test_iPhoneX_11_0:
	mvn install -DLOCAL=true -Ddevice="iPhone X" -Dos_version=11 -Dreal_mobile=true

test_iPhone8_11_0:
	mvn install -DLOCAL=true -Ddevice="iPhone 8" -Dos_version=11 -Dreal_mobile=true

test_iPhoneSE_11_0:
	mvn install -DLOCAL=true -Ddevice="iPhone SE" -Dos_version=11 -Dreal_mobile=true

test_iPadPro12_12_0:
	mvn install -DLOCAL=true -Ddevice="iPad Pro 12.9 2018" -Dos_version=12 -Dreal_mobile=true

test_iPadMini4_11_0:
	mvn install -DLOCAL=true -Ddevice="iPad Mini 4" -Dos_version=11 -Dreal_mobile=true

run_wiremock:
	cd src/main/resources && java -jar wiremock-standalone-2.21.0.jar -port 8760 -verbose