import numpy as np
import warnings

def swapRows(A, i, j):
    """
    interchange two rows of A
    operates on A in place
    """
    tmp = A[i].copy()
    A[i] = A[j]
    A[j] = tmp

def relError(a, b):
    """
    compute the relative error of a and b
    """
    with warnings.catch_warnings():
        warnings.simplefilter("error")
        try:
            return np.abs(a-b)/np.max(np.abs(np.array([a, b])))
        except:
            return 0.0

def rowReduce(A, i, j, pivot):
    """
    reduce row j using row i with pivot pivot, in matrix A
    operates on A in place
    """
    factor = A[j][pivot] / A[i][pivot]
    for k in range(len(A[j])):
        # we allow an accumulation of error 100 times larger 
        # than a single computation
        # this is crude but works for computations without a large 
        # dynamic range
        if relError(A[j][k], factor * A[i][k]) < 100 * np.finfo('float').resolution:
            A[j][k] = 0.0
        else:
            A[j][k] = A[j][k] - factor * A[i][k]

# stage 1 (forward elimination)
def forwardElimination(B):
    """
    Return the row echelon form of B
    """
    A = B.copy()
    m, n = np.shape(A)
    for i in range(m-1):
        # Let lefmostNonZeroCol be the position of the leftmost nonzero value 
        # in row i or any row below it 
        leftmostNonZeroRow = m
        leftmostNonZeroCol = n
        ## for each row below row i (including row i)
        for h in range(i,m):
            ## search, starting from the left, for the first nonzero
            for k in range(i,n):
                if (A[h][k] != 0.0) and (k < leftmostNonZeroCol):
                    leftmostNonZeroRow = h
                    leftmostNonZeroCol = k
                    break
        # if there is no such position, stop
        if leftmostNonZeroRow == m:
            break
        # If the leftmostNonZeroCol in row i is zero, swap this row 
        # with a row below it
        # to make that position nonzero. This creates a pivot in that position.
        if (leftmostNonZeroRow > i):
            swapRows(A, leftmostNonZeroRow, i)
        # Use row reduction operations to create zeros in all positions 
        # below the pivot.
        for h in range(i+1,m):
            rowReduce(A, i, h, leftmostNonZeroCol)
    return A

#################### 

# If any operation creates a row that is all zeros except the last element,
# the system is inconsistent; stop.
def inconsistentSystem(B):
    """
    B is assumed to be in echelon form; return True if it represents
    an inconsistent system, and False otherwise
    """
    m, n = np.shape(B)
    for i in range(m):
        allZero = True
        for j in range(n-1):
            #print B[i][j]
            if B[i][j] != 0:
                allZero = False
        if allZero and B[i][n-1] != 0:
            return True
    return False

def backsubstitution(B):
    """
    return the reduced row echelon form matrix of B
    """
    A = B.copy()
    m, n = np.shape(B)
    for i in range(m):
        allZero = True
        pivot = A[i][0]
        for j in range(n):
            #If row i has a nonzero pivot value, divide row i by its pivot value.
            if A[i][j] != 0:
                if allZero:
                    allZero = False
                    pivot = A[i][j]
                    # Use row reduction operations to create zeros in all positions above the pivot.
                    for m in range(i):
                        if A[m][j] != 0:
                            rowReduce(A,i,m,j)
                A[i][j] = A[i][j]/pivot
        #If row i is all zeros, stop.
        if allZero:
            break
    return A

def solutionSet(B):
    m, n = np.shape(B)
    solutionset = []
    for i in range(m):
        x = str(B[i][n - 1])
        allZero = True
        for j in range(i,n-1):
            if B[i][j] != 0:
                allZero = False
                if i != j:
                    if B[i][j] > 0:
                        x +='-'+str(B[i][j])+ 'x' + str(j+1)
                    else:
                        x += '+' + str(abs(B[i][j])) + 'x' + str(j+1)
        if not allZero:
            solutionset.append(x)

    if n-1-len(solutionset) > 0:
        for i in range(n-1-len(solutionset)):
            solutionset.append('free')

    return solutionset


if __name__ == '__main__':
    for i in range(7):
        file = 'h2m'+str(i)+'.txt'
        A = np.loadtxt(file)
        #print A
        AEchelon = forwardElimination(A)
        #print AEchelon
        if not inconsistentSystem(AEchelon):
            AReducedEchelon = backsubstitution(AEchelon)
            print 'linear system in ' + file + ' is consistent.'
            #print AReducedEchelon
            print 'solution :' + str(solutionSet(AReducedEchelon))
        else:
            print 'linear system in ' + file + ' is inconsistent.'
        print "\n"
#####################


