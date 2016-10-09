package ChaperSix.Array;

import java.util.HashMap;
import java.util.Map;

public class TwoNumSum {
	public static String  SumTwoNumber(int[] a , int sum){
		Map<Integer , Integer > mapSum= new HashMap<>();
		for (int i=0 ;i<a.length;i++) {
			mapSum.put(a[i], i);
		}
		for (int  i : a) {
			if(null!=mapSum.get(sum-i)){
				return mapSum.get(i)+" "+mapSum.get(sum-i);
			}
		}
		return null;
	}
public static void main(String[] args) {
	int[] a= { 0, 10, 0, 0, 5 };
	System.out.println(SumTwoNumber(a, 15));
}
}
