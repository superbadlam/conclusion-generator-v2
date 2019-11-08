package ru.skkdc.lis.conclusion.template.helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import ru.skkdc.lis.conclusion.template.TextAlignment;

class AdjustmentManagerTest {

	@Test
	void testNullInsteadText01() {
		List<String> lines = AdjustmentManager.adjustText(null, TextAlignment.create("right"), 10, -1);
		assertEquals(0, lines.size());
	}
	@Test
	void testNullInsteadText02() {
		List<String> lines = AdjustmentManager.adjustText(null, TextAlignment.create("right"), 10);
		assertEquals(0, lines.size());
	}
	@Test
	void testNullInsteadText03() {
		List<String> lines = AdjustmentManager.adjustText(null, TextAlignment.create("right"), 10, 3);
		assertEquals(0, lines.size());
	}
	@Test
	void testSimpleText01() {
		List<String> lines = AdjustmentManager.adjustText("some text", TextAlignment.create("right"), 10, -1);
		assertEquals(1, lines.size());
		assertEquals(" some text", lines.get(0));
	}

	
	@Test
	void testSimpleText02() {
		List<String> lines = AdjustmentManager.adjustText("some text", TextAlignment.create("right"), 10, 2);
		assertEquals(1, lines.size());
		assertEquals(" some text", lines.get(0));
	}

	@Test
	void testSimpleText03() {
		List<String> lines = AdjustmentManager.adjustText("33   tex3", TextAlignment.create("right"), 10, 2);
		assertEquals(1, lines.size());
		assertEquals(" 33   tex3", lines.get(0));
	}

	@Test
	void testTextNumberWithoutRounding() {
		List<String> lines = AdjustmentManager.adjustText("123.45", TextAlignment.create("right"), 10, -1);
		assertEquals(1, lines.size());
		assertEquals("    123.45", lines.get(0));
	}
	
	@Test
	void testTextNumberWithRounding01() {
		List<String> lines = AdjustmentManager.adjustText("123.45", TextAlignment.create("right"), 10, 0);
		assertEquals(1, lines.size());
		assertEquals("       123", lines.get(0));
	}
	
	@Test
	void testTextNumberWithRounding02() {
		List<String> lines = AdjustmentManager.adjustText("123.45", TextAlignment.create("right"), 10, 1);
		assertEquals(1, lines.size());
		assertEquals("     123.5", lines.get(0));
	}
	
	@Test
	void testTextNumberWithRounding03() {
		List<String> lines = AdjustmentManager.adjustText("123.45", TextAlignment.create("right"), 10, 2);
		assertEquals(1, lines.size());
		assertEquals("    123.45", lines.get(0));
	}
	
	@Test
	void testTextNumberWithRounding04() {
		List<String> lines = AdjustmentManager.adjustText("123.45", TextAlignment.create("right"), 10, 3);
		assertEquals(1, lines.size());
		assertEquals("   123.450", lines.get(0));
	}
	
	@Test
	void testTextNumberWithRounding05() {
		List<String> lines = AdjustmentManager.adjustText(".45", TextAlignment.create("right"), 10, 3);
		assertEquals(1, lines.size());
		assertEquals("     0.450", lines.get(0));
	}
	
	@Test
	void testTextNumberWithCommaInsteadDot() {
		List<String> lines = AdjustmentManager.adjustText(",45", TextAlignment.create("right"), 10, 3);
		assertEquals(1, lines.size());
		assertEquals("       ,45", lines.get(0));
	}
	
}
