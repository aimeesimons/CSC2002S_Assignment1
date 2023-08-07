JAVAC=/usr/bin/javac
JAVA=/usr/bin/java
.SUFFIXES: .java .class


SRCDIR=src/MonteCarloMini
BINDIR=bin
.java.class:
	$(JAVAC) $< 

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) -sourcepath $(SRCDIR) $<


CLASSES=PairedThreads.class Direction.class TerrainArea.class SearchParallel.class MonteCarloMinimization.class MonteCarloMinimizationParallel.class

CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)
default: $(CLASS_FILES)
clean:
	rm $(BINDIR)/MonteCarloMini/*.class

runSerial: $(CLASS_FILES)
	java -cp $(BINDIR) MonteCarloMini.MonteCarloMinimization $(ARGS)

runParallel: $(CLASS_FILES)
	java -cp $(BINDIR) MonteCarloMini.MonteCarloMinimizationParallel $(ARGS)


	