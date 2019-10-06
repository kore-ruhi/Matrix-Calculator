# Matrix-Calculator

A calculator that implements a bi-directional queue to perform matrix operations.

Background Information/Inspiration for this Project:
The cost of a matrix operation depends heavily on the choice of data structure used to represent the matrix operands. There are several ways I thought of to represent a square matrix with real entries. A standard approach is to use a 2-dimensional 𝑛 × 𝑛 array of doubles. The advantage of this representation is that all of the above matrix operations have a straight-forward implementation using nested loops. 
My project uses a very different representation however: as a 1 dimensional array of Lists. Each List represents one row of the Matrix, but only the non-zero entries will be stored. Therefore List elements store, not just the matrix entries, but the column index in which those entries reside. This method results in substantial space saving when the matrix is sparse (does not contain many non-zero elements). However, the matrix operations are much more difficult to implement using this representation. The trade-off then, is a gain in space and time efficiency for sparse matrices, at the expense of more complicated algorithms for performing standard matrix operations.
