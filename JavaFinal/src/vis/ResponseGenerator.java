package vis;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;
abstract class ResponseGeneratorBase {
	protected String errMsg, result;
	protected boolean isErr;
	public String makejson() throws IOException {
		putresult();
		return isErr ? errMsg : result;
	}
	ResponseGeneratorBase() {
		errMsg = "unknown error"; result = "";
	}
	public abstract void putresult() throws IOException;
	public abstract void appendValue(int a);
	public abstract void appendValue(int a, String Name);
}
class SingleValueGenerator extends ResponseGeneratorBase {
	private ArrayList<Double> container;
	private ArrayList<String> nameContainer;
	int curptr;
	int setsize; // shape
	int requiresName; // 1: X 轴需要后端传入指标名, 0: X 轴已经定义好了指标名
	SingleValueGenerator(int ss, int req) {
		super();
		setsize = ss; requiresName = req;
		container = new ArrayList<Double>();
		nameContainer = new ArrayList<String>();
		curptr = 0;
	}
	public void appendValue(int a) {
		if(requiresName == 1) {
			isErr = true;
			errMsg = "please use appendValue(int a, String name)";
			return;
		}
		container.add(1.0 * a / 100);
		curptr++;
		if(curptr > setsize) {
			// debug用
			isErr = true;
			errMsg = "too many items";
			return;
		}
		return;
	}
	public void appendValue(int a, String name) {
		if(requiresName == 0) {
			isErr = true;
			errMsg = "please use appendValue(int a)";
			return;
		}
		container.add(1.0 * a / 100);
		if(nameContainer.contains(name) == false) nameContainer.add(name);
		curptr++;
		if(curptr > setsize) {
			/* debug */
			isErr = true;
			errMsg = "too many items";
			return;
		}
		return;
	}
	public void putresult() throws IOException {
		if(isErr) {
			result = errMsg;
			return;
		}
		ObjectMapper mp = new ObjectMapper();
		if(requiresName == 1)
			result = "{ \"status\": \"success\", \"scores\": " + mp.writeValueAsString(container) + ", \"names\":" + mp.writeValueAsString(nameContainer) + "}";
		else result = "{ \"status\": \"success\", \"scores\": " + mp.writeValueAsString(container) + "}";
	}
}
class MultiValueGenerator extends ResponseGeneratorBase {
	private ArrayList<ArrayList<Double>> container;
	private ArrayList<String> nameContainer;
	int curptr;
	int numberOfArray, numberOfElement; // shape
	int requiresName;
	MultiValueGenerator(int n, int m, int req) {
		super();
		numberOfArray = n; numberOfElement = m; requiresName = req;
		container = new ArrayList<ArrayList<Double>>();
		nameContainer = new ArrayList<String>();
		for(int i = 0; i < n; i++) container.add(new ArrayList<Double>());
		curptr = 0;
	}
	public void appendValue(int a) {
		if(numberOfElement == 0) return; // avoiding divide by zero
		if(requiresName == 1) {
			isErr = true;
			errMsg = "please use appendValue(int a, String name)";
			return;
		}
		int pos = curptr / numberOfElement;
		container.get(pos).add(1.0 * a / 100);
		curptr++;
		if(pos > numberOfArray) {
			// debug用
			isErr = true;
			errMsg = "too many items";
			return;
		}
		return;
	}
	public void appendValue(int a, String name) {
		if(numberOfElement == 0) return;
		if(requiresName == 0) {
			isErr = true;
			errMsg = "please use appendValue(int a)";
			return;
		}
		if(nameContainer.contains(name) == false) nameContainer.add(name);
		int pos = curptr / numberOfElement;
		container.get(pos).add(1.0 * a / 100);
		curptr++;
		if(pos > numberOfArray) {
			// debug用
			isErr = true;
			errMsg = "too many items";
			return;
		}
		return;
	}
	public void putresult() throws IOException {
		if(isErr) {
			result = errMsg;
			return;
		}
		ObjectMapper mp = new ObjectMapper();
		if(requiresName == 1)
			result = "{ \"status\": \"success\", \"scores\": " + mp.writeValueAsString(container) + ", \"names\":" + mp.writeValueAsString(nameContainer) + "}";
		else result = "{ \"status\": \"success\", \"scores\": " + mp.writeValueAsString(container) + "}";
	}
}