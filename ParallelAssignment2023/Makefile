JAVAC=/usr/bin/javac
.SUFFIXES: .java .class


SRCDIR=src/MonteCarloMini
BINDIR=bin/MonteCarloMini

.java.class:
	$(JAVAC) $*.java

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<


CLASSES= \
	PairedThreads.java\
	TerrainArea.java SearchParallel.java\
	MonteCarloMinimization.java\
	MonteCarloMinimizationParallel.java

CLASS_FILES=$(CLASSES:%.java=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class

runSerial: $(CLASS_FILES)
	java -cp bin MonteCarloMinimization
runParallel: $(CLASS_FILES)
	java -cp bin MonteCarloMinimizationParallel