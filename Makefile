run_all_in_parallel:
	make --output-sync clean_it test_base_configs

clean_it:
	mvn clean

test_base_configs:
	make -j test_chrome_68_w10 test_firefox_61_w10 test_edge_17_w10 test_ie_9_w7 test_ie_10_w8 test_ie_11_w8 test_safari_11_osxhs

test_chrome_74_w10_fullTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=chrome -Dbrowser_version=74.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_chrome_74_w81_fullTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=8.1 -Dbrowser=chrome -Dbrowser_version=74.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_chrome_73_w8_fullTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=8 -Dbrowser=chrome -Dbrowser_version=73.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_chrome_74_w7_fullTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=7 -Dbrowser=chrome -Dbrowser_version=74.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_chrome_74_osxmoj_fullTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Mojave -Dbrowser=chrome -Dbrowser_version=74.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_chrome_73_osxmoj_fullTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Mojave -Dbrowser=chrome -Dbrowser_version=73.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_chrome_74_osxhs_fullTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version="High Sierra" -Dbrowser=chrome -Dbrowser_version=74.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_chrome_74_osxs_fullTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Sierra -Dbrowser=chrome -Dbrowser_version=74.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_firefox_66_w10_fullTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=Firefox -Dbrowser_version=66.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_firefox_65_w81_fullTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=8.1 -Dbrowser=Firefox -Dbrowser_version=65.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_firefox_66_w7_fullTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=7 -Dbrowser=Firefox -Dbrowser_version=66.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_firefox_66_osxmoj_fullTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Mojave -Dbrowser=Firefox -Dbrowser_version=66.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_firefox_65_osxhs_fullTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version="High Sierra" -Dbrowser=Firefox -Dbrowser_version=65.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_firefox_66_osxs_fullTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Sierra -Dbrowser=Firefox -Dbrowser_version=66.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_ie_11_w10_fullTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=IE -Dbrowser_version=11.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_ie_10_w8_fullTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=8 -Dbrowser=IE -Dbrowser_version=10.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_edge_18_w10_fullTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=Edge -Dbrowser_version=18.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_edge_17_w10_fullTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=Edge -Dbrowser_version=17.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_safari_12_0_osxmoj:_fullTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Mojave -Dbrowser=Safari -Dbrowser_version=12.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_safari_11_1_osxhs_fullTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version="High Sierra" -Dbrowser=Safari -Dbrowser_version=11.1 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_safari_10_1_osxs_fullTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Sierra -Dbrowser=Safari -Dbrowser_version=10.1 -Dresolution=1920x1080 -Dcucumber.options="--tags @fullTest"

test_galaxyNote9_8_1_fullTest:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy Note 9" -Dos_version=8.1 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_galaxyS9_8_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy S9" -Dos_version=8.0 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_galaxyS9Plus_9_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy S9 Plus" -Dos_version=9.0 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_galaxyS8_7_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy S8" -Dos_version=7.0 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_galaxyS7_6_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy S7" -Dos_version=6.0 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_galaxyTabS3_7_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy Tab S3" -Dos_version=7.0 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_Pixel3_9_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="Google Pixel 3" -Dos_version=9.0 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_Pixel_8_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="Google Pixel" -Dos_version=8.0 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_Pixel_7_1_fullTest:
	mvn install -DLOCAL=true -Ddevice="Google Pixel" -Dos_version=7.1 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_Nexus6_6_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="Google Nexus 6" -Dos_version=6.0 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_Nexus9_5_1_fullTest:
	mvn install -DLOCAL=true -Ddevice="Google Nexus 9" -Dos_version=5.1 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_Nexus5_4_4_fullTest:
	mvn install -DLOCAL=true -Ddevice="Google Nexus 5" -Dos_version=4.4 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_iPhoneXS_12_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="iPhone XS" -Dos_version=12 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_iPhoneXS_Max_12_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="iPhone XS Max" -Dos_version=12 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_iPhoneXR_12_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="iPhone XR" -Dos_version=12 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_iPhoneX_11_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="iPhone X" -Dos_version=11 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_iPhone8_11_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="iPhone 8" -Dos_version=11 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_iPhoneSE_11_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="iPhone SE" -Dos_version=11 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_iPadPro12_12_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="iPad Pro 12.9 2018" -Dos_version=12 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_iPadMini4_11_0_fullTest:
	mvn install -DLOCAL=true -Ddevice="iPad Mini 4" -Dos_version=11 -Dreal_mobile=true -Dcucumber.options="--tags @fullTest"

