
public class List {
	Cast[] list = new Cast[10];
	int num = 0;
	
	public void setList(Cast c) {
		if(num < 10) {
			list[num] = new Cast();
			list[num].copyCast(c);
			num++;
		} else {
			for(int i = 0; i < num-1; i++) {
				list[i].copyCast(list[i+1]);
			}
			list[9].copyCast(c);
		}
	}
	
	public Cast getList() {
		Cast cast = new Cast();
		num--;
		cast.copyCast(list[num]);
		list[num] = null;
//		System.out.println(num);
		return cast;
	}
}
