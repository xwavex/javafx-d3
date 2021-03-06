package org.treez.javafxd3.d3.arrays;

import java.util.ArrayList;
import java.util.List;

import org.treez.javafxd3.d3.AbstractTestCase;

import org.treez.javafxd3.d3.core.JsObject;

public class ArrayTest extends AbstractTestCase {

	@Override	
	public void doTest() {		

		testEmptyArray();
		test3x0Array();
		test1x3Array();
		test3x2Array();
		testFromList();
		testFromDoubles();
		testFromJavaScriptObjects();
		testForEach();
		
	}

	

	private void testEmptyArray() {
		JsObject emptyArrayObj = (JsObject) d3.eval("[]");
		Array<?> emptyArray = new Array<>(engine, emptyArrayObj);

		List<Integer> sizes = emptyArray.sizes();
		assertEquals("number of rows", 0, (int) sizes.get(0));
		assertEquals("number of columns", 0, (int) sizes.get(1));
		assertEquals("dimension", 0, emptyArray.dimension());
	}

	private void test3x0Array() {
		JsObject emptyArrayObj = (JsObject) d3.eval("[[],[],[]]");
		Array<?> emptyArray = new Array<>(engine, emptyArrayObj);

		List<Integer> sizes = emptyArray.sizes();
		assertEquals("number of rows", (int) sizes.get(0), 3);
		assertEquals("number of columns", (int) sizes.get(1), 0);
		assertEquals("dimension", 2, emptyArray.dimension());
	}

	private void test3x2Array() {
		// 3 x 2
		JsObject array3x2Object = (JsObject) d3.eval("[[1, 2],[3,4],[5,6]]");
		Array<Integer> array3x2 = new Array<>(engine, array3x2Object);

		List<Integer> sizes3x2 = array3x2.sizes();

		assertEquals("number of rows", (int) sizes3x2.get(0), 3);
		assertEquals("number of columns", (int) sizes3x2.get(1), 2);
		assertEquals("dimension", array3x2.dimension(), 2);
	}

	private void test1x3Array() {
		// 1 x 3
		JsObject array1x3Object = (JsObject) d3.eval("[1, 2, 3]");
		Array<Integer> array1x3 = new Array<>(engine, array1x3Object);

		List<Integer> sizes1x3 = array1x3.sizes();
		assertEquals("number of rows", 1, (int) sizes1x3.get(0));
		assertEquals("number of columns", 3, (int) sizes1x3.get(1));
		assertEquals("dimensions", 1, array1x3.dimension());
	}

	private void testFromList() {
		List<Double> data = new ArrayList<>();
		data.add(1.0d);
		data.add(2.0d);
		Array<Double> doubleArray = Array.fromList(engine, data);

		List<Integer> sizes = doubleArray.sizes();
		assertEquals("number of rows", 1, (int) sizes.get(0));
		assertEquals("number of columns", 2, (int) sizes.get(1));
		assertEquals("dimensions", 1, doubleArray.dimension());
		
		double firstValue = doubleArray.get(0, Double.class);
		assertEquals("first value", 1.0, firstValue, 1e-6);
		
		assertEquals("second value", 2.0, doubleArray.get(1, Double.class), 1e-6);
	}

	private void testFromDoubles() {
		
		Array<Double> doubleArray = Array.fromDoubles(engine, new Double[]{3.0,4.0});
		
		List<Integer> sizes = doubleArray.sizes();
		assertEquals("number of rows", 1, (int) sizes.get(0));
		assertEquals("number of columns", 2, (int) sizes.get(1));
		assertEquals("dimensions", 1, doubleArray.dimension());
		
		assertEquals("first value", 3.0, doubleArray.get(0, Double.class),TOLERANCE);
		assertEquals("second value", 4.0, doubleArray.get(1, Double.class),TOLERANCE);

	}

	private void testFromJavaScriptObjects() {
		
		JsObject firstObject = d3.evalForJsObject("[2]");
		JsObject secondObject = d3.evalForJsObject("['foo']");		
		
		Array<JsObject> array = Array.fromJavaScriptObjects(engine, firstObject, secondObject);		
		List<Integer> sizes = array.sizes();
		assertEquals("number of rows", 2, (int) sizes.get(0));
		assertEquals("number of columns", 1, (int) sizes.get(1));
		assertEquals("dimensions", 1, array.dimension());
				
		
		assertEquals("first value", firstObject, array.get(0, JsObject.class) );
		assertEquals("second value", secondObject, array.get(1, JsObject.class) );

	}
	
	
	private void testForEach() {
		
		JsObject arrayObject = d3.evalForJsObject("[1,2,3]");
		Array<Double> array = new Array<>(engine, arrayObject);
		
		Double firstElement = array.get(0, Double.class);
		assertEquals(1d, firstElement, TOLERANCE);
		
		int[] count = {0};
		array.forEach((element)->{count[0]++;});
		assertEquals(3, count[0]);		
		
	}

}
