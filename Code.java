package misc;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class main_misc2 {
	
	public static class Coordinate{
		
		public int x;
		public int y;
		public int has_path = 1;
		
		public Coordinate(int a,int b) {
			x = a;
			y = b;
		}
		public boolean equals(Coordinate a){
			if((x == a.x) && (y == a.y)){
				return true;
			}
			return false;
		}
		public String toString(){
			
			return "("+x+","+y+")";
			
		}
		
		
	}
	
	public static void main(String[] args) {
	//(a+b)%k = ((a % k)+(b % k)) % k    || ||
	
	find_path(4,3,3);
		 
	
	}	
	
	/*
	 * Find a path from (0,0) to (m,n),the robot can only right and down and there are cells of limits
	 */
	
	
	private static boolean in_list(List<Coordinate> off,Coordinate p){
		for(int i = 0;i < off.size();i++) {
			if(off.get(i).equals(p)) {
				return true;
			}
		}
		return false;
	}
	
	private static void generate_off_limits_cells(int num,int n,int m,List<List<Coordinate>>grid){	
		//num:number of off limits cells
		//n and m --> NxM grid
		
		List<Coordinate> off = new ArrayList<>();
		
		if(num >= n*m) {
			num = (n*m)-1;
		}
		
		int c = 0;
		while(c < num) {
			
			Random r1 = new Random();
			Random r2 = new Random();
			int x = r1.nextInt(n); 
			int y = r2.nextInt(m);
			Coordinate novo = new Coordinate(x,y);
			if(!in_list(off,novo)) {
				grid.get(x).get(y).has_path = 0;
				off.add(novo);
				c++;
			}
			
		}
		System.out.println(off);
	
	}
	
	private static int find_path(int n,int m,int num){
		
		List<List<Coordinate>> grid = new ArrayList<>();
		for(int i = 0;i < n;i++){
			grid.add(new ArrayList<>()); 
			for(int j = 0;j < m;j++){
				Coordinate p = new Coordinate(i,j);
				grid.get(i).add(p);
			}
		}
		//if (0,0) is a off limit,the robot will act as if (0,0) is a normal cell
		generate_off_limits_cells(num,n,m,grid);
		//testing:
		/*grid.get(3).get(2).has_path = 0;
		grid.get(1).get(2).has_path = 0;
		grid.get(0).get(2).has_path = 0;*/
		
		List<Coordinate> path = new ArrayList<>();
		int x = find_path(grid,0,0,path,n-1,m-1);
		System.out.println(x);
		System.out.println(path);
		
		return 1;
	}
	
	private static int check_path(int x,int y,int n,int m,List<List<Coordinate>> grid){
		
		if(x > n || y > m) {
			return 0;
		}
		return grid.get(x).get(y).has_path;
		
	}
	
	private static int find_path(List<List<Coordinate>> grid,int x,int y,List<Coordinate> path,int n,int m){
		
		//find a path in the grid,but there are some coordinates that are off limits
		if(x == n && y == m) {
			return 1;
		}
		if(x == 0 && y == 0 && (grid.get(1).get(0).has_path == 0 && grid.get(0).get(1).has_path == 0)){
			return 0;
		}
		
		if(x+1 <= n && grid.get(x+1).get(y).has_path == 1) {
			path.add(grid.get(x+1).get(y));
			return find_path(grid,x+1,y,path,n,m);
		}
		if(y+1 <= m && grid.get(x).get(y+1).has_path == 1) {
			path.add(grid.get(x).get(y+1));
			return find_path(grid,x,y+1,path,n,m);
		}
		if(check_path(x+1,y,n,m,grid) == 0 && check_path(x,y+1,n,m,grid) == 0) {
			//no path,so go back to (0,0).Now,the robot now that this cell is "bad"
			grid.get(x).get(y).has_path = 0;
			path.clear();
                        return find_path(grid,0,0,path,n,m);
		}
		return 0;
		
	}
	
	
	} 
	
