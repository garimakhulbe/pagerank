import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;


public class PageRankAlgorithm {
	
	double adjMat[][];
	double transitionMat[][];
	double M[][];
	int m;
	double dampenFactor=0.85;
	double [][] rankVector;
	
	void setDampenFactor(double alpha)
	{
		this.dampenFactor=alpha;
	}
	double getDampdenFactor()
	{
		return this.dampenFactor;
	}
	
	void setPageCount(int m)
	{
		this.m=m;
	}
	int getPageCount()
	{
		return this.m;
	}
	
	void setAdjMat(double adj[][])
	{
		this.adjMat=adj;
	}
	
	double[][] getAdjMat()
	{
		return this.adjMat;
	}
	/* Read Adjancency matrix from the input file 
	 * The name of file is specified from argument 1 in command line*/
	private void readAdjMatrixFromFile(String args)
	{
		File file=null;
		try{
		if(args!=null) {
            file = new File(args);
		}
		
		this.adjMat=new double[m][m];
		BufferedReader fst = new BufferedReader(new FileReader(file));
		String line = new String();
        
        String oneline=null;
        while ((oneline = fst.readLine()) != null) {
            line=new String(oneline);
            String [] str = line.split(" ");
            if(Integer.parseInt(str[2])!=1)
            		continue;
            adjMat[Integer.parseInt(str[1])-1][Integer.parseInt(str[0])-1]=1;
        }
        fst.close();
		
	}catch(IOException e){
	      System.out.print("Exception"+e);
	   }
            
	}
	
	/* Create the transition Matrix A */
	private void makeTransitionMatrix()
	{
		int col = adjMat[0].length;
		int[][] outDegCount = new int[1][col];
		
		transitionMat=new double[m][m];
			
		transitionMat=adjMat;
		for(int j=0; j<m;j++)
		{
			for(int i=0;i<m;i++)
			{
				outDegCount[0][j]+=adjMat[i][j];
			}
		}
		
		for(int j=0; j<m;j++)
		{
			for(int i=0;i<m;i++)
			{
				if(outDegCount[0][j]==0)
				{
					transitionMat[i][j]=(double)1/m;
				}
				else 
				{
					transitionMat[i][j]=(adjMat[i][j]/outDegCount[0][j]); 
				}
			}
		}
	}
	
	/* Create final matrix M for equation: d*M+(1-d)A */
	private void CreateFinalMatrix()
	{
		M=new double[m][m];
		double B[][]= new double[m][m];
		
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<m;j++)
			{
				B[i][j]=(double)1/m;
			}
		}
		
		makeTransitionMatrix();
		
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<m;j++)
			{
				M[i][j]= (dampenFactor*transitionMat[i][j]+(1-dampenFactor)*B[i][j]);
			}
		}
	}
	
	
	
	private boolean diffBetweenMatrices(double [][]A, double [][] B, int m, int n)
	{
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++)
			{
				if(Math.abs(A[i][j]-B[i][j])==0.0)
					continue;
				return true;
			}
		return false;
	}
	
	private double roundoff(double a,double b)
	{
		return Math.round(a*Math.pow(10,b))/Math.pow(10,b);
	}
	
	
	private double [][] multiplyMatrices(double [][]A, double [][] B, int m, int p,int n)
	{
		double [][] C = new double[m][n];
		double sum =0.0;
		for (int c=0 ; c<m ; c++ )
        {
           for ( int d=0 ; d<n ; d++ )
           {   
              for ( int k = 0 ; k < p ; k++ )
              {
                 sum = sum + A[c][k]*B[k][d];
              }

              C[c][d] = sum;
              sum = 0.0;
           }
        }
		return C;
	}
	
	private void displayMatrix(double a[][],int m,int n)
	{
		for(int i=0; i<m;i++)
		{
			for(int j=0;j<n;j++)
			{
				System.out.print(a[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	/* Final Page Rank with the equation in while loop until there is change in documents' Ranks */
	
	void pageRank(String filename)
	{
		double[][] V=new double[m][1];
		double [][] K = new double[m][1];
		
		readAdjMatrixFromFile(filename);
		for(int i=0;i<m;i++)
		{
			K[i][0]=0.0;
		}
		
		for(int i =0;i<m;i++)
			V[i][0]=(double)1/m;
		
		
		CreateFinalMatrix();
		while(true)
		{
			K=multiplyMatrices(M,V,m,m,1);
			if(!diffBetweenMatrices(K,V,m,1))
				break;
			V=K;
		}

		// Print the ranks with documents
		printRanks(K,m);
	}
	
	/* Sorted the scores and print the ranks with documents */
	void printRanks(double K[][],int m)
	{
		rankVector=new double[m][2];
		
		for(int i=0;i<m;i++)
		{
			rankVector[i][0]=K[i][0];
			rankVector[i][1]=i;
		}
		Arrays.sort(rankVector, new Comparator<double[]>() {
            @Override
            public int compare(final double[] col1, final double[] col2) {
                final Double val1 = col1[0];
                final Double val2 = col2[0];
                return val1.compareTo(val2);
            }
        });	
		
		System.out.println("Ranked Documents");
		for(int i=m-1,j=1;i>=0;i--)
		{
			System.out.println("Rank "+j+"-> Document "+(int)(rankVector[i][1]+1)+
					" ::Score :"+roundoff(rankVector[i][0],4));
			j++;
		}
		
	}
	
	public static void main(String args[])
	{
		PageRankAlgorithm pga=new PageRankAlgorithm();
		
		String s=new String();
		String m=new String();
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			System.out.println("Enter the File name:");
		    s = bufferRead.readLine();
		    System.out.println("Enter Number of pages:");
		    m = bufferRead.readLine();
		    pga.setPageCount(Integer.parseInt(m));
		    pga.pageRank(s);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	 	
	}

}