test_chrome_74_w10_smokeTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=chrome -Dbrowser_version=74.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_chrome_74_w81_smokeTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=8.1 -Dbrowser=chrome -Dbrowser_version=74.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_chrome_73_w8_smokeTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=8 -Dbrowser=chrome -Dbrowser_version=73.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_chrome_74_w7_smokeTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=7 -Dbrowser=chrome -Dbrowser_version=74.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_chrome_74_osxmoj_smokeTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Mojave -Dbrowser=chrome -Dbrowser_version=74.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_chrome_73_osxmoj_smokeTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Mojave -Dbrowser=chrome -Dbrowser_version=73.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_chrome_74_osxhs_smokeTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version="High Sierra" -Dbrowser=chrome -Dbrowser_version=74.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_chrome_74_osxs_smokeTestt:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Sierra -Dbrowser=chrome -Dbrowser_version=74.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_firefox_66_w10_smokeTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=Firefox -Dbrowser_version=66.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_firefox_65_w81_smokeTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=8.1 -Dbrowser=Firefox -Dbrowser_version=65.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_firefox_66_w7_smokeTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=7 -Dbrowser=Firefox -Dbrowser_version=66.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_firefox_66_osxmoj_smokeTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Mojave -Dbrowser=Firefox -Dbrowser_version=66.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_firefox_65_osxhs_smokeTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version="High Sierra" -Dbrowser=Firefox -Dbrowser_version=65.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_firefox_66_osxs_smokeTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Sierra -Dbrowser=Firefox -Dbrowser_version=66.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_ie_11_w10_smokeTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=IE -Dbrowser_version=11.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_ie_10_w8_smokeTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=8 -Dbrowser=IE -Dbrowser_version=10.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_edge_18_w10_smokeTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=Edge -Dbrowser_version=18.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_edge_17_w10_smokeTest:
	mvn install -DLOCAL=true -Dos=Windows -Dos_version=10 -Dbrowser=Edge -Dbrowser_version=17.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_safari_12_0_osxmoj:_smokeTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Mojave -Dbrowser=Safari -Dbrowser_version=12.0 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_safari_11_1_osxhs_fullTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version="High Sierra" -Dbrowser=Safari -Dbrowser_version=11.1 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_safari_10_1_osxs_smokeTest:
	mvn install -DLOCAL=true -Dos="OS X" -Dos_version=Sierra -Dbrowser=Safari -Dbrowser_version=10.1 -Dresolution=1920x1080 -Dcucumber.options="--tags @smokeTest"

test_galaxyNote9_8_1_smokeTest:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy Note 9" -Dos_version=8.1 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_galaxyS9_8_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy S9" -Dos_version=8.0 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_galaxyS9Plus_9_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy S9 Plus" -Dos_version=9.0 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_galaxyS8_7_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy S8" -Dos_version=7.0 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_galaxyS7_6_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy S7" -Dos_version=6.0 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_galaxyTabS3_7_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="Samsung Galaxy Tab S3" -Dos_version=7.0 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_Pixel3_9_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="Google Pixel 3" -Dos_version=9.0 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_Pixel_8_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="Google Pixel" -Dos_version=8.0 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_Pixel_7_1_smokeTest:
	mvn install -DLOCAL=true -Ddevice="Google Pixel" -Dos_version=7.1 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_Nexus6_6_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="Google Nexus 6" -Dos_version=6.0 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_Nexus9_5_1_smokeTest:
	mvn install -DLOCAL=true -Ddevice="Google Nexus 9" -Dos_version=5.1 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_Nexus5_4_4_smokeTest:
	mvn install -DLOCAL=true -Ddevice="Google Nexus 5" -Dos_version=4.4 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_iPhoneXS_12_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="iPhone XS" -Dos_version=12 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_iPhoneXS_Max_12_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="iPhone XS Max" -Dos_version=12 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_iPhoneXR_12_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="iPhone XR" -Dos_version=12 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_iPhoneX_11_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="iPhone X" -Dos_version=11 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_iPhone8_11_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="iPhone 8" -Dos_version=11 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_iPhoneSE_11_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="iPhone SE" -Dos_version=11 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_iPadPro12_12_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="iPad Pro 12.9 2018" -Dos_version=12 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

test_iPadMini4_11_0_smokeTest:
	mvn install -DLOCAL=true -Ddevice="iPad Mini 4" -Dos_version=11 -Dreal_mobile=true -Dcucumber.options="--tags @smokeTest"

run_wiremock:
	cd src/main/resources && java -jar wiremock-standalone-2.21.0.jar -https-port 8443 -verbose
