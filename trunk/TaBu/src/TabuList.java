
public class TabuList {
	Chil[] list = new Chil[10];
	int num = 0;
	
	public void setList(Cast c, int l, int t) {
		if(num < 10) {
			list[num] = new Chil();
			list[num].cast.copyCast(c);
			list[num].type = l;
			list[num].num = t;
			num++;
		} else {
			for(int i = 0; i < num-1; i++) {
				list[i].cast.copyCast(list[i+1].cast);
				list[i].type = list[i+1].type;
				list[i].num = list[i+1].num;
			}
			list[9].cast.copyCast(c);
			list[9].type = l;
			list[9].num = t;
		}
	}
	
	public Chil getList() {
		if(num == 0)
			return null;
		Chil chil = new Chil();
		num--;
		chil.cast.copyCast(list[num].cast);
		chil.type = list[num].type;
		chil.num = list[num].num;
		list[num] = null;
//		System.out.println(num);
		return chil;
	}
}
