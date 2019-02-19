run_all_in_parallel:
	make clean_it test_base_configs

clean_it:
	mvn clean

test_base_configs:
	make -j 10 test_chrome_68_w10 test_firefox_61_w10 test_edge_17_w10 test_edge_17_w10 test_ie_9_w7 test_ie_10_w8 test_ie_11_w8 test_safari_11_osxhs

test_chrome_68_w10:
	mvn install -Dos=Windows -Dos_version=10 -Dbrowser=chrome -Dbrowser_version=68.0 -Dresolution=1920x1080

test_firefox_61_w10:
	mvn install -Dos=Windows -Dos_version=10 -Dbrowser=Firefox -Dbrowser_version=61.0 -Dresolution=1920x1080

test_edge_17_w10:
	mvn install -Dos=Windows -Dos_version=10 -Dbrowser=Edge -Dbrowser_version=17.0 -Dresolution=1920x1080

test_ie_9_w7:
	mvn install -Dos=Windows -Dos_version=7 -Dbrowser=IE -Dbrowser_version=9.0 -Dresolution=1920x1080

test_ie_10_w8:
	mvn install -Dos=Windows -Dos_version=8 -Dbrowser=IE -Dbrowser_version=10.0 -Dresolution=1920x1080

test_ie_11_w8:
	mvn install -Dos=Windows -Dos_version=10 -Dbrowser=IE -Dbrowser_version=11.0 -Dresolution=1920x1080

test_safari_11_osxhs:
	mvn install -Dos="OS X" -Dos_version="High Sierra" -Dbrowser=Safari -Dbrowser_version=11.0 -Dresolution=1920x1080