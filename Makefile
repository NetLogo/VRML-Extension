ifeq ($(origin JAVA_HOME), undefined)
  JAVA_HOME=/usr
endif

ifeq ($(origin NETLOGO), undefined)
  NETLOGO=../..
endif

ifneq (,$(findstring CYGWIN,$(shell uname -s)))
  COLON=\;
  JAVA_HOME := `cygpath -up "$(JAVA_HOME)"`
else
  COLON=:
endif

SRCS=$(wildcard src/*.java)

vrml.jar vrml.jar.pack.gz: $(SRCS) cx3djava100a.jar cx3djava100a.jar.pack.gz manifest.txt
	mkdir -p classes
	$(JAVA_HOME)/bin/javac -g -encoding us-ascii -source 1.5 -target 1.5 -classpath $(NETLOGO)/NetLogoLite.jar$(COLON)cx3djava100a.jar -d classes $(SRCS)
	jar cmf manifest.txt vrml.jar -C classes .
	pack200 --modification-time=latest --effort=9 --strip-debug --no-keep-file-order --unknown-attribute=strip vrml.jar.pack.gz vrml.jar

cx3djava100a.jar cx3djava100a.jar.pack.gz:
	wget -nc 'http://ccl.northwestern.edu/devel/cx3djava100a.jar'
	pack200 --modification-time=latest --effort=9 --strip-debug --no-keep-file-order --unknown-attribute=strip cx3djava100a.jar.pack.gz cx3djava100a.jar

vrml.zip: vrml.jar
	rm -rf vrml
	mkdir vrml
	cp -rp vrml.jar vrml.jar.pack.gz cx3djava100a.jar cx3djava100a.jar.pack.gz README.md Makefile src manifest.txt vrml
	zip -rv vrml.zip vrml
	rm -rf vrml

