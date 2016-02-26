package cst.zju.com.util;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import cst.zju.com.main.mainMethod;

public class LineChart {

	public static JFreeChart createLineChart(String chartTitle, String x, String y, CategoryDataset dataset) {

		// 构建一个chart9
		JFreeChart chart = ChartFactory.createLineChart(chartTitle, x, y, dataset, PlotOrientation.VERTICAL, true, true,
				false);
		// 字体清晰
		chart.setTextAntiAlias(false);
		// 设置背景颜色
		chart.setBackgroundPaint(Color.WHITE);

		// 设置图标题的字体
		Font font = new Font("隶书", Font.BOLD, 25);
		chart.getTitle().setFont(font);

		// 设置面板字体
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
		// 设置图示的字体
		chart.getLegend().setItemFont(labelFont);

		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		// x轴 // 分类轴网格是否可见
		categoryplot.setDomainGridlinesVisible(true);
		// y轴 //数据轴网格是否可见
		categoryplot.setRangeGridlinesVisible(true);
		categoryplot.setRangeGridlinePaint(Color.WHITE);// 虚线色彩
		categoryplot.setDomainGridlinePaint(Color.WHITE);// 虚线色彩
		categoryplot.setBackgroundPaint(Color.lightGray);// 折线图的背景颜色

		// 设置轴和面板之间的距离
		// categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));

		// 横轴 x
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		domainAxis.setLabelFont(labelFont);// 轴标题
		domainAxis.setTickLabelFont(labelFont);// 轴数值
		// domainAxis.setLabelPaint(Color.BLUE);//轴标题的颜色
		// domainAxis.setTickLabelPaint(Color.BLUE);//轴数值的颜色

		// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);

		// 设置距离图片左端距离
		domainAxis.setLowerMargin(0.0);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.0);

		// 纵轴 y
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setLabelFont(labelFont);
		numberaxis.setTickLabelFont(labelFont);
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);

		// 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();
		lineandshaperenderer.setBaseShapesVisible(true); // series 点（即数据点）可见
		lineandshaperenderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见

		// 显示折点数据
		lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineandshaperenderer.setBaseItemLabelsVisible(true);

		return chart;
	}

	public static CategoryDataset createDataset(String[] rowKeys,String[] colKeys, double[][] data) {

		/*
		 * String[] rowKeys = { "One", "Two", "Three" }; String[] colKeys = {
		 * "1987", "1997", "2007" };
		 * 
		 * double[][] data = { { 50, 20, 30 }, { 20, 10D, 40D }, { 40, 30.0008D,
		 * 38.24D }, };
		 */
		/*double[][] data =new double[3][length] ;
		Iterator<NetworkFeathurIndex> iterator = networkFeathurIndexArray.iterator();
		int j = 0;
		while (iterator.hasNext()) {
			NetworkFeathurIndex networkFeathurIndex = iterator.next();
			data[0][j] = networkFeathurIndex.getNetworkCapacity();
			data[1][j] = networkFeathurIndex.getAveragePathLength();
			data[2][j++] = networkFeathurIndex.getAverageClusteringCoefficient();
		}*/
		// 或者使用类似以下代码
		// DefaultCategoryDataset categoryDataset = new
		// DefaultCategoryDataset();
		// categoryDataset.addValue(10, "rowKey", "colKey");

		return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);
	}
}