JAVAC=/usr/bin/javac
.SUFFIXES: .java .class


SRCDIR=src/MonteCarloMini
BINDIR=bin/MonteCarloMini

.java.class:
	$(JAVAC) $< 

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<


CLASSES=Post.class User.class BinaryTreeNode.class BinaryTree.class BinarySearchTree.class Main.class

CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)
default: $(CLASS_FILES)
clean:
	rm $(BINDIR)/*.class 
run: $(CLASS_FILES)
	java -cp bin Main