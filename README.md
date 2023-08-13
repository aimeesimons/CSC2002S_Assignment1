# CSC2002S 
## PCP Assignment 1 SMNAIM002

## Getting started:
There are 4 rules/targets established in the Makefile:
* ```make```: This compiles all the files and saves them in the bin/MonteCarloMini folder.
* ```make runSerial```: This compiles and runs the serial program. 
* ```make runParallel```: This compiles and runs the parallel solution.
* ```make clean```: This removes the .class files from the bin/MonteCarloMini folder.\
#### *When using any of these, make sure you are in the main project folder: SMNAIM002_CSC2002S_PCP1

## Input parameters:
* rows, columns: the number of rows and columns in the discrete grid representing the function
* xmin, xmax, ymin, ymax: the boundaries of the rectangular area for the terrain
* searches density: number of searches per grid point
### Input parameters are given in the form:
```bash
"rows columns xmin xmax ymin ymax search density" 
```

## make runSerial:
To run the serial program, type the following:
```bash
$ make runSerial ARGS="<rows> <columns> <xmin> <xmax> <ymin> <ymax> <search density>"
```


## make runParallel:
Running the parallel program is exactly the same, however, the following is typed:
```bash
$ make runParallel ARGS="<rows> <columns> <xmin> <xmax> <ymin> <ymax> <search density>"
```


