import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;


public class Cast {
	int c;
	int u;
	int a;
	Unicast uni[];
	Anycast any[];
	
	public Cast() {
		
	}
	
	public Cast(String s1, String s2, String s3) {
		
		File file = new File(s1);
		CvString cv = new CvString();
		String tmp[];
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String s;
			s = in.readLine();
			u = Integer.parseInt(s);
			s = in.readLine();
			
			uni = new Unicast[u];
			
			for(int i = 0; i < u; i++) {
				uni[i] = new Unicast();
				s = in.readLine();
				tmp = cv.tach(s, 3);
				uni[i].s = Integer.parseInt(tmp[0]) - 1;
				uni[i].t = Integer.parseInt(tmp[1]) - 1;
				uni[i].b = Integer.parseInt(tmp[2]);
			}
			in.close();
			
			file = new File(s2);
			in = new BufferedReader(new FileReader(file));
			s = in.readLine();
			a = Integer.parseInt(s);
			s = in.readLine();
			
			any = new Anycast[a];
			
			for(int i = 0; i < a; i++) {
				any[i] = new Anycast();
				s = in.readLine();
				tmp = cv.tach(s, 3);
				any[i].s = Integer.parseInt(tmp[0]) - 1;
				any[i].t1 = Integer.parseInt(tmp[1]) - 1;
				any[i].t2 = any[i].t1;
				any[i].b = Integer.parseInt(tmp[2]);
//				any[i].b = 3;
			}
			in.close();
			
			file = new File(s3);
			in = new BufferedReader(new FileReader(file));
			int rp[][] = new int[3][2];
			s = in.readLine();
			int i = 0;
			while(s != null) {
				tmp = cv.tach(s, 2);
				rp[i][0] = Integer.parseInt(tmp[0]) - 1;
				rp[i][1] = Integer.parseInt(tmp[1]) - 1;
				i++;
				s = in.readLine();
			}
			in.close();
			
			for(int j = 0; j < a; j++) {
				for(int k = 0; k < i; k++) {
					if(any[j].t1 == rp[k][0] && any[j].s != rp[k][1]) {
						any[j].t2 = rp[k][1];
					}
				}
			}
			
		} catch (Exception e) {
		}
		
	}
	
	public void copyCast(Cast cast) {
		u = cast.u;
		a = cast.a;
		c = cast.c;
		uni = new Unicast[u];
		any = new Anycast[a];
		for(int i = 0; i < u; i++) {
			uni[i] = new Unicast();
			uni[i].s = cast.uni[i].s;
			uni[i].t = cast.uni[i].t;
			uni[i].b = cast.uni[i].b;
			uni[i].wp.s = cast.uni[i].wp.s;
			uni[i].wp.t = cast.uni[i].wp.t;
			uni[i].wp.b = cast.uni[i].wp.b;
			uni[i].wp.size = cast.uni[i].wp.size;
			uni[i].wp.path = new int[uni[i].wp.size];
			for(int j = 0; j < uni[i].wp.size; j++) {
				uni[i].wp.path[j] = cast.uni[i].wp.path[j];
			}
			uni[i].bp.s = cast.uni[i].bp.s;
			uni[i].bp.t = cast.uni[i].bp.t;
			uni[i].bp.b = cast.uni[i].bp.b;
			uni[i].bp.size = cast.uni[i].bp.size;
			uni[i].bp.path = new int[uni[i].bp.size];
			for(int j = 0; j < uni[i].bp.size; j++) {
				uni[i].bp.path[j] = cast.uni[i].bp.path[j];
			}
		}
		for(int i = 0; i < a; i++) {
			any[i] = new Anycast();
			any[i].s = cast.any[i].s;
			any[i].t1 = cast.any[i].t1;
			any[i].t2 = cast.any[i].t2;
			any[i].b = cast.any[i].b;
			any[i].wp.s = cast.any[i].wp.s;
			any[i].wp.t = cast.any[i].wp.t;
			any[i].wp.b = cast.any[i].wp.b;
			any[i].wp.size = cast.any[i].wp.size;
			any[i].wp.path = new int[any[i].wp.size];
			for(int j = 0; j < any[i].wp.size; j++) {
				any[i].wp.path[j] = cast.any[i].wp.path[j];
			}
			any[i].bp.s = cast.any[i].bp.s;
			any[i].bp.t = cast.any[i].bp.t;
			any[i].bp.b = cast.any[i].bp.b;
			any[i].bp.size = cast.any[i].bp.size;
			any[i].bp.path = new int[any[i].bp.size];
			for(int j = 0; j < any[i].bp.size; j++) {
				any[i].bp.path[j] = cast.any[i].bp.path[j];
			}
		}
	}
	
	public void Output(String s) {
		File file = new File(s);
		try {
			PrintWriter out = new PrintWriter(new FileWriter(file));
			
			int t = a + u;
			out.println(t);
			out.println();
			t = 0;
			int tmp = 0;
			
			for(int i = 0; i < u; i++) {
				t++;
				out.println(t + " 0");
				tmp = uni[i].s + 1;
				out.print(tmp + " ");
				tmp = uni[i].t + 1;
				out.println(tmp);
				for(int j = 0; j < uni[i].wp.size; j++) {
					tmp = uni[i].wp.path[j] + 1;
					out.print(tmp + " ");
				}
				out.println();
				for(int j = 0; j < uni[i].bp.size; j++) {
					tmp = uni[i].bp.path[j] + 1;
					out.print(tmp + " ");
				}
				out.println();
				out.println();
			}
			for(int i = 0; i < a; i++) {
				t++;
				out.println(t + " 1");
				tmp = any[i].s + 1;
				out.print(tmp + " ");
				tmp = any[i].t1 + 1;
				out.println(tmp);
				for(int j = 0; j < any[i].wp.size; j++) {
					tmp = any[i].wp.path[j] + 1;
					out.print(tmp + " ");
				}
				out.println();
				for(int j = 0; j < any[i].bp.size; j++) {
					tmp = any[i].bp.path[j] + 1;
					out.print(tmp + " ");
				}
				out.println();
				out.println();
			}
			out.close();
		} catch (Exception e) {
		}
	}
}
